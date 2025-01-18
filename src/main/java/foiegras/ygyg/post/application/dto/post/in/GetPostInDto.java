package foiegras.ygyg.post.application.dto.post.in;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPostInDto {

	private Long userPostId;
	private UUID userUuid;

}
