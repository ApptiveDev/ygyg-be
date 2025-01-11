package foiegras.ygyg.comment.application.dto.in;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentInDto {

	private Long userPostId;

	private UUID writerUuid;

	private String commentContents;

}
