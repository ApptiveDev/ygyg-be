package foiegras.ygyg.global.common.security;


import foiegras.ygyg.global.common.exception.BaseException;
import foiegras.ygyg.global.common.response.BaseResponseStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	// 인증 되어있지 않은 경우를 다룬다
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		// 이미 커밋된 응답인지 확인
		if (response.isCommitted()) {
			return;
		}
		// request를 여러 번 읽기 위한 wrapping
		log.error("login 필요: {}", request.getRequestURI());
		throw new BaseException(BaseResponseStatus.NO_SIGN_IN);
	}

}
