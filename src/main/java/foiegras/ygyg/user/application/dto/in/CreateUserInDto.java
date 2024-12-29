package foiegras.ygyg.user.application.dto.in;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserInDto {

	private UUID userUuid;
	private String userSignInId;
	private String userEmail;
	private String userPassword;
	private String userNickname;

}
