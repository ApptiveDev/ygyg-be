package foiegras.ygyg.post.infrastructure.jpa;


import foiegras.ygyg.post.infrastructure.entity.UserPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserPostJpaRepository extends JpaRepository<UserPostEntity, Long> {
}
