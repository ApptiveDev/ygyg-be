package foiegras.ygyg.comment.domain.model;


import foiegras.ygyg.global.common.exception.BaseException;
import foiegras.ygyg.global.common.response.BaseResponseStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Builder(toBuilder = true, builderMethodName = "innerBuilder")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

	private Long id;

	private Long userPostId;

	private UUID writerUuid;

	private String commentContents;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;


	/**
	 * 생성자
	 * - createNew: userPostId, writerUuid, commentContents를 받아 새로운 Comment 생성
	 * - validate: Comment 생성 시 유효성 검사
	 */

	public static Comment createNew(Long userPostId, UUID writerUuid, String commentContents) {
		validate(userPostId, writerUuid, commentContents);
		return Comment.innerBuilder()
			.userPostId(userPostId)
			.writerUuid(writerUuid)
			.commentContents(commentContents)
			.build();
	}


	public static void validate(Long userPostId, UUID writerUuid, String commentContents) {
		if (userPostId == null ||
			writerUuid == null ||
			commentContents == null) {
			throw new BaseException(BaseResponseStatus.MISSING_CREATE_COMMENT_VALUE);
		}
	}

}
