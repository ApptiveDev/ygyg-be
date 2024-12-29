package foiegras.ygyg.global.common.exception;


import foiegras.ygyg.global.common.response.BaseResponseStatus;
import lombok.Getter;


@Getter
public class BaseException extends RuntimeException {

	private final BaseResponseStatus status;


	public BaseException(BaseResponseStatus status) {
		this.status = status;
	}

}