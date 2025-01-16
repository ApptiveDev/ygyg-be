package foiegras.ygyg.post.infrastructure.jpa;


import foiegras.ygyg.post.infrastructure.entity.ParticipatingUsersEntity;
import foiegras.ygyg.post.infrastructure.entity.UserPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface ParticipatingUsersJpaRepository extends JpaRepository<ParticipatingUsersEntity, Long> {

	Optional<ParticipatingUsersEntity> findByUserPostEntity(UserPostEntity userPostEntity);

	Optional<ParticipatingUsersEntity> findByUserPostEntityAndParticipatingUserUUID(
		UserPostEntity userPostEntity,
		UUID userUuid
	);

}
