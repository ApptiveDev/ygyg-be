package foiegras.ygyg.global.event;


import foiegras.ygyg.post.application.service.ParticipatingUserService;
import foiegras.ygyg.post.application.service.PostService;
import foiegras.ygyg.post.application.service.UserPostService;
import foiegras.ygyg.post.infrastructure.entity.PostEntity;
import foiegras.ygyg.post.infrastructure.entity.UserPostEntity;
import foiegras.ygyg.user.application.dto.event.DeleteAccountEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.UUID;


@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class DeleteAccountEventHandler {

	// service
	private final ParticipatingUserService participatingUserService;
	private final UserPostService userPostService;
	private final PostService postService;


	// 계정 삭제 이벤트 핸들러
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void deleteAccountEventHandler(DeleteAccountEvent event) {
		log.info("계정 삭제 이벤트 핸들러 실행: userUuid = {}", event.getUserUuid());
		// 탈퇴한 유저가 참여한, 현재 진행중인 소분글 조회
		List<UserPostEntity> notFinishedUserPost = this.findNotFinishedUserPost(event);
		// 소분글 참여중인, 탈퇴한 유저 삭제
		this.deleteParticipatingUsers(event.getUserUuid());
		// 참여중인 소분글 인원 업데이트
		this.updateUserPost(notFinishedUserPost);
	}


	/**
	 * DeleteAccountEventHandler
	 * 1. 탈퇴한 유저가 참여한, 현재 진행중인 소분글 리스트 조회
	 * 2. 소분글 참여중인, 탈퇴한 유저 삭제
	 * 3. 참여중인 소분글 인원 업데이트
	 */

	// 1. 탈퇴한 유저가 참여한, 현재 진행중인 소분글 리스트 조회
	private List<UserPostEntity> findNotFinishedUserPost(DeleteAccountEvent event) {
		return userPostService.findNotFinishedUserPostListByUserUuid(event.getUserUuid(), event.getDeletedAt());
	}


	// 2. 소분글 참여중인, 탈퇴한 유저 삭제
	private void deleteParticipatingUsers(UUID userUuid) {
		participatingUserService.deleteParticipatingUserByUserUuid(userUuid);
	}


	// 3. 참여중인 소분글 인원 업데이트
	private void updateUserPost(List<UserPostEntity> notFinishedUserPost) {
		for (UserPostEntity joinedUserPost : notFinishedUserPost) {
			// 참여 가능 남은 인원수 업데이트
			userPostService.updateRemainCount(joinedUserPost, "cancel");
			// 현재 참여 인원 업데이트
			PostEntity joinedPost = postService.updateCurrentEngageCount(joinedUserPost.getPostEntity(), "cancel");
			// 최소 참여 인원 달성 여부 업데이트
			userPostService.updateIsFullMinimum(joinedUserPost, joinedPost.getCurrentEngageCount(), joinedPost.getMinEngageCount());
		}
	}

}
