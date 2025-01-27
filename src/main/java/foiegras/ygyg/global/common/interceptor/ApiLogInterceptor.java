package foiegras.ygyg.global.common.interceptor;


import foiegras.ygyg.global.common.security.CustomUserDetails;
import foiegras.ygyg.global.common.wrapper.CachingRequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.io.BufferedReader;
import java.util.Map;


@Slf4j
@Component
public class ApiLogInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			// request를 여러 번 읽기 위한 wrapping
			CachingRequestWrapper requestWrapper = new CachingRequestWrapper(request);
			// 로그 내용
			String contents = "";
			// 회원 여부
			contents += this.checkUser(requestWrapper);
			// pathVariable 추출
			String pathVariables = this.getPathVariables(requestWrapper);
			// Body 추출 및 password 보안 처리
			StringBuilder body = this.getBody(requestWrapper);
			// 호출한 API, parameter, Path-vairable, body를 contents에 추가
			contents += this.completeContents(requestWrapper, pathVariables, body);
			log.info(contents);
		} catch (Exception e) {
			log.error("ApiLogInterceptor Error: {}", e.getMessage());
		}
		return true;
	}


	// 호출한 API, parameter, Path-vairable, body를 contents에 추가
	private String completeContents(CachingRequestWrapper requestWrapper, String pathVariables, StringBuilder body) {
		return "Request Method: " + requestWrapper.getMethod() + "\n"
			+ "Request token: " + requestWrapper.getHeader("Authorization") + "\n"
			+ "Request URI: " + requestWrapper.getRequestURI() + "\n"
			+ "Request Parameter: " + requestWrapper.getQueryString() + "\n"
			+ "Request PathVariable: " + pathVariables + "\n"
			+ "Request Body: " + body + "\n"
			+ "=====================================================================================";
	}


	// 회원,비회원 확인
	private String checkUser(CachingRequestWrapper request) {
		// contextHolder에서 principal 추출
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUserDetails authentication;
		// 회원,비회원에 따라서 로그 내용 분리
		if (!principal.equals("anonymousUser")) {
			authentication = (CustomUserDetails) principal;
			return "\n=====================================================================================\n[User] -> Uuid: " + authentication.getUserUuid() + "\n" + "userEmail: "
				+ authentication.getUserEmail() + "\n";
		} else {
			return "\n=====================================================================================\n[Non-User]\n";
		}
	}


	// PathVariable 추출
	private String getPathVariables(CachingRequestWrapper request) {
		Map<String, String> pathVariables;
		try {
			pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			return pathVariables.toString();
		} catch (Exception e) {
			return null;
		}

	}


	// Body 추출 및 password 보안 처리
	private StringBuilder getBody(CachingRequestWrapper request) {
		StringBuilder body = new StringBuilder();
		BufferedReader reader;
		try {
			reader = request.getReader();
			String line;
			// password, userPassword가 포함된 라인은 보안처리
			while ((line = reader.readLine()) != null) {
				if (line.contains("password") || line.contains("userPassword") || line.contains("Password")) line = " \"password\": -- ";
				body.append(line);
			}
		} catch (Exception e) {
			log.error("ApiLogInterceptor Error: {}", e.getMessage());
		}
		return body;
	}

}
