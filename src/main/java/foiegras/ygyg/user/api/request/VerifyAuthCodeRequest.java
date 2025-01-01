package foiegras.ygyg.user.api.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class VerifyAuthCodeRequest {

	@Email
	private String userEmail;

	@NotEmpty(message = "인증 코드를 입력해주세요.")
	@Size(min = 6, max = 6, message = "인증 코드는 6자리로 입력해주세요.")
	private String authCode;

}