package foiegras.ygyg.comment.api.controller;


import foiegras.ygyg.comment.api.request.CreateCommentRequest;
import foiegras.ygyg.comment.application.dto.in.CreateCommentInDto;
import foiegras.ygyg.comment.application.service.CommentService;
import foiegras.ygyg.global.common.response.BaseResponse;
import foiegras.ygyg.global.common.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

	// service
	private final CommentService commentService;
	// util
	private final ModelMapper modelMapper;


	/**
	 * CommentController
	 * 1. 댓글 작성
	 */

	//1. 댓글 작성
	@Operation(summary = "댓글 작성", description = "댓글 작성", tags = { "Comment" })
	@PostMapping("")
	@SecurityRequirement(name = "Bearer Auth")
	public BaseResponse<Void> createComment(@Valid @RequestBody CreateCommentRequest requestDto, @AuthenticationPrincipal CustomUserDetails authentication) {
		CreateCommentInDto inDto = modelMapper.map(requestDto, CreateCommentInDto.class);
		inDto = inDto.toBuilder().writerUuid(authentication.getUserUuid()).build();
		commentService.createComment(inDto);
		return new BaseResponse<>();
	}

}
