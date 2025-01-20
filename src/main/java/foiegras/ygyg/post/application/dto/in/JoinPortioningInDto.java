package foiegras.ygyg.post.application.dto.in;


import foiegras.ygyg.global.common.security.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JoinPortioningInDto {

	private Long userPostId;
	private CustomUserDetails authentication;

}
