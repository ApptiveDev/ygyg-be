package foiegras.ygyg.user.api.request;


import lombok.Getter;


@Getter
public class SignInRequest {

	private String userSignInId;
	private String userPassword;

}
