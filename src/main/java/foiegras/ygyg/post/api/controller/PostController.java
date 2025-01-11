package foiegras.ygyg.post.api.controller;


import foiegras.ygyg.global.common.response.BaseResponse;
import foiegras.ygyg.post.api.request.CreatePostRequest;
import foiegras.ygyg.post.application.dto.in.CreatePostInDto;
import foiegras.ygyg.post.application.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/board")
@RequiredArgsConstructor
public class PostController {

	//services
	private final ModelMapper modelMapper;
	private final PostService postService;


	/**
	 * PostController - 기본적인 post crud 처리
	 * 1. 소분글 생성
	 * 2. 소분글 수정
	 * 3. 소분글 삭제
	 * 4. 소분글 상세조회
	 */

	// 1. 소분글 게시
	@Operation(summary = "소분글 생성", description = "소분글 생성", tags = { "Post" })
	@PostMapping("/")
	public BaseResponse<Void> createPost(@Valid @RequestBody CreatePostRequest request) {

		CreatePostInDto indto = modelMapper.map(request, CreatePostInDto.class);
		postService.createPost(indto);

		return new BaseResponse<>();
	}

}
