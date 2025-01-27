package foiegras.ygyg.post.infrastructure.querydsl;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import foiegras.ygyg.global.common.querydsl.PostDomainBooleanExpression;
import foiegras.ygyg.global.common.querydsl.QueryDslService;
import foiegras.ygyg.post.application.dto.userpost.in.GetMyPostListInDto;
import foiegras.ygyg.post.application.dto.userpost.out.UserPostItemDto;
import foiegras.ygyg.post.application.dto.userpost.out.UserPostListQueryDataByCursorOutDto;
import foiegras.ygyg.post.infrastructure.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Slf4j
@Repository
@RequiredArgsConstructor
public class UserPostQueryDslRepositoryImpl implements UserPostQueryDslRepository {

	// query dsl value
	private final static QUserPostEntity userPostEntity = QUserPostEntity.userPostEntity;
	private final static QParticipatingUsersEntity participatingUsersEntity = QParticipatingUsersEntity.participatingUsersEntity;
	private final static QPostEntity postEntity = QPostEntity.postEntity;
	private final static QItemImageUrlEntity itemImageUrlEntity = QItemImageUrlEntity.itemImageUrlEntity;
	// static value
	private final static String ASC = "asc";
	private final static String DESC = "desc";
	// querydsl
	private final JPAQueryFactory queryFactory;
	private final PostDomainBooleanExpression booleanExpression;
	private final QueryDslService queryDslService;


	/**
	 * UserPostQueryDslRepository
	 * 1. 유저 UUID로, 유저가 참여한 진행중인 소분글 조회
	 * 2. 유저 UUID로, 타입별 소분글 조회
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


	// 2. 유저 UUID로, 타입별 소분글 조회
	@Override
	public UserPostListQueryDataByCursorOutDto findPostListByCursor(GetMyPostListInDto inDto) {
		Pageable pageable = inDto.getPageable();
		// content 조회
		List<UserPostItemDto> content =
			queryFactory
				.select(Projections.constructor(UserPostItemDto.class,
					userPostEntity.id,
					userPostEntity.postTitle,
					itemImageUrlEntity.imageUrl,
					userPostEntity.portioningDate,
					postEntity.originalPrice,
					postEntity.minEngageCount,
					postEntity.maxEngageCount,
					postEntity.currentEngageCount
				))
				.from(userPostEntity)
				.join(postEntity).on(postEntity.id.eq(userPostEntity.postEntity.id))
				.leftJoin(itemImageUrlEntity).on(itemImageUrlEntity.postEntity.id.eq(postEntity.id)).limit(1)
				.leftJoin(participatingUsersEntity).on(participatingUsersEntity.userPostEntity.id.eq(userPostEntity.id))
				.where(
					booleanExpression.selectType(inDto.getType(), inDto.getUserUuid(), inDto.getCurrentTime()),
					booleanExpression.getNextUserPost(inDto.getLastCursor(), inDto.getOrder()))
				.orderBy(inDto.getOrder().equals(ASC) ? userPostEntity.id.asc() : userPostEntity.id.desc())
				.limit(pageable.getPageSize() + 1) // 다음 페이지 여부 확인을 위해 +1개 조회
				.fetch();
		// 다음 페이지 여부 확인 & content에서 마지막 값 제거, result = {hasNext, content}
		Pair<Boolean, List<UserPostItemDto>> result = queryDslService.getHasNext(pageable, content);
		Boolean hasNext = result.getFirst();
		List<UserPostItemDto> finalContent = result.getSecond();
		// lastCursorId: 마지막 커서 아이디
		Long lastCursorId = queryDslService.getLastCursor(finalContent);
		// result = content, pageable, hasNext, lastCursorId
		return new UserPostListQueryDataByCursorOutDto(finalContent, pageable, hasNext, lastCursorId);
	}

}
