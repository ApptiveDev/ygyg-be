package foiegras.ygyg.user.api.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class SignUpRequest {

	@Email
	private String userEmail;
	@NotBlank
	private String userName;
	@NotBlank
	private String userPassword;
	@NotBlank
	private String userNickname;
	@NotNull
	private Integer routeId;

}
