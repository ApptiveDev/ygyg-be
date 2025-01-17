package foiegras.ygyg.user.api.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
	@Size(max = 10)
	private String userNickname;
	@NotNull
	private Integer routeId;

}
