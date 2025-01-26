package foiegras.ygyg.comment.api.controller;


import foiegras.ygyg.comment.api.request.CreateCommentRequest;
import foiegras.ygyg.comment.api.response.GetCommentResponse;
import foiegras.ygyg.comment.application.dto.in.CreateCommentInDto;
import foiegras.ygyg.comment.application.dto.in.GetCommentInDto;
import foiegras.ygyg.comment.application.dto.out.GetCommentListItemOutDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
	 * 2. 댓글 조회
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


	//2. 댓글 조회
	@Operation(summary = "댓글 조회", description = "댓글 조회", tags = { "Comment" })
	@GetMapping("/{userPostId}")
	@SecurityRequirement(name = "Bearer Auth")
	public BaseResponse<GetCommentResponse> getComment(@PathVariable Long userPostId) {
		GetCommentInDto inDto = GetCommentInDto.builder().userPostId(userPostId).build();
		List<GetCommentListItemOutDto> outDto = commentService.getComment(inDto)
			.stream().map(comment -> modelMapper.map(comment, GetCommentListItemOutDto.class)).toList();
		GetCommentResponse response = GetCommentResponse.builder().comments(outDto).build();
		return new BaseResponse<>(response);
	}

}
