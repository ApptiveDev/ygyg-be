package foiegras.ygyg.post.api.controller;


import foiegras.ygyg.global.common.response.BaseResponse;
import foiegras.ygyg.global.common.security.CustomUserDetails;
import foiegras.ygyg.post.api.request.CreatePostRequest;
import foiegras.ygyg.post.api.response.GetPostResponse;
import foiegras.ygyg.post.application.dto.in.CreatePostInDto;
import foiegras.ygyg.post.application.dto.in.GetPostInDto;
import foiegras.ygyg.post.application.dto.in.GetPostOutDto;
import foiegras.ygyg.post.application.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/post")
@RequiredArgsConstructor
@Validated
public class PostController {

	//utils
	private final ModelMapper modelMapper;

	//services
	private final PostService postService;


	/**
	 * PostController - 기본적인 post crud 처리
	 * 1. 소분글 생성
	 * 2. 소분글 상세조회
	 * 3. 소분글 삭제
	 * 4. 소분글 수정
	 */

	// 1. 소분글 게시
	@Operation(summary = "소분글 생성", description = "소분글 생성", tags = { "Post" })
	@PostMapping()
	@SecurityRequirement(name = "Bearer Auth")
	public BaseResponse<Void> createPost(@Valid @RequestBody CreatePostRequest request) {
		CreatePostInDto indto = modelMapper.map(request, CreatePostInDto.class);
		postService.createPost(indto);
		return new BaseResponse<>();
	}


	// 2. 소분글 상세조회
	@Operation(summary = "소분글 상세조회", description = "소분글 상세조회", tags = { "Post" })
	@GetMapping("/{userPostId}")
	@SecurityRequirement(name = "Bearer Auth")
	public BaseResponse<GetPostResponse> getPost(@PathVariable Long userPostId, @AuthenticationPrincipal CustomUserDetails authentication) {
		GetPostInDto inDto = GetPostInDto.builder()
			.userPostId(userPostId)
			.userUuid(authentication.getUserUuid())
			.build();
		GetPostOutDto outDto = postService.getPost(inDto);
		GetPostResponse response = modelMapper.map(outDto, GetPostResponse.class);
		return new BaseResponse<>(response);
	}

}
