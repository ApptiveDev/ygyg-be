package foiegras.ygyg.comment.infrastructure;


import foiegras.ygyg.comment.application.port.CommentRepository;
import foiegras.ygyg.comment.domain.model.Comment;
import foiegras.ygyg.comment.infrastructure.entity.CommentEntity;
import foiegras.ygyg.comment.infrastructure.jpa.CommentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

	// jpa
	private final CommentJpaRepository commentJpaRepository;


	@Override
	public Comment save(Comment comment) {
		return commentJpaRepository.save(CommentEntity.fromModel(comment)).toModel();
	}


	// userPostId로 게시글에 달린 댓글 리스트 리턴
	@Override
	public List<Comment> findAllByUserPostId(Long userPostId) {
		return commentJpaRepository.findAllByUserPostId(userPostId)
			.stream()
			.map(CommentEntity::toModel) // 영속성 이전엔 없던 정보들까지 포함한 모델로 변환
			.toList();
	}

}
