package foiegras.ygyg.user.application.dto.in;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SignUpInDto {

	private String userEmail;
	private String userPassword;
	private String userNickname;
	private Integer routeId;

}
