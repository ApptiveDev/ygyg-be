package foiegras.ygyg.global.common.exception;


import foiegras.ygyg.global.common.response.BaseResponse;
import foiegras.ygyg.global.common.response.BaseResponseStatus;
import foiegras.ygyg.slack.application.SlackService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class BaseExceptionHandler {

	private final SlackService slackService;


	/**
	 * 발생한 예외 처리
	 */

	// 등록된 에러
	@ExceptionHandler(BaseException.class)
	protected ResponseEntity<BaseResponse<Void>> BaseError(BaseException e) {
		// BaseException의 BaseResponseStatus를 가져와서 BaseResponse를 만들어서 return해줌
		BaseResponse<Void> response = new BaseResponse<>(e.getStatus());
		log.error("BaseException -> {}({})", e.getStatus(), e.getStatus().getMessage(), e);
		return new ResponseEntity<>(response, response.httpStatus());
	}


	/**
	 * security 인증 에러
	 * 아이디가 없거나 비밀번호가 틀린 경우 AuthenticationManager 에서 발생
	 *
	 * @return FAILED_TO_LOGIN 에러 response
	 */
	@ExceptionHandler(BadCredentialsException.class)
	protected ResponseEntity<BaseResponse<Void>> handleBadCredentialsException(BadCredentialsException e) {
		BaseResponse<Void> response = new BaseResponse<>(BaseResponseStatus.FAILED_TO_LOGIN);
		log.error("BadCredentialsException: ", e);
		return new ResponseEntity<>(response, response.httpStatus());
	}


	// 런타임 에러
	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<BaseResponse<Void>> RuntimeError(RuntimeException e) {
		// BaseException으로 잡히지 않는 RuntimeError는, INTERNAL_SEBVER_ERROR로 처리해줌
		BaseResponse<Void> response = new BaseResponse<>(BaseResponseStatus.INTERNAL_SERVER_ERROR);
		// 로그 출력
		log.error("RuntimeException: ", e);
		for (StackTraceElement s : e.getStackTrace()) {
			System.out.println(s);
		}
		// 에러 메시지를 슬랙으로 전송
		slackService.sendMessage("예기치 못한 에러가 발생했습니다. 확인이 필요합니다.", this.concatErrorMessage(e));
		return new ResponseEntity<>(response, response.httpStatus());
	}


	// 유효성 검사 에러
	@ExceptionHandler({ ConstraintViolationException.class, MethodArgumentNotValidException.class })
	protected ResponseEntity<BaseResponse<Void>> ConstraintViolationException(Exception e) {
		// BaseException으로 잡히지 않는 ConstraintViolationException 과 MethodArgumentNotValidException 은 INVALID_INPUT_VALUE 로 처리
		BaseResponse<Void> response = new BaseResponse<>(BaseResponseStatus.INVALID_INPUT_VALUE);
		// 에러 메시지
		log.error("ConstraintViolationException: ", e);
		return new ResponseEntity<>(response, response.httpStatus());
	}


	// 에러 메시지 조합
	private String concatErrorMessage(Exception e) {
		// 전체 스택 트레이스에서 최대 10줄만 가져옴
		StringBuilder limitedStackTrace = new StringBuilder();
		StackTraceElement[] stackTrace = e.getStackTrace();
		for (int i = 0; i < 10 && i < stackTrace.length; i++) {
			limitedStackTrace.append(stackTrace[i].toString()).append("\n");
		}
		// 조합
		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append("          [Error] ").append(e.getClass().getSimpleName()).append("\n")
			.append("          [Message] ----> ").append(e.getMessage()).append("\n")
			.append("          [StackTrace Start] --------------------------------------------------\n").append(limitedStackTrace).append("\n")
			.append("          [StackTrace End] --------------------------------------------------\n")
			.append("\n          [Caused By] ----> ")
			.append(e.getCause() != null ? e.getCause().toString() : "없음");
		return errorMessage.toString();
	}

}
