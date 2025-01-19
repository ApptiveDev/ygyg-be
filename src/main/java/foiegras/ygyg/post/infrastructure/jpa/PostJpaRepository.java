package foiegras.ygyg.post.infrastructure.jpa;


import foiegras.ygyg.post.infrastructure.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {
}
