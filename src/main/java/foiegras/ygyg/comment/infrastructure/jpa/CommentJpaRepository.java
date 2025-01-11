package foiegras.ygyg.comment.infrastructure.jpa;


import foiegras.ygyg.comment.infrastructure.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {
}
