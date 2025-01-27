package foiegras.ygyg.global.common.querydsl;


import com.querydsl.core.types.dsl.BooleanExpression;
import foiegras.ygyg.post.infrastructure.entity.QItemImageUrlEntity;
import foiegras.ygyg.post.infrastructure.entity.QParticipatingUsersEntity;
import foiegras.ygyg.post.infrastructure.entity.QPostEntity;
import foiegras.ygyg.post.infrastructure.entity.QUserPostEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;


@Component
public class PostDomainBooleanExpression {

	// query dsl value
	private final static QUserPostEntity userPostEntity = QUserPostEntity.userPostEntity;
	private final static QParticipatingUsersEntity participatingUsersEntity = QParticipatingUsersEntity.participatingUsersEntity;
	private final static QPostEntity postEntity = QPostEntity.postEntity;
	private final static QItemImageUrlEntity itemImageUrlEntity = QItemImageUrlEntity.itemImageUrlEntity;
	// static value
	private final static String ASC = "asc";
	private final static String DESC = "desc";
	private final static String WRITTEN = "written";
	private final static String JOIN = "join";
	private final static String COMPLETE = "complete";


	/**
	 * Post 도메인의 BooleanExpressions
	 * 1. cursor로 다음 userPost 조회
	 * 2. 타입별 userPost 조회
	 */

	// 1. cursor로 다음 userPost 조회
	public BooleanExpression getNextUserPost(Long lastCursor, String order) {
		// 첫 페이지인 경우 -> lastCursor가 없음
		if (lastCursor == null) {
			return null;
		}
		// 두 번째 페이지부터 -> lastCursor 이후의 Post를 조회
		return order.equals(ASC) ? userPostEntity.id.gt(lastCursor) : userPostEntity.id.lt(lastCursor);
	}


	// 2. 타입별 userPost 조회: written(내가 작성), join(내가 참여), complete(소분 완료)
	public BooleanExpression selectType(String type, UUID userUuid, LocalDateTime currentTime) {
		BooleanExpression written = userPostEntity.writerUuid.eq(userUuid);
		BooleanExpression join = written.or(participatingUsersEntity.participatingUserUUID.eq(userUuid));
		BooleanExpression beforeComplete = userPostEntity.portioningDate.gt(currentTime);
		BooleanExpression afterComplete = userPostEntity.portioningDate.lt(currentTime);
		return switch (type) {
			case WRITTEN -> written.and(beforeComplete);
			case JOIN -> join.and(beforeComplete);
			case COMPLETE -> join.and(afterComplete);
			default -> null;
		};
	}

}
