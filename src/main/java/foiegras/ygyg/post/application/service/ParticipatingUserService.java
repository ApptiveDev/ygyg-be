package foiegras.ygyg.post.application.service;


import foiegras.ygyg.post.infrastructure.entity.ParticipatingUsersEntity;
import foiegras.ygyg.post.infrastructure.entity.UserPostEntity;
import foiegras.ygyg.post.infrastructure.jpa.ParticipatingUsersJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class ParticipatingUserService {

	// repository
	private final ParticipatingUsersJpaRepository participatingUsersJpaRepository;


	/**
	 * ParticipatingUserService
	 * 1. 참여자 생성
	 * 2. 참여 상태 확인
	 * 3. 참여자 삭제
	 */

	// 1. 참여자 생성
	public ParticipatingUsersEntity createParticipatingUser(UUID participantUuid, UserPostEntity userPost) {
		return participatingUsersJpaRepository.save(ParticipatingUsersEntity.createNew(participantUuid, userPost));
	}


	// 2. 참여 상태 확인
	public boolean checkParticipatedState(UUID participantUuid, UserPostEntity userPost) {
		return participatingUsersJpaRepository.existsByParticipatingUserUUIDAndUserPostEntity(participantUuid, userPost);
	}


	public void deleteParticipatingUserByUserUuid(UUID userUuid) {
		participatingUsersJpaRepository.deleteAllByParticipatingUserUUID(userUuid);
	}

}
