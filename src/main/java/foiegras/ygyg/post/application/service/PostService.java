package foiegras.ygyg.post.application.service;


import foiegras.ygyg.post.application.dto.in.CreatePostInDto;
import foiegras.ygyg.post.infrastructure.entity.*;
import foiegras.ygyg.post.infrastructure.jpa.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PostService {

	// repositories
	private final ModelMapper modelMapper;
	private final UserPostJpaRepository userPostJpaRepository;
	private final PostJpaRepository postJpaRepository;
	private final ItemImageUrlJpaRepository itemImageUrlJpaRepository;
	private final ItemPortioningUnitJpaRepository itemPortioningUnitJpaRepository;
	private final SeasoningCategoryJpaRepository seasoningCategoryJpaRepository;
	private final ParticipatingUsersJpaRepository participatingUsersJpaRepository;


	/**
	 * PostService
	 * 1. 소분글 생성
	 * 2. 소분글 수정
	 * 3. 소분글 삭제
	 * 4. 소분글 상세조회
	 */

	// 1. 소분글 생성
	@Transactional
	public void createPost(CreatePostInDto indto) {

		// JPA는 find 시 리턴값이 pk라서 인덱스값 얻는데 곧바로 활용가능
		SeasoningCategoryEntity categoryId = seasoningCategoryJpaRepository
			.findByCategoryName(indto.getCategoryName())
			.orElseThrow(() -> new RuntimeException("Category not found"));

		ItemPortioningUnitEntity unitId = itemPortioningUnitJpaRepository
			.findByUnit(indto.getUnit())
			.orElseThrow(() -> new RuntimeException("Unit not found"));

		// UserPost 생성 및 저장
		UserPostEntity userPostEntity = UserPostEntity.builder()
			.seasoningCategoryEntity(categoryId) // fk 매핑!
			.writerUuid(UUID.fromString(indto.getWriterUuid()))
			.postTitle(indto.getPostTitle())
			.expectedMinimumPrice(calculateMinimumPrice(indto.getOriginalPrice(), indto.getMaxEngageCount()))
			.remainCount(indto.getMaxEngageCount() - 1) // 작성자 본인 1명 차감
			.isFullMinimum(false)
			.portioningDate(LocalDateTime.parse(indto.getPortioningDate()))
			.build();
		UserPostEntity savedUserPost = userPostJpaRepository.save(userPostEntity);

		// ParticipatingUsersEntity에 게시자 추가
		ParticipatingUsersEntity participatingUser = ParticipatingUsersEntity.builder()
			.participatingUserUUID(UUID.fromString(indto.getWriterUuid()))
			.userPostEntity(savedUserPost) // userpost의 jpa 리턴값이 매핑되는 엔티티의 fk값 저장에 활용되는 포인트
			.build();
		participatingUsersJpaRepository.save(participatingUser);

		// Post 생성 및 저장
		PostEntity postEntity = PostEntity.builder()
			.userPostEntity(savedUserPost)
			.itemPortioningUnitEntity(unitId)
			.onlinePurchaseUrl(indto.getOnlinePurchaseUrl())
			.originalPrice(Integer.parseInt(indto.getOriginalPrice()))
			.amount(Integer.parseInt(indto.getAmount()))
			.minEngageCount(indto.getMinEngageCount())
			.maxEngageCount(indto.getMaxEngageCount())
			.currentEngageCount(1) // 작성자 포함 1명으로 시작
			.portioningPlaceLatitude(Double.parseDouble(indto.getPortioningPlaceLatitude()))
			.portioningPlaceLongitude(Double.parseDouble(indto.getPortioningPlaceLongitude()))
			.description(indto.getDescription())
			.portioningPlaceAddress(indto.getPortioningPlaceAddress())
			.portioningPlaceDetailAddress(indto.getPortioningPlaceDetailAddress())
			.build();
		PostEntity savedPost = postJpaRepository.save(postEntity);

		// ItemImageUrl 생성 및 저장
		ItemImageUrlEntity imageUrlEntity = ItemImageUrlEntity.builder()
			.imageUrl(indto.getImageUrl())
			.postEntity(savedPost)
			.build();
		itemImageUrlJpaRepository.save(imageUrlEntity);

		// ItemPortioningUnit 생성 및 저장
		ItemPortioningUnitEntity itemPortioningUnitEntity = ItemPortioningUnitEntity.builder()
			.unit(indto.getUnit())
			.build();
		itemPortioningUnitJpaRepository.save(itemPortioningUnitEntity);
	}


	// 예상 최소소분가 계산용 함수
	private Integer calculateMinimumPrice(String originalPrice, Integer maxEngageCount) {
		return Integer.parseInt(originalPrice) / maxEngageCount;
	}

}


