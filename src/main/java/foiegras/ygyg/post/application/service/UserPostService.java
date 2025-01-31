package foiegras.ygyg.post.application.service;


import foiegras.ygyg.global.common.exception.BaseException;
import foiegras.ygyg.global.common.response.BaseResponseStatus;
import foiegras.ygyg.post.application.dto.userpost.in.GetMyPostListInDto;
import foiegras.ygyg.post.application.dto.userpost.in.GetUserPostListByCategoryInDto;
import foiegras.ygyg.post.application.dto.userpost.in.GetUserPostListInDto;
import foiegras.ygyg.post.application.dto.userpost.out.*;
import foiegras.ygyg.post.infrastructure.entity.ItemImageUrlEntity;
import foiegras.ygyg.post.infrastructure.entity.PostEntity;
import foiegras.ygyg.post.infrastructure.entity.SeasoningCategoryEntity;
import foiegras.ygyg.post.infrastructure.entity.UserPostEntity;
import foiegras.ygyg.post.infrastructure.jpa.post.ItemImageUrlJpaRepository;
import foiegras.ygyg.post.infrastructure.jpa.post.SeasoningCategoryJpaRepository;
import foiegras.ygyg.post.infrastructure.jpa.post.UserPostJpaRepository;
import foiegras.ygyg.post.infrastructure.querydsl.UserPostQueryDslRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class UserPostService {

	// value
	private static final String JOIN = "join";
	private static final String CANCEL = "cancel";
	// repository
	private final UserPostJpaRepository userPostJpaRepository;
	private final UserPostQueryDslRepository userPostQueryDslRepository;
	// todo: 반드시 분리할 것.
	private final ItemImageUrlJpaRepository itemImageUrlJpaRepository;
	// todo: 반드시 분리할 것.
	private final SeasoningCategoryJpaRepository seasoningCategoryJpaRepository;
	// util
	private final ModelMapper modelMapper;


	/**
	 * UserPostService
	 * 1. id로 UserPost 조회
	 * 2. 소분글 참여하기
	 * 3. 참여 가능 남은 인원수 업데이트
	 * 4. 최소 참여 인원 달성 여부 업데이트
	 * 5. 작성자 UUID로 진행중인 소분글 조회
	 * 6. 소분글 리스트 조회
	 * 7. 카테고리로 소분글 리스트 조회
	 * 8. 타입별 내 소분글 리스트 조회
	 */

	// 1. id로 UserPost 조회
	public UserPostEntity getUserPostById(Long userPostId) {
		return userPostJpaRepository.findById(userPostId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_POST));
	}


	// 2. 소분글 참여하기
	public UserPostEntity joinPortioning(UserPostEntity userPost, Integer currentEngageCount, Integer minEngageCount) {
		// 참여 가능 남은 인원수 업데이트
		this.updateRemainCount(userPost, JOIN);
		// 최소 참여 인원 달성 여부 업데이트
		return this.updateIsFullMinimum(userPost, currentEngageCount, minEngageCount);
	}


	// 3. 참여 가능 남은 인원수 업데이트
	public UserPostEntity updateRemainCount(UserPostEntity userPost, String type) {
		return userPost.updateRemainCount(type);
	}


	// 4. 최소 참여 인원 달성 여부 업데이트
	public UserPostEntity updateIsFullMinimum(UserPostEntity userPost, Integer currentEngageCount, Integer minEngageCount) {
		return userPost.updateIsFullMinimum(currentEngageCount, minEngageCount);
	}


	// 5. 작성자 UUID로 진행중인 소분글 조회
	public List<UserPostEntity> findNotFinishedUserPostListByUserUuid(UUID writerUuid, LocalDateTime deletedAt) {
		return userPostQueryDslRepository.findNotFinishedUserPostByUserUuid(writerUuid, deletedAt);
	}


	// 6. 소분글 리스트 조회
	public GetUserPostListOutDto getUserPostList(GetUserPostListInDto inDto) {
		Pageable newPageable = inDto.getPageable();
		Pageable pageable = PageRequest.of(newPageable.getPageNumber(), newPageable.getPageSize(), createSortBy(inDto.getSortBy()));

		// 최소소분인원 충족여부에 알맞은 userPost 엔티티 페이지 목록 조회
		Page<UserPostEntity> userPostEntityPage;
		if (inDto.getIsMinimumPeopleMet().equals(true)) {
			userPostEntityPage = userPostJpaRepository.findByIsFullMinimumTrue(pageable);
		} else {
			userPostEntityPage = userPostJpaRepository.findAll(pageable);
		}

		// 정렬 기준에 맞게 가져온 userPostEntities가 담긴 배열을 stream.map()으로 PostListItemDto로 변경
		List<UserPostItemDto> items = userPostEntityPage.getContent().stream()
			.map(this::convertToListItemDto).toList();

		PageInfoDto pageInfoDto = PageInfoDto.builder()
			.totalItemsLength(userPostEntityPage.getTotalElements())
			.currentPage(userPostEntityPage.getNumber() + 1)
			.size(userPostEntityPage.getContent().size()).build();

		return GetUserPostListOutDto.builder()
			.items(items)
			.pageInfoDto(pageInfoDto).build();
	}


	// 7. 카테고리로 소분글 리스트 조회 todo: facade로 분리
	public GetUserPostListOutDto getUserPostListByCategory(GetUserPostListByCategoryInDto inDto) {
		Pageable newPageable = inDto.getPageable();
		// 카테고리 id 검증 후 가져오기
		SeasoningCategoryEntity seasoningCategoryEntity = seasoningCategoryJpaRepository.findById(inDto.getCategoryId())
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CATEGORY));

		Pageable pageable = PageRequest.of(newPageable.getPageNumber(), newPageable.getPageSize(), createSortBy(inDto.getSortBy()));

		Page<UserPostEntity> userPostEntityPage;
		if (inDto.getIsMinimumPeopleMet().equals(true)) {
			userPostEntityPage = userPostJpaRepository.findAllBySeasoningCategoryEntityIdAndIsFullMinimumTrue(seasoningCategoryEntity.getId(), pageable);
		} else {
			userPostEntityPage = userPostJpaRepository.findAllBySeasoningCategoryEntityId(seasoningCategoryEntity.getId(), pageable);
		}

		List<UserPostItemDto> items = userPostEntityPage.getContent().stream()
			.map(this::convertToListItemDto).toList();

		PageInfoDto pageInfoDto = PageInfoDto.builder()
			.totalItemsLength(userPostEntityPage.getTotalElements())
			.currentPage(userPostEntityPage.getNumber() + 1)
			.size(userPostEntityPage.getContent().size()).build();

		return GetUserPostListOutDto.builder()
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


	// 개별 userPostEntity => postListItem 매핑용 함수 //todo: facade로 분리
	private UserPostItemDto convertToListItemDto(UserPostEntity userPostEntity) {
		PostEntity postEntity = userPostEntity.getPostEntity();
		List<ItemImageUrlEntity> imageList = itemImageUrlJpaRepository.findByPostEntity(postEntity);
		String imageUrl = (imageList != null && !imageList.isEmpty()) ? imageList.get(0).getImageUrl() : null;
		return UserPostItemDto.builder()
			.userPostId(userPostEntity.getId())
			.postTitle(userPostEntity.getPostTitle())
			.portioningDate(userPostEntity.getPortioningDate())
			.originalPrice(postEntity.getOriginalPrice())
			.minEngageCount(postEntity.getMinEngageCount())
			.maxEngageCount(postEntity.getMaxEngageCount())
			.currentEngageCount(postEntity.getCurrentEngageCount())
			.imageUrl(imageUrl).build();
	}


	// 8. 타입별 내 소분글 리스트 조회
	public GetUserPostListByCursorOutDto getMyPostListByType(GetMyPostListInDto inDto) {
		UserPostListQueryDataByCursorOutDto queryData = userPostQueryDslRepository.findPostListByCursor(inDto);
		return GetUserPostListByCursorOutDto.builder()
			.content(queryData.getContent())
			.hasNext(queryData.hasNext())
			.pageable(queryData.getPageable())
			.lastCursor(queryData.getLastCursor())
			.build();
	}

}
