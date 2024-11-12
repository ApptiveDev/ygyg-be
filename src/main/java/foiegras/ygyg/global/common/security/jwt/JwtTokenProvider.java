package foiegras.ygyg.global.common.security.jwt;


import foiegras.ygyg.global.common.exception.BaseException;
import foiegras.ygyg.global.common.response.BaseResponseStatus;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;


@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

	@Value("${JWT.secret-key}")
	private String secretKey;

	@Value("${JWT.token.access-expiration-time}")
	private long accessExpirationTime;

	/**
	 * TokenProvider
	 * 1. 토큰에서 uuid 가져오기
	 * 2. Claims에서 원하는 claim 값 추출
	 * 3. 토큰에서 모든 claims 추출
	 * 4. 액세스 토큰 생성
	 * 5. refresh 토큰 생성
	 */

	/**
	 * 1. 토큰에서 uuid 가져오기
	 *
	 * @param token
	 * @return jwt토큰에서 추출한 사용자 UUID 반환
	 * 토큰에서 추출한 클레임에서 사용자 UUID를 추출하여 반환합니다.
	 */
	public String validateAndGetUserUuid(String token) throws BaseException {
		try {
			return extractClaim(token, Claims::getSubject);
		} catch (NullPointerException e) {
			log.info("토큰에 담긴 유저 정보가 없습니다");
			throw new BaseException(BaseResponseStatus.WRONG_JWT_TOKEN);
		}
	}


	/**
	 * 2. Claims에서 원하는 claim 값 추출
	 *
	 * @param token
	 * @param claimsResolver jwt토큰에서 추출한 정보를 어떻게 처리할지 결정하는 함수
	 * @return jwt토큰에서 모든 클레임(클레임은 토큰에 담긴 정보 의미 ) 추출한 다음 claimsResolver함수를 처리해 결과 반환
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}


	/**
	 * 3. 토큰에서 모든 claims 추출
	 *
	 * @param token 주어진 JWT 토큰에서 모든 클레임을 추출하여 반환합니다.
	 *              토큰의 서명을 확인하기 위해 사용할 서명 키(getSigningKey())를 설정하고 토큰을 파싱하여 클레임들을 추출합니다.
	 */
	private Claims extractAllClaims(String token) {
		log.info("extractAllClaims token={}", token);
		try {
			return Jwts
				.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token) // 이 단계에서 token의 유효성 검사 및 만료일 검사를 실시한다!
				.getBody();
		}
		// 파싱 예외 처리
		catch (ExpiredJwtException e) {
			log.error("만료된 토큰입니다");
			throw new BaseException(BaseResponseStatus.WRONG_JWT_TOKEN);
		} catch (UnsupportedJwtException e) {
			log.error("지원되지 않는 유형의 토큰입니다");
			throw new BaseException(BaseResponseStatus.WRONG_JWT_TOKEN);
		} catch (MalformedJwtException | IllegalArgumentException e) {
			log.error("잘못된 토큰입니다");
			throw new BaseException(BaseResponseStatus.WRONG_JWT_TOKEN);
		} catch (io.jsonwebtoken.security.SignatureException e) {
			log.error("SecretKey가 일치하지 않습니다");
			throw new BaseException(BaseResponseStatus.WRONG_JWT_TOKEN);
		}
	}


	/**
	 * 4. 액세스 토큰 생성
	 *
	 * @param userDetails 사용자 정보
	 * @return 클레임 정보와 사용자 정보를 기반으로 jwt 토큰 생성
	 * 클레임 정보, 사용자 ID, 생성 시간, 만료 시간 등을 설정하고, 서명 알고리즘과 서명 키를 사용하여 토큰을 생성합니다.
	 * Access Token 역활
	 */
	public String generateToken(UserDetails userDetails) {

		return generateToken(Map.of(), userDetails);
	}


	public String generateToken(Map<String, Objects> extractClaims, UserDetails userDetails) {
		return buildToken(extractClaims, userDetails, accessExpirationTime);
	}


	//  토큰 생성
	public String buildToken(
		Map<String, Objects> extractClaims,
		UserDetails userDetails,
		long expiration) {
		log.info("generateToken {} ", userDetails);
		return Jwts.builder()
			.setClaims(extractClaims)
			.setSubject(userDetails.getUsername()) // uuid를 subject로 설정
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + expiration))
			.signWith(getSigningKey(), SignatureAlgorithm.HS256)
			.compact();
	}

	//	/**
	//	 * 5. 리프래시 토큰 생성
	//	 *
	//	 * @param userDetails 사용자 정보
	//	 * @return
	//	 */
	//	public String generateRefreshToken(UserDetails userDetails) {
	//		String refreshToken = buildToken(Map.of(), userDetails, refreshExpirationTime);
	//		// redis에 저장
	//		redisTemplate.opsForValue().set(
	//			userDetails.getUsername(),
	//			refreshToken,
	//			refreshExpirationTime,
	//			TimeUnit.MILLISECONDS
	//		);
	//		log.info("refreshExpirationTime={}", refreshExpirationTime);
	//		log.info("TimeUnit.MILLISECONDS={}", TimeUnit.MILLISECONDS);
	//
	//		return refreshToken;
	//	}


	//  3개로 이루어진 키값을 풀기위한 메소드
	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
