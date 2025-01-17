package foiegras.ygyg.user.infrastructure.entity;


import foiegras.ygyg.global.common.basetime.BaseTimeEntity;
import foiegras.ygyg.user.infrastructure.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class UserEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(name = "user_uuid", nullable = false, columnDefinition = "BINARY(16)")
	private UUID userUuid;

	@Column(name = "user_name", length = 100, nullable = false)
	private String userName;

	@Column(name = "user_email", length = 100, nullable = false)
	private String userEmail;

	@Column(name = "user_password", length = 100, nullable = false)
	private String userPassword;

	@Column(name = "user_nickname", length = 10, nullable = false)
	private String userNickname;

	@Column(name = "user_role", length = 1, nullable = false)
	@Builder.Default
	private UserRole userRole = UserRole.USER;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;


	/**
	 * 생성자
	 */

	// 생성자
	public static UserEntity createNewUser(UUID userUuid, String userName, String userEmail, String userPassword, String userNickname) {
		return UserEntity.builder()
			.userUuid(userUuid)
			.userName(userName)
			.userEmail(userEmail)
			.userPassword(userPassword)
			.userNickname(userNickname)
			.userRole(UserRole.USER)
			.build();
	}


	/**
	 * 도메인 로직
	 * 1. soft delete
	 */

	// 1. soft delete
	public UserEntity softDelete(LocalDateTime deletedAt) {
		this.deletedAt = deletedAt;
		return this;
	}

}
