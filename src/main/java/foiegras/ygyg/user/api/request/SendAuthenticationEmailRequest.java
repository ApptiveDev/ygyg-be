package foiegras.ygyg.user.api.request;


import jakarta.validation.constraints.Email;
import lombok.Getter;


@Getter
public class SendAuthenticationEmailRequest {

	@Email
	private String userEmail;

}
