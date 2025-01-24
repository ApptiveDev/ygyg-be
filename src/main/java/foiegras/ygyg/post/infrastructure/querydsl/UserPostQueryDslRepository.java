package foiegras.ygyg.post.infrastructure.querydsl;


import foiegras.ygyg.post.infrastructure.entity.UserPostEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public interface UserPostQueryDslRepository {

	List<UserPostEntity> findNotFinishedUserPostByUserUuid(UUID writerUuid, LocalDateTime deletedAt);

}
