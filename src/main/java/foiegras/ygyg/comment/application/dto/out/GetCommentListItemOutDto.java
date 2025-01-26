package foiegras.ygyg.comment.application.dto.out;


import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetCommentListItemOutDto {

	// 탈퇴한 유저 시 null
	private UUID writerUuid;
	private String commentContents;
	private LocalDateTime createdAt;

}
