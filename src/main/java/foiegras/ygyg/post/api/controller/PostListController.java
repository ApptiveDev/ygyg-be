package foiegras.ygyg.post.api.controller;


import foiegras.ygyg.global.common.response.BaseResponse;
import foiegras.ygyg.post.api.response.GetPostListResponse;
import foiegras.ygyg.post.application.dto.postList.GetPostListInDto;
import foiegras.ygyg.post.application.dto.postList.GetPostListOutDto;
import foiegras.ygyg.post.application.service.PostListService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/list")
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
	@GetMapping()
	public BaseResponse<GetPostListResponse> getPostList(@RequestParam String sortBy,
		@RequestParam Integer page, @RequestParam(defaultValue = "9") Integer size) {
		GetPostListInDto inDto = GetPostListInDto.builder()
			.sortBy(sortBy)
			.page(page)
			.size(size).build();
		GetPostListOutDto outDto = postListService.getPostList(inDto);
		GetPostListResponse response = modelMapper.map(outDto, GetPostListResponse.class);
		return new BaseResponse<>(response);
	}

}
