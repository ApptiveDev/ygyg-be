package foiegras.ygyg.comment.application.service;


import foiegras.ygyg.comment.application.dto.in.CreateCommentInDto;
import foiegras.ygyg.comment.application.dto.in.GetCommentInDto;
import foiegras.ygyg.comment.application.dto.out.GetCommentListItemOutDto;
import foiegras.ygyg.comment.application.port.CommentRepository;
import foiegras.ygyg.comment.domain.model.Comment;
import foiegras.ygyg.global.common.exception.BaseException;
import foiegras.ygyg.global.common.response.BaseResponseStatus;
import foiegras.ygyg.post.infrastructure.jpa.post.UserPostJpaRepository;
import foiegras.ygyg.user.infrastructure.entity.UserEntity;
import foiegras.ygyg.user.infrastructure.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CommentService {

	// repository
	private final CommentRepository commentRepository;
	private final UserPostJpaRepository userPostJpaRepository;
	private final UserJpaRepository userJpaRepository;


	/**
	 * CommentService
	 * 1. 댓글 생성
	 * 2. 댓글 조회
	 */

	//1. 댓글 생성
	public Comment createComment(CreateCommentInDto inDto) {
		return commentRepository.save(Comment.createNew(inDto.getUserPostId(), inDto.getWriterUuid(), inDto.getCommentContents()));
	}


	//2. 댓글 리스트 조회
	public List<GetCommentListItemOutDto> getComment(GetCommentInDto inDto) {
		// userPostId 검증
		userPostJpaRepository.findById(inDto.getUserPostId())
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER_POST_ENTITY));

		return commentRepository.findAllByUserPostId(inDto.getUserPostId())
			.stream().map(comment -> {
				// 댓글 게시자 가져오기
				Optional<UserEntity> user = userJpaRepository.findByUserUuid(comment.getWriterUuid());

				// todo: 댓글 하나당 user 조회 쿼리 하나씩 하는 현재방식 리팩토링
				return GetCommentListItemOutDto.builder()
					.writerUuid(user.map(UserEntity::getUserUuid).orElse(null))
					.userNickname(user.map(UserEntity::getUserNickname).orElse(null))
					.commentContents(comment.getCommentContents())
					.createdAt(comment.getCreatedAt())
					.build();

			}).toList();
	}

}
