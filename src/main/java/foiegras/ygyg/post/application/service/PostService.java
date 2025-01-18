package foiegras.ygyg.post.application.service;


import foiegras.ygyg.global.common.exception.BaseException;
import foiegras.ygyg.global.common.response.BaseResponseStatus;
import foiegras.ygyg.post.application.dto.post.in.CreatePostInDto;
import foiegras.ygyg.post.application.dto.post.in.GetPostInDto;
import foiegras.ygyg.post.application.dto.post.in.PostDataInDto;
import foiegras.ygyg.post.application.dto.post.in.UserPostDataInDto;
import foiegras.ygyg.post.application.dto.post.out.GetPostOutDto;
import foiegras.ygyg.post.application.dto.post.out.PostDataOutDto;
import foiegras.ygyg.post.application.dto.post.out.UserPostDataOutDto;
import foiegras.ygyg.post.infrastructure.entity.*;
import foiegras.ygyg.post.infrastructure.jpa.post.*;
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


	/**
	 * PostService
	 * 1. 소분글 생성
	 * 2. 소분글 상세조회
	 * 3. 소분글 수정
	 * 4. 소분글 삭제
	 */

	// 1. 소분글 생성
	public void createPost(CreatePostInDto indto) {

		// 요청받은 각 카테고리, 소분단위 pk로 알맞는 엔티티 가져오기
		SeasoningCategoryEntity categoryEntity = seasoningCategoryJpaRepository
			.findById(indto.getCategoryId())
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CATEGORY));

		ItemPortioningUnitEntity unitEntity = itemPortioningUnitJpaRepository
			.findById(indto.getUnitId())
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_UNIT));

		// req엔 값이 없어 매핑 후에도 null로 된 속성들 값 채우기
		UserPostDataInDto userPostDataInDto = modelMapper.map(indto.getUserPostDataInDto(), UserPostDataInDto.class)
			.toBuilder()
			.expectedMinimumPrice(indto.getPostDataInDto().getOriginalPrice() / indto.getPostDataInDto().getMaxEngageCount())
			.remainCount(indto.getPostDataInDto().getMaxEngageCount() - 1)
			.isFullMinimum(false)
			.build();

		// tobuilder로 기생성된 인스턴스에 연관관계 객체 추가적으로 매핑
		UserPostEntity userPostEntity = modelMapper.map(userPostDataInDto, UserPostEntity.class)
			.toBuilder()
			.seasoningCategoryEntity(categoryEntity)
			.build();
		UserPostEntity savedUserPost = userPostJpaRepository.save(userPostEntity);

		// 게시자 추가
		ParticipatingUsersEntity participatingUser = ParticipatingUsersEntity.builder()
			.participatingUserUUID(indto.getUserPostDataInDto().getWriterUuid())
			.userPostEntity(savedUserPost) // userpost의 jpa 리턴값이 매핑되는 엔티티 인자로 바로 활용되는 포인트
			.build();
		participatingUsersJpaRepository.save(participatingUser);

		// post 엔티티 저장
		PostDataInDto postDataInDto = modelMapper.map(indto.getPostDataInDto(), PostDataInDto.class);

		PostEntity postEntity = modelMapper.map(postDataInDto, PostEntity.class)
			.toBuilder()
			.userPostEntity(savedUserPost)
			.itemPortioningUnitEntity(unitEntity)
			.build();
		PostEntity savedPost = postJpaRepository.save(postEntity);

		// ItemImageUrl 생성 및 저장
		ItemImageUrlEntity imageUrlEntity = ItemImageUrlEntity.builder()
			.imageUrl(indto.getImageUrl())
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

		PostEntity postEntity = postJpaRepository.findByUserPostEntity(userPostEntity)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_POST_ENTITY));

		List<ItemImageUrlEntity> itemImageUrlEntity = itemImageUrlJpaRepository.findByPostEntity(postEntity);
		if (itemImageUrlEntity.isEmpty()) throw new BaseException(BaseResponseStatus.NO_EXIST_ITEM_IMAGE_URL_ENTITY);

		// Composition 적용한 dto 매핑
		UserPostDataOutDto userPostDataOutDto = modelMapper.map(userPostEntity, UserPostDataOutDto.class);
		PostDataOutDto postDataOutDto = modelMapper.map(postEntity, PostDataOutDto.class);

		// 나머지 outDto 필드
		String imageUrl = itemImageUrlEntity.get(0).getImageUrl(); // 아직은 이미지 한개만 첨부하므로
		String unitName = postEntity.getItemPortioningUnitEntity().getUnit();
		String categoryName = userPostEntity.getSeasoningCategoryEntity().getCategoryName();
		Boolean writerActiveState = participatingUsersJpaRepository
			.findByUserPostEntityAndParticipatingUserUUID(userPostEntity, inDto.getUserUuid()).isPresent();

		// 리턴할 OutDto 매핑
		return GetPostOutDto.builder()
			.userPostDataOutDto(userPostDataOutDto)
			.postDataOutDto(postDataOutDto)
			.imageUrl(imageUrl)
			.unitName(unitName)
			.categoryName(categoryName)
			.writerActiveState(writerActiveState)
			.build();

	}

}


