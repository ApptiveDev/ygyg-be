package foiegras.ygyg.comment.application.port;


import foiegras.ygyg.comment.domain.model.Comment;


public interface CommentRepository {

	// 댓글 저장
	Comment save(Comment comment);

}
