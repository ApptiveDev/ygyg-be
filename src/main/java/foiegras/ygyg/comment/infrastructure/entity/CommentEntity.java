package foiegras.ygyg.comment.infrastructure.entity;


import foiegras.ygyg.comment.domain.model.Comment;
import foiegras.ygyg.global.common.basetime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Getter
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "comment")
public class CommentEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;

	@Column(name = "user_post_id")
	private Long userPostId;

	@Column(name = "writer_uuid", nullable = false, columnDefinition = "BINARY(16)")
	private UUID writerUuid;

	@Column(name = "comment_contents", length = 200, nullable = false)
	private String commentContents;


	/**
	 * - Domain Model을 Entity로 변환
	 * - Entity를 Domain Model로 변환
	 */
	public static CommentEntity fromModel(Comment comment) {
		return CommentEntity.builder()
			.userPostId(comment.getUserPostId())
			.writerUuid(comment.getWriterUuid())
			.commentContents(comment.getCommentContents())
			.build();
	}


	public Comment toModel() {
		return Comment.innerBuilder()
			.id(id)
			.userPostId(userPostId)
			.writerUuid(writerUuid)
			.commentContents(commentContents)
			.createdAt(getCreatedAt())
			.updatedAt(getUpdatedAt())
			.build();
	}

}
