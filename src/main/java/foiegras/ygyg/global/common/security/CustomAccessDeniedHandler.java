package foiegras.ygyg.global.common.security;


import foiegras.ygyg.global.common.exception.BaseException;
import foiegras.ygyg.global.common.response.BaseResponseStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;


@Slf4j
@Configuration
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	// 로그인은 되었지만, 접근 권한이 부족한 경우(인가 실패)를 다룬다
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// 이미 커밋된 응답인지 확인
		if (response.isCommitted()) {
			return;
		}
		log.error("접근 권한이 없습니다");
		throw new BaseException(BaseResponseStatus.NO_ACCESS_AUTHORITY);
	}

}
