package foiegras.ygyg.post.application.service;


import foiegras.ygyg.global.common.exception.BaseException;
import foiegras.ygyg.global.common.response.BaseResponseStatus;
import foiegras.ygyg.post.application.dto.postList.GetPostListOutDto;
import foiegras.ygyg.post.application.dto.postList.PageInfoDto;
import foiegras.ygyg.post.application.dto.postList.PostListItemDto;
import foiegras.ygyg.post.infrastructure.entity.ItemImageUrlEntity;
import foiegras.ygyg.post.infrastructure.entity.PostEntity;
import foiegras.ygyg.post.infrastructure.entity.SeasoningCategoryEntity;
import foiegras.ygyg.post.infrastructure.entity.UserPostEntity;
import foiegras.ygyg.post.infrastructure.jpa.post.ItemImageUrlJpaRepository;
import foiegras.ygyg.post.infrastructure.jpa.post.PostJpaRepository;
import foiegras.ygyg.post.infrastructure.jpa.post.SeasoningCategoryJpaRepository;
import foiegras.ygyg.post.infrastructure.jpa.post.UserPostJpaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostListService {

	// utils
	private final ModelMapper modelMapper;

	// repositories
	private final UserPostJpaRepository userPostJpaRepository;
	private final PostJpaRepository postJpaRepository;
	private final ItemImageUrlJpaRepository itemImageUrlJpaRepository;
	private final SeasoningCategoryJpaRepository seasoningCategoryJpaRepository;


	public GetPostListOutDto getPostList(String sortBy, Pageable newPageable) {
		Pageable pageable = PageRequest.of(newPageable.getPageNumber(), newPageable.getPageSize(), createSortBy(sortBy));

		// userPost 엔티티 페이지 목록 조회
		Page<UserPostEntity> userPostEntityPage = userPostJpaRepository.findAll(pageable);

		// 정렬 기준에 맞게 가져온 userPostEntities가 담긴 배열을 stream.map()으로 PostListItemDto로 변경
		List<PostListItemDto> items = userPostEntityPage.getContent().stream()
			.map(this::convertToListItemDto).toList();

		PageInfoDto pageInfoDto = PageInfoDto.builder()
			.totalItemsLength(userPostEntityPage.getTotalElements())
			.currentPage(userPostEntityPage.getNumber() + 1)
			.size(userPostEntityPage.getSize()).build();

		return GetPostListOutDto.builder()
			.items(items)
			.pageInfoDto(pageInfoDto).build();
	}


	public GetPostListOutDto getCategoryPostList(Integer categoryId, String sortBy, Pageable newPageable) {
		// 카테고리 id 검증 후 가져오기
		SeasoningCategoryEntity seasoningCategoryEntity = seasoningCategoryJpaRepository.findById(categoryId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CATEGORY));

		Pageable pageable = PageRequest.of(newPageable.getPageNumber(), newPageable.getPageSize(), createSortBy(sortBy));

		// 쿼리 메서드로 카테고리 객체의 id와 동일한 userpost 가져오기
		Page<UserPostEntity> userPostEntityPage = userPostJpaRepository
			.findAllBySeasoningCategoryEntityId(seasoningCategoryEntity.getId(), pageable);

		List<PostListItemDto> items = userPostEntityPage.getContent().stream()
			.map(this::convertToListItemDto).toList();

		PageInfoDto pageInfoDto = PageInfoDto.builder()
			.totalItemsLength(userPostEntityPage.getTotalElements())
			.currentPage(userPostEntityPage.getNumber() + 1)
			.size(userPostEntityPage.getSize()).build();

		return GetPostListOutDto.builder()
			.items(items)
			.pageInfoDto(pageInfoDto).build();

	}


	// 입력되는 정렬기준에 따라 각기 다른 Sort 객체 리턴
	private Sort createSortBy(String sortBy) {
		return switch (sortBy) {
			case "soonest" -> Sort.by(Sort.Direction.ASC, "portioningDate");
			case "lowestPrice" -> Sort.by(Sort.Direction.ASC, "expectedMinimumPrice");
			case "lowestRemain" -> Sort.by(Sort.Direction.ASC, "remainCount");
			default -> Sort.by(Sort.Direction.DESC, "createdAt");
		};
	}


	// 개별 userPostEntity => postListItem 매핑용 함수
	private PostListItemDto convertToListItemDto(UserPostEntity userPostEntity) {
		PostEntity postEntity = postJpaRepository.findByUserPostEntity(userPostEntity)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_POST_ENTITY));

		List<ItemImageUrlEntity> imageList = itemImageUrlJpaRepository.findByPostEntity(postEntity);
		String imageUrl = (imageList != null && !imageList.isEmpty()) ? imageList.get(0).getImageUrl() : null;
		return PostListItemDto.builder()
			.userPostId(userPostEntity.getId())
			.postTitle(userPostEntity.getPostTitle())
			.portioningDate(userPostEntity.getPortioningDate())
			.originalPrice(postEntity.getOriginalPrice())
			.minEngageCount(postEntity.getMinEngageCount())
			.maxEngageCount(postEntity.getMaxEngageCount())
			.currentEngageCount(postEntity.getCurrentEngageCount())
			.imageUrl(imageUrl).build();

	}

}
