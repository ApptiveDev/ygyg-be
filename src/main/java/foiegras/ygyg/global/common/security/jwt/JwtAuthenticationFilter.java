package foiegras.ygyg.global.common.security.jwt;


import foiegras.ygyg.global.common.wrapper.CachingRequestWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	// 필터 과정
	private final JwtTokenProvider jwtTokenProvider;
	private final UserDetailsService userDetailsService;


	@Override
	protected void doFilterInternal(
		@NonNull
		HttpServletRequest request,
		@NonNull
		HttpServletResponse response,
		@NonNull
		FilterChain filterChain

	) throws ServletException, IOException {
		// 요청을 CachingRequestWrapper로 감싸서, 여러번 요청을 받아도 문제가 없도록 함
		CachingRequestWrapper requestWrapper = new CachingRequestWrapper(request);
		// 각종 변수 설정
		final String authHeader = requestWrapper.getHeader("Authorization"); // HTTP 요청 헤더에서 "Authorization" 값을 가져옴

		/**
		 * 토큰 유효성 검사 및 인증 정보 저장 로직
		 * 1. 헤더가 null이거나, "Bearer"로 시작하지 않는다면, 로그인 에러를 발생
		 * 2. 유효성 검사 & JWT 토큰에서 사용자 email을 가져옴
		 * 3. 중복되지 않았다면 ContextHolder에 유저 정보를 담음 (여기선 userUuid가 담김)
		 */

		// 1. 헤더가 null이거나, "Bearer"로 시작하지 않는다면 pass
		if (!this.checkHeader(authHeader)) {
			filterChain.doFilter(requestWrapper, response);
			return;
		}

		// 2. 유효성 검사 & JWT 토큰에서 사용자 email을 가져옴
		String userUUID = this.validateAndExtractUuid(authHeader);

		// 3. 중복되지 않았다면 ContextHolder에 유저 정보를 담음 (여기선 userUuid가 담김)
		this.setUserDataToSecurityContextHolder(userUUID, requestWrapper);

		// ContextHolder에 유저 정보를 저장 후, 다음 필터로 요청과 응답을 전달
		filterChain.doFilter(requestWrapper, response);
	}


	// 2. 헤더 검사
	private boolean checkHeader(String authHeader) {
		return authHeader != null && authHeader.startsWith("Bearer ");
	}


	// 3. 유효성 검사 & 토큰에서 사용자 UUID 추출
	private String validateAndExtractUuid(String authHeader) {
		return jwtTokenProvider.validateAndGetUserUuid(authHeader.substring(7));
	}


	// 4. ContextHolder에 유저 정보 저장
	private void setUserDataToSecurityContextHolder(String userUUID, CachingRequestWrapper requestWrapper) {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			// userEmail 기반으로 사용자 정보를 가져옴
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userUUID);
			// 인증 정보를 생성하고 Security Context에 유저 정보를 설정 -> (principal, credentials, authorities) 순서로 들어감
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails,
				null,
				userDetails.getAuthorities());
			// 현재 요청에 대한 세부 정보를 인증 토큰에 설정
			authenticationToken.setDetails(
				new WebAuthenticationDetailsSource().buildDetails(requestWrapper));
			// 인증 토큰을 Security Context에 설정
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
	}

}