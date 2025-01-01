package foiegras.ygyg.user.application.dto.in;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignInInDto {

	private String userEmail;
	private String userPassword;

}
