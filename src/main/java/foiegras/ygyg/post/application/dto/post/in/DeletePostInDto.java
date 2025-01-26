package foiegras.ygyg.post.application.dto.post.in;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeletePostInDto {

	@NotNull
	private Long userPostId;

	@NotNull
	private UUID userUuid;

}
