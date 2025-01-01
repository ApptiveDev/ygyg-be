package foiegras.ygyg.user.application.dto.out;


import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class SendEmailOutDto {

	// 수신자 email
	private String receiverEmail;
	// 제목
	private String subject;
	// 내용
	private String text;
	// 코드
	private String code;

}
