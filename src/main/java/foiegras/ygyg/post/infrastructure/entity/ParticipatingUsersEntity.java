package foiegras.ygyg.post.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "participating_users")
public class ParticipatingUsersEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "participating_users_id", nullable = false, columnDefinition = "TINYINT")
	private Long id;

	@Column(name = "participating_user_uuid", nullable = false, columnDefinition = "BINARY(16)")
	private UUID participatingUserUUID;

	// 다대일 단방향에서 fk는 多인 참여자 엔티티가 갖게 된다
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_post_id")
	private UserPostEntity userPostEntity;


	/**
	 * 생성자
	 * - createNew: 참여자 UUID, 참여자가 참여한 게시글
	 */

	public static ParticipatingUsersEntity createNew(UUID participatingUserUUID, UserPostEntity userPostEntity) {
		return ParticipatingUsersEntity.builder()
			.participatingUserUUID(participatingUserUUID)
			.userPostEntity(userPostEntity)
			.build();
	}

}
