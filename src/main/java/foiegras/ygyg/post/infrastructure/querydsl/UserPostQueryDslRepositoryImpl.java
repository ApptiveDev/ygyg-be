package foiegras.ygyg.post.infrastructure.querydsl;


import com.querydsl.jpa.impl.JPAQueryFactory;
import foiegras.ygyg.post.infrastructure.entity.QParticipatingUsersEntity;
import foiegras.ygyg.post.infrastructure.entity.QUserPostEntity;
import foiegras.ygyg.post.infrastructure.entity.UserPostEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Slf4j
@Repository
@RequiredArgsConstructor
public class UserPostQueryDslRepositoryImpl implements UserPostQueryDslRepository {

	// value
	private final static QUserPostEntity userPostEntity = QUserPostEntity.userPostEntity;
	private final static QParticipatingUsersEntity participatingUsersEntity = QParticipatingUsersEntity.participatingUsersEntity;
	// querydsl
	private final JPAQueryFactory queryFactory;


	/**
	 * UserPostQueryDslRepository
	 * 1. 유저 UUID로, 유저가 참여한 진행중인 소분글 조회
	 */

	// 1. 유저 UUID로, 유저가 참여한 진행중인 소분글 조회
	@Override
	public List<UserPostEntity> findNotFinishedUserPostByUserUuid(UUID userUuid, LocalDateTime deletedAt) {
		return queryFactory
			.selectFrom(userPostEntity)
			.join(participatingUsersEntity).on(participatingUsersEntity.participatingUserUUID.eq(userUuid))
			.where(
				userPostEntity.portioningDate.gt(deletedAt)
			)
			.fetch();
	}

}
