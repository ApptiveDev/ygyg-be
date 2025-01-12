package foiegras.ygyg.global.common.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

	/**
	 * 200: 요청 성공
	 **/
	SUCCESS(HttpStatus.OK, true, 200, "요청에 성공하였습니다."),

	/**
	 * 400 : security 에러
	 */
	WRONG_JWT_TOKEN(HttpStatus.UNAUTHORIZED, false, 401, "다시 로그인 해주세요"),
	NO_SIGN_IN(HttpStatus.UNAUTHORIZED, false, 402, "로그인을 먼저 진행해주세요"),
	NO_ACCESS_AUTHORITY(HttpStatus.FORBIDDEN, false, 403, "접근 권한이 없습니다"),
	DISABLED_USER(HttpStatus.FORBIDDEN, false, 404, "비활성화된 계정입니다"),
	FAILED_TO_RESTORE(HttpStatus.INTERNAL_SERVER_ERROR, false, 405, "계정 복구에 실패했습니다. 관리자에게 문의해주세요."),

	/**
	 * 900: 기타 에러
	 */
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, 900, "Internal server error"),
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, false, 901, "잘못된 입력값입니다. 다시 확인해주세요."),

	/**
	 * 2000: users service error
	 */
	// Users
	DUPLICATED_USER(HttpStatus.CONFLICT, false, 2001, "이미 가입된 멤버입니다."),
	FAILED_TO_LOGIN(HttpStatus.UNAUTHORIZED, false, 2002, "아이디 또는 패스워드를 다시 확인하세요."),
	NO_EXIST_USER(HttpStatus.NOT_FOUND, false, 2003, "존재하지 않는 멤버 정보입니다."),
	PASSWORD_SAME_FAILED(HttpStatus.BAD_REQUEST, false, 2004, "현재 사용중인 비밀번호 입니다."),
	PASSWORD_MATCH_FAILED(HttpStatus.BAD_REQUEST, false, 2005, "패스워드를 다시 확인해주세요."),
	DUPLICATED_NICKNAME(HttpStatus.CONFLICT, false, 2006, "이미 사용중인 닉네임입니다."),
	INVALID_EMAIL_ADDRESS(HttpStatus.BAD_REQUEST, false, 2007, "이메일을 다시 확인해주세요."),
	NO_PRIVACY_SETTINGS(HttpStatus.INTERNAL_SERVER_ERROR, false, 2008, "개인정보 설정이 존재하지 않습니다. 서버 관리자에게 문의해주세요"),
	WRONG_AUTH_EMAIL(HttpStatus.BAD_REQUEST, false, 2009, "요청 이메일이 일치하지 않습니다"),
	NOT_FOLLOW_SELF(HttpStatus.BAD_REQUEST, false, 2010, "본인을 팔로우할 수 없습니다"),
	NOT_EXIST_ROUTE(HttpStatus.BAD_REQUEST, false, 2011, "존재하지 않는 방문 경로입니다."),

	/**
	 * 3000: post service error
	 */
	NO_EXIST_POST(HttpStatus.NOT_FOUND, false, 3001, "존재하지 않는 글입니다"),
	NO_EXIST_CATEGORY(HttpStatus.NOT_FOUND, false, 3002, "존재하지 않는 카테고리입니다."),
	NO_EXIST_UNIT(HttpStatus.NOT_FOUND, false, 3003, "존재하지 않는 소분단위입니다."),

	/**
	 * 4000: comment service error
	 */
	NO_EXIST_COMMENT(HttpStatus.NOT_FOUND, false, 4001, "존재하지 않는 댓글입니다"),
	NO_DELETE_COMMENT_AUTHORITY(HttpStatus.BAD_REQUEST, false, 4002, "댓글 삭제 권한이 없습니다"),
	MISSING_CREATE_COMMENT_VALUE(HttpStatus.BAD_REQUEST, false, 4003, "댓글 생성시 필요한 값이 누락되었습니다"),

	;

	private final HttpStatusCode httpStatusCode;
	private final boolean isSuccess;
	private final int code;
	private final String message;
}
