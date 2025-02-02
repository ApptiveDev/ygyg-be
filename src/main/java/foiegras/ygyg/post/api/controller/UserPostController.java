package foiegras.ygyg.post.api.controller;


import foiegras.ygyg.global.common.response.BaseResponse;
import foiegras.ygyg.global.common.security.CustomUserDetails;
import foiegras.ygyg.post.api.request.GetMyPostListRequest;
import foiegras.ygyg.post.api.response.GetUserPostListByCursorResponse;
import foiegras.ygyg.post.api.response.GetUserPostListResponse;
import foiegras.ygyg.post.application.dto.userpost.in.*;
import foiegras.ygyg.post.application.dto.userpost.out.GetUserPostListByCursorOutDto;
import foiegras.ygyg.post.application.dto.userpost.out.GetUserPostListOutDto;
import foiegras.ygyg.post.application.facade.JoinPortioningFacade;
import foiegras.ygyg.post.application.service.UserPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class UserPostController {

	// service
	private final JoinPortioningFacade joinPortioningFacade;
	private final UserPostService userPostService;
	// util
	private final ModelMapper modelMapper;


	/**
	 * UserPostController
	 * 1. 소분글 참여하기
	 * 2. 소분글 전체목록 조회
	 * 3. 소분글 카테고리별 목록 조회
	 * 4. 타입별 소분글 목록 조회
	 * 5. 제목으로 소분글 검색
	 */

	// 1. 소분글 참여하기
	@Operation(summary = "소분글 참여하기", description = "소분글 참여하기", tags = { "Post" })
	@PostMapping("/join/{userPostId}")
	@SecurityRequirement(name = "Bearer Auth")
	public BaseResponse<Void> joinPortioning(@PathVariable("userPostId") Long userPostId, @AuthenticationPrincipal CustomUserDetails authentication) {
		joinPortioningFacade.joinPortioning(new JoinPortioningInDto(userPostId, authentication));
		return new BaseResponse<>();
	}


	// 2. 소분글 전체목록 조회
	@Operation(summary = "소분글 전체목록 조회", description = "소분글 전체목록 조회", tags = { "Post" })
	@GetMapping("/list")
	public BaseResponse<GetUserPostListResponse> getPostList(@RequestParam(name = "sortBy") String sortBy,
		@PageableDefault(size = 9) Pageable pageable, @RequestParam(name = "isMinimumPeopleMet") Boolean isMinimumPeopleMet) {
		GetUserPostListOutDto outDto = userPostService.getUserPostList(new GetUserPostListInDto(sortBy, isMinimumPeopleMet, pageable));
		GetUserPostListResponse response = modelMapper.map(outDto, GetUserPostListResponse.class);
		return new BaseResponse<>(response);
	}


	// 3. 소분글 카테고리별 목록 조회
	@Operation(summary = "소분글 카테고리별 목록 조회", description = "소분글 카테고리별 목록 조회", tags = { "Post" })
	@GetMapping("/list/{categoryId}")
	public BaseResponse<GetUserPostListResponse> getPostList(@PathVariable Integer categoryId,
		@RequestParam(name = "sortBy") String sortBy, @PageableDefault(size = 9) Pageable pageable, @RequestParam(name = "isMinimumPeopleMet") Boolean isMinimumPeopleMet) {
		GetUserPostListOutDto outDto = userPostService.getUserPostListByCategory(new GetUserPostListByCategoryInDto(categoryId, sortBy, isMinimumPeopleMet, pageable));
		GetUserPostListResponse response = modelMapper.map(outDto, GetUserPostListResponse.class);
		return new BaseResponse<>(response);
	}


	// 4. 타입별 내 소분글 목록 조회
	@Operation(summary = "타입별 내 소분글 목록 조회", description = "type: written(내가 작성), join(내가 참여), complete(소분 완료)", tags = { "Post" })
	@GetMapping("/my/list")
	@SecurityRequirement(name = "Bearer Auth")
	public BaseResponse<GetUserPostListByCursorResponse> getMyPostListByType(GetMyPostListRequest request, Pageable pageable, @AuthenticationPrincipal CustomUserDetails authentication) {
		GetMyPostListInDto inDto = modelMapper.map(request, GetMyPostListInDto.class);
		inDto = inDto.toBuilder()
			.pageable(pageable)
			.userUuid(authentication.getUserUuid())
			.currentTime(LocalDateTime.now())
			.build();
		GetUserPostListByCursorOutDto outDto = userPostService.getMyPostListByType(inDto);
		GetUserPostListByCursorResponse response = modelMapper.map(outDto, GetUserPostListByCursorResponse.class);
		return new BaseResponse<>(response.toBuilder().myPost(outDto.getContent()).build());
	}


	// 5. 제목으로 소분글 검색
	@Operation(summary = "제목으로 소분글 검색", description = "제목으로 소분글 목록을 조회한다. sortBy type은 latest, soonest, lowestPrice, lowestRemain 네 가지중 하나를 택한다", tags = { "Post" })
	@GetMapping("/search")
	public BaseResponse<GetUserPostListResponse> searchPostList(@RequestParam(name = "keyword") String keyword, @RequestParam(name = "isMinimumPeopleMet") Boolean isMinimumPeopleMet,
		@RequestParam(name = "sortBy") String sortBy, @PageableDefault(size = 9) Pageable pageable) {
		SearchPostInDto inDto = SearchPostInDto.builder()
			.keyword(keyword)
			.isMinimumPeopleMet(isMinimumPeopleMet)
			.sortBy(sortBy)
			.pageable(pageable)
			.build();
		GetUserPostListOutDto outDto = userPostService.searchPost(inDto);
		GetUserPostListResponse response = modelMapper.map(outDto, GetUserPostListResponse.class);
		return new BaseResponse<>(response);
	}

}
