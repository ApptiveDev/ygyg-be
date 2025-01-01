package foiegras.ygyg.user.api.request;


import lombok.Getter;


@Getter
public class SignInRequest {

	private String userEmail;
	private String userPassword;

}
