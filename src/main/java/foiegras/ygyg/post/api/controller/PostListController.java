package foiegras.ygyg.post.api.controller;


import foiegras.ygyg.global.common.response.BaseResponse;
import foiegras.ygyg.post.api.response.GetPostListResponse;
import foiegras.ygyg.post.application.dto.postList.GetPostListOutDto;
import foiegras.ygyg.post.application.service.PostListService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/post")
@RequiredArgsConstructor
@Validated
public class PostListController {

	//utils
	private final ModelMapper modelMapper;
	//services
	private final PostListService postListService;


	/**
	 * PostListController - 소분글 목록 요청 처리
	 * 1. 소분글 전체목록 조회
	 * 2. 소분글 카테고리별 목록 조회
	 */

	@Operation(summary = "소분글 전체목록 조회", description = "소분글 전체목록 조회", tags = { "Post" })
	@GetMapping("/list")
	public BaseResponse<GetPostListResponse> getPostList(@RequestParam(name = "sortBy") String sortBy, @PageableDefault(size = 9) Pageable pageable) {
		GetPostListOutDto outDto = postListService.getPostList(sortBy, pageable);
		GetPostListResponse response = modelMapper.map(outDto, GetPostListResponse.class);
		return new BaseResponse<>(response);
	}


	@Operation(summary = "소분글 카테고리별 목록 조회", description = "소분글 카테고리별 목록 조회", tags = { "Post" })
	@GetMapping("/category-list/{categoryId}")
	public BaseResponse<GetPostListResponse> getPostList(@PathVariable Integer categoryId,
		@RequestParam(name = "sortBy") String sortBy, @PageableDefault(size = 9) Pageable pageable) {
		GetPostListOutDto outDto = postListService.getCategoryPostList(categoryId, sortBy, pageable);
		GetPostListResponse response = modelMapper.map(outDto, GetPostListResponse.class);
		return new BaseResponse<>(response);
	}

}
