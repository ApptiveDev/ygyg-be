package foiegras.ygyg.user.api.response;


import lombok.Getter;

import java.util.UUID;


@Getter
public class SignInResponse {

	private UUID userUuid;
	private String userNickname;
	private String accessToken;

}
