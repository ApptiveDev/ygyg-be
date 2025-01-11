package foiegras.ygyg.user.application.dto.out;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SignInOutDto {

	private UUID userUuid;
	private String userNickname;
	private String accessToken;
	private String userName;
	private String userEmail;

}
