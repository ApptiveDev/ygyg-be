package foiegras.ygyg.comment.infrastructure;


import foiegras.ygyg.comment.application.port.CommentRepository;
import foiegras.ygyg.comment.domain.model.Comment;
import foiegras.ygyg.comment.infrastructure.entity.CommentEntity;
import foiegras.ygyg.comment.infrastructure.jpa.CommentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

	// jpa
	private final CommentJpaRepository commentJpaRepository;


	@Override
	public Comment save(Comment comment) {
		return commentJpaRepository.save(CommentEntity.fromModel(comment)).toModel();
	}

}
