package foiegras.ygyg.comment.application.dto.out;


import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetCommentListItemOutDto {

	private UUID writerUuid;
	private String userNickname;
	private String commentContents;
	private LocalDateTime createdAt;

}
