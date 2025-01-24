package foiegras.ygyg.post.application.facade;


import foiegras.ygyg.global.common.exception.BaseException;
import foiegras.ygyg.global.common.response.BaseResponseStatus;
import foiegras.ygyg.post.application.dto.userpost.in.JoinPortioningInDto;
import foiegras.ygyg.post.application.service.ParticipatingUserService;
import foiegras.ygyg.post.application.service.PostService;
import foiegras.ygyg.post.application.service.UserPostService;
import foiegras.ygyg.post.infrastructure.entity.PostEntity;
import foiegras.ygyg.post.infrastructure.entity.UserPostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class JoinPortioningFacade {

	// value
	private static final String JOIN = "join";
	// service
	private final PostService postService;
	private final UserPostService userPostService;
	private final ParticipatingUserService participatingUserService;


	/**
	 * ParticipatePortioningFacade
	 * - 소분글 참여하기
	 * 1. 참여 가능 남은 인원수 확인
	 * 2-1 참여 가능하다면 소분글 참여
	 * 2-2. 불가능하다면 exception
	 */

	// 소분글 참여하기
	@Retryable(
		retryFor = { OptimisticLockingFailureException.class }, // 재시도 대상 exception
		maxAttempts = 3, // 재시도 횟수
		backoff = @Backoff(delay = 500), // 재시도 딜레이
		recover = "joinPortioningRecover" // 재시도 실패시 호출할 메소드
	)
	public void joinPortioning(JoinPortioningInDto joinPortioningInDto) {
		// 참여자 UUID
		UUID participantUuid = joinPortioningInDto.getAuthentication().getUserUuid();
		// 소분글 조회
		UserPostEntity userPost = userPostService.getUserPostById(joinPortioningInDto.getUserPostId());
		// 본인 참여 여부 확인 -> 이미 참여중이라면 exception
		if (participatingUserService.checkParticipatedState(participantUuid, userPost)) {
			throw new BaseException(BaseResponseStatus.ALREADY_PARTICIPATED);
		}
		// 참여 가능 인원수가 0보다 큰 경우(참여 가능한 경우) 참여
		if (userPost.getRemainCount() > 0) {
			// 현재 참여자 수 증가
			PostEntity updatedPost = postService.updateCurrentEngageCount(userPost.getPostEntity(), JOIN);
			// 소분글 참여
			UserPostEntity updatedUserPost = userPostService.joinPortioning(userPost, updatedPost.getCurrentEngageCount(), updatedPost.getMinEngageCount());
			// 소분글 참여자 생성
			participatingUserService.createParticipatingUser(participantUuid, updatedUserPost);
		}
		// 참여 가능 인원수가 0인 경우(참여 불가능한 경우) throw exception
		else {
			throw new BaseException(BaseResponseStatus.CAPACITY_REACHED);
		}
	}


	/**
	 * Recover: retry 실패시 예외처리
	 */
	@Recover
	private void joinPortioningRecover(RuntimeException e, JoinPortioningInDto joinPortioningInDto) {
		if (e instanceof OptimisticLockingFailureException) {
			throw new BaseException(BaseResponseStatus.JOIN_PORTIONING_RETRY_FAILED);
		} else if (e instanceof BaseException) {
			throw (BaseException) e;
		}
	}

}
