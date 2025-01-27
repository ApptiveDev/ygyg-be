package foiegras.ygyg.post.infrastructure.querydsl;


import foiegras.ygyg.post.application.dto.userpost.in.GetMyPostListInDto;
import foiegras.ygyg.post.application.dto.userpost.out.UserPostListQueryDataByCursorOutDto;
import foiegras.ygyg.post.infrastructure.entity.UserPostEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public interface UserPostQueryDslRepository {

	// 유저 UUID로, 유저가 참여한 진행중인 소분글 조회
	List<UserPostEntity> findNotFinishedUserPostByUserUuid(UUID writerUuid, LocalDateTime deletedAt);

	// 유저 UUID로, 타입별 소분글 조회
	UserPostListQueryDataByCursorOutDto findPostListByCursor(GetMyPostListInDto inDto);

}
