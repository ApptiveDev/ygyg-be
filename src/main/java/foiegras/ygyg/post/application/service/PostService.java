package foiegras.ygyg.post.application.service;


import foiegras.ygyg.global.common.exception.BaseException;
import foiegras.ygyg.global.common.response.BaseResponseStatus;
import foiegras.ygyg.post.application.dto.in.CreatePostInDto;
import foiegras.ygyg.post.application.dto.in.PostDataInDto;
import foiegras.ygyg.post.application.dto.in.UserPostDataInDto;
import foiegras.ygyg.post.infrastructure.entity.*;
import foiegras.ygyg.post.infrastructure.jpa.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


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

}


