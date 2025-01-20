package foiegras.ygyg.post.infrastructure.jpa;


import foiegras.ygyg.post.infrastructure.entity.ParticipatingUsersEntity;
import foiegras.ygyg.post.infrastructure.entity.UserPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ParticipatingUsersJpaRepository extends JpaRepository<ParticipatingUsersEntity, Long> {

	List<ParticipatingUsersEntity> findByUserPostEntity(UserPostEntity userPostEntity);

	Optional<ParticipatingUsersEntity> findByUserPostEntityAndParticipatingUserUUID(
		UserPostEntity userPostEntity,
		UUID userUuid
	);

	// UserUuid와 UserPostEntity로 참여자 존재 여부 확인
	boolean existsByParticipatingUserUUIDAndUserPostEntity(UUID participantUuid, UserPostEntity userPost);

}
