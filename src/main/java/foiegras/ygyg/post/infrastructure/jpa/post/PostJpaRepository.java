package foiegras.ygyg.post.infrastructure.jpa.post;


import foiegras.ygyg.post.infrastructure.entity.PostEntity;
import foiegras.ygyg.post.infrastructure.entity.UserPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {

	Optional<PostEntity> findByUserPostEntity(UserPostEntity userPostEntity);

}
