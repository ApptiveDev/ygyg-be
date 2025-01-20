package foiegras.ygyg.global.common.exception;


import foiegras.ygyg.global.common.response.BaseResponse;
import foiegras.ygyg.global.common.response.BaseResponseStatus;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler {

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
		log.error("RuntimeException: ", e);
		for (StackTraceElement s : e.getStackTrace()) {
			System.out.println(s);
		}
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

	//

}
