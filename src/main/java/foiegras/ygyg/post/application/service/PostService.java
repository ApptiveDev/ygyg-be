package foiegras.ygyg.post.application.service;


import foiegras.ygyg.global.common.exception.BaseException;
import foiegras.ygyg.global.common.response.BaseResponseStatus;
import foiegras.ygyg.post.application.dto.post.in.*;
import foiegras.ygyg.post.application.dto.post.out.GetPostOutDto;
import foiegras.ygyg.post.application.dto.post.out.PostDataOutDto;
import foiegras.ygyg.post.application.dto.userpost.in.UserPostDataInDto;
import foiegras.ygyg.post.application.dto.userpost.out.UserPostDataOutDto;
import foiegras.ygyg.post.infrastructure.entity.*;
import foiegras.ygyg.post.infrastructure.jpa.post.*;
import foiegras.ygyg.user.infrastructure.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

	// utils
	private final ModelMapper modelMapper;

	// repositories
	private final UserPostJpaRepository userPostJpaRepository;
	private final PostJpaRepository postJpaRepository;
	private final ItemImageUrlJpaRepository itemImageUrlJpaRepository;
	private final ItemPortioningUnitJpaRepository itemPortioningUnitJpaRepository;
	private final SeasoningCategoryJpaRepository seasoningCategoryJpaRepository;
	private final ParticipatingUsersJpaRepository participatingUsersJpaRepository;
	private final UserJpaRepository userJpaRepository;


	/**
	 * PostService
	 * 1. 소분글 생성
	 * 2. 소분글 상세조회
	 * 3. 소분글 수정
	 * 4. 소분글 삭제
	 */

	// 1. 소분글 생성
	public void createPost(CreatePostInDto inDto) {
		// 요청받은 각 카테고리, 소분단위 pk로 알맞는 엔티티 가져오기
		SeasoningCategoryEntity categoryEntity = seasoningCategoryJpaRepository
			.findById(inDto.getSeasoningCategoryId())
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CATEGORY));

		ItemPortioningUnitEntity unitEntity = itemPortioningUnitJpaRepository
			.findById(inDto.getUnitId())
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_UNIT));

		// post 엔티티 저장
		PostDataInDto postDataInDto = modelMapper.map(inDto.getPostDataInDto(), PostDataInDto.class);

		PostEntity postEntity = modelMapper.map(postDataInDto, PostEntity.class)
			.toBuilder()
			.itemPortioningUnitEntity(unitEntity)
			.build();
		PostEntity savedPost = postJpaRepository.save(postEntity);

		// req엔 값이 없어 매핑 후에도 null로 된 속성들 값 채우기
		UserPostDataInDto userPostDataInDto = modelMapper.map(inDto.getUserPostDataInDto(), UserPostDataInDto.class);

		// tobuilder로 기생성된 인스턴스에 연관관계 객체 추가적으로 매핑
		UserPostEntity userPostEntity = modelMapper.map(userPostDataInDto, UserPostEntity.class)
			.toBuilder()
			.writerUuid(inDto.getWriterUuid())
			.expectedMinimumPrice(inDto.getPostDataInDto().getOriginalPrice() / inDto.getPostDataInDto().getMaxEngageCount())
			.remainCount(inDto.getPostDataInDto().getMaxEngageCount() - 1)
			.isFullMinimum(false)
			.seasoningCategoryEntity(categoryEntity)
			.postEntity(savedPost)
			.build();
		UserPostEntity savedUserPost = userPostJpaRepository.save(userPostEntity);

		// 게시자 추가
		ParticipatingUsersEntity participatingUser = ParticipatingUsersEntity.builder()
			.participatingUserUUID(inDto.getWriterUuid())
			.userPostEntity(savedUserPost) // userpost의 jpa 리턴값이 매핑되는 엔티티 인자로 바로 활용되는 포인트
			.build();
		participatingUsersJpaRepository.save(participatingUser);

		// ItemImageUrl 생성 및 저장
		ItemImageUrlEntity imageUrlEntity = ItemImageUrlEntity.builder()
			.imageUrl(inDto.getImageUrl())
			.postEntity(savedPost)
			.build();
		itemImageUrlJpaRepository.save(imageUrlEntity);
	}


	// 2. 소분글 상세 조회
	@Transactional(readOnly = true)
	public GetPostOutDto getPost(GetPostInDto inDto) {

		// 연관 엔티티들 조회
		UserPostEntity userPostEntity = userPostJpaRepository.findById(inDto.getUserPostId())
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER_POST_ENTITY));

		List<ParticipatingUsersEntity> participatingUsersEntity = participatingUsersJpaRepository.findByUserPostEntity(userPostEntity);
		if (participatingUsersEntity.isEmpty()) throw new BaseException(BaseResponseStatus.NO_EXIST_PARTICIPATING_USERS);

		PostEntity postEntity = userPostEntity.getPostEntity();

		List<ItemImageUrlEntity> itemImageUrlEntity = itemImageUrlJpaRepository.findByPostEntity(postEntity);
		if (itemImageUrlEntity.isEmpty()) throw new BaseException(BaseResponseStatus.NO_EXIST_ITEM_IMAGE_URL_ENTITY);

		// outdto 필드들 구성
		UserPostDataOutDto userPostDataOutDto = modelMapper.map(userPostEntity, UserPostDataOutDto.class);
		if (userJpaRepository.findByUserUuid(userPostDataOutDto.getWriterUuid()).isEmpty()) {
			// 소분글 작성자가 탈퇴유저라면 UUID를 null로 설정
			// todo: post 도메인에서 user 도메인 의존하는 부분 리팩토링
			userPostDataOutDto.toBuilder().writerUuid(null).build();
		}
		PostDataOutDto postDataOutDto = modelMapper.map(postEntity, PostDataOutDto.class);
		String imageUrl = itemImageUrlEntity.get(0).getImageUrl(); // 아직은 이미지 한개만 첨부하므로
		String unitName = postEntity.getItemPortioningUnitEntity().getUnit();
		String categoryName = userPostEntity.getSeasoningCategoryEntity().getCategoryName();
		Boolean userParticipatingIn = participatingUsersJpaRepository
			.findByUserPostEntityAndParticipatingUserUUID(userPostEntity, inDto.getUserUuid()).isPresent();

		// 리턴할 OutDto 매핑
		return GetPostOutDto.builder()
			.userPostDataOutDto(userPostDataOutDto)
			.postDataOutDto(postDataOutDto)
			.imageUrl(imageUrl)
			.unitName(unitName)
			.categoryName(categoryName)
			.userParticipatingIn(userParticipatingIn)
			.build();

	}


	//3. 소분글 수정
	public void updatePost(UpdatePostInDto inDto) {
		// 관련 엔티티 가져오기
		UserPostEntity userPostEntity = userPostJpaRepository.findById(inDto.getUserPostId())
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER_POST_ENTITY));
		if (!userPostEntity.getWriterUuid().equals(inDto.getUserUuid())) // API 호출자가 소분글 게시자인지 체크
			throw new BaseException(BaseResponseStatus.NO_ACCESS_AUTHORITY);
		PostEntity postEntity = userPostEntity.getPostEntity();
		SeasoningCategoryEntity seasoningCategoryEntity = seasoningCategoryJpaRepository.findById(inDto.getSeasoningCategoryId())
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CATEGORY));
		ItemPortioningUnitEntity itemUnitEntity = itemPortioningUnitJpaRepository.findById(inDto.getUnitId())
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_UNIT));
		List<ItemImageUrlEntity> existingImageUrls = itemImageUrlJpaRepository.findByPostEntity(postEntity);

		// userpost 수정값 영향받는 값들 최신화 및 수정본 저장
		UserPostEntity updatedUserPostEntity = userPostEntity.toBuilder()
			.postTitle(inDto.getUserPostDataInDto().getPostTitle())
			.portioningDate(inDto.getUserPostDataInDto().getPortioningDate())
			.seasoningCategoryEntity(seasoningCategoryEntity)
			.expectedMinimumPrice(inDto.getPostDataInDto().getOriginalPrice() / inDto.getPostDataInDto().getMaxEngageCount())
			.remainCount(inDto.getPostDataInDto().getMaxEngageCount() - postEntity.getCurrentEngageCount())
			.build();
		updatedUserPostEntity.updateIsFullMinimum(postEntity.getCurrentEngageCount(), inDto.getPostDataInDto().getMinEngageCount());
		userPostJpaRepository.save(updatedUserPostEntity);

		// post 수정본 저장
		PostEntity updatedPostEntity = modelMapper.map(inDto.getPostDataInDto(), PostEntity.class);
		updatedPostEntity = updatedPostEntity.toBuilder()
			.id(postEntity.getId())
			.currentEngageCount(postEntity.getCurrentEngageCount())
			.itemPortioningUnitEntity(itemUnitEntity).build();
		postJpaRepository.save(updatedPostEntity);

		// 상품사진 url 수정
		if (existingImageUrls != null && !existingImageUrls.isEmpty()) {
			ItemImageUrlEntity existingImageUrl = existingImageUrls.get(0);
			existingImageUrl = existingImageUrl.toBuilder()
				.imageUrl(inDto.getImageUrl()).build();
			itemImageUrlJpaRepository.save(existingImageUrl);
		} else { // 만약 처음이 아니라 수정 때 사진을 업로드 할 경우
			ItemImageUrlEntity newImage = ItemImageUrlEntity.builder()
				.postEntity(postEntity)
				.imageUrl(inDto.getImageUrl())
				.build();
			itemImageUrlJpaRepository.save(newImage);

		}

	}


	// 4. 소분글 삭제
	public void deletePost(DeletePostInDto inDto) {
		UserPostEntity userPostEntity = userPostJpaRepository.findById(inDto.getUserPostId())
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER_POST_ENTITY));
		if (!userPostEntity.getWriterUuid().equals(inDto.getUserUuid())) // API 호출자가 소분글 게시자인지 체크
			throw new BaseException(BaseResponseStatus.NO_ACCESS_AUTHORITY);
		PostEntity postEntity = userPostEntity.getPostEntity();

		// 각 엔티티들 삭제
		itemImageUrlJpaRepository.deleteByPostEntity(postEntity);
		participatingUsersJpaRepository.deleteByUserPostEntity(userPostEntity);
		userPostJpaRepository.delete(userPostEntity);
		postJpaRepository.delete(postEntity);
	}


	// 5. 현재 참여인원 수정
	public PostEntity updateCurrentEngageCount(PostEntity postEntity, String type) {
		return postEntity.updateCurrentEngageCount(type);
	}

}


