package foiegras.ygyg.comment.application.service;


import foiegras.ygyg.comment.application.dto.in.CreateCommentInDto;
import foiegras.ygyg.comment.application.port.CommentRepository;
import foiegras.ygyg.comment.domain.model.Comment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentService {

	// repository
	private final CommentRepository commentRepository;
	// util
	private final ModelMapper modelMapper;


	/**
	 * CommentService
	 * 1. 댓글 생성
	 */

	//1. 댓글 생성
	public Comment createComment(CreateCommentInDto inDto) {
		return commentRepository.save(Comment.createNew(inDto.getUserPostId(), inDto.getWriterUuid(), inDto.getCommentContents()));
	}

}
