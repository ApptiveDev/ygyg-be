package foiegras.ygyg.post.infrastructure.jpa;


import foiegras.ygyg.post.infrastructure.entity.ParticipatingUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ParticipatingUsersJpaRepository extends JpaRepository<ParticipatingUsersEntity, Long> {
}
