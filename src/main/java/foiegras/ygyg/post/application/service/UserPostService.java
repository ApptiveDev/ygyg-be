package foiegras.ygyg.post.application.service;


import foiegras.ygyg.global.common.exception.BaseException;
import foiegras.ygyg.global.common.response.BaseResponseStatus;
import foiegras.ygyg.post.infrastructure.entity.UserPostEntity;
import foiegras.ygyg.post.infrastructure.jpa.UserPostJpaRepository;
import foiegras.ygyg.post.infrastructure.querydsl.UserPostQueryDslRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
	// util
	private final ModelMapper modelMapper;


	/**
	 * UserPostService
	 * 1. id로 UserPost 조회
	 * 2. 소분글 참여하기
	 * 3. 참여 가능 남은 인원수 업데이트
	 * 4. 최소 참여 인원 달성 여부 업데이트
	 * 5. 작성자 UUID로 진행중인 소분글 조회
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

}
