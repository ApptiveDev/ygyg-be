package foiegras.ygyg.user.infrastructure.jpa;


import foiegras.ygyg.user.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
}
