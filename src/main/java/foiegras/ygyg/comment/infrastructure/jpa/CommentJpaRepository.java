package foiegras.ygyg.comment.infrastructure.jpa;


import foiegras.ygyg.comment.infrastructure.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {

	List<CommentEntity> findAllByUserPostId(Long userPostId);

}
