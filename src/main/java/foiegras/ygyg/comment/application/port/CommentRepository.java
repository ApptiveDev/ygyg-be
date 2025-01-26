package foiegras.ygyg.comment.application.port;


import foiegras.ygyg.comment.domain.model.Comment;

import java.util.List;


public interface CommentRepository {

	// 댓글 저장
	Comment save(Comment comment);

	// 댓글 조회
	List<Comment> findAllByUserPostId(Long userPostId);

}
