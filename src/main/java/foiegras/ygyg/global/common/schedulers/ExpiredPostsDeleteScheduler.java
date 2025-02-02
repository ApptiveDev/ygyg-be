package foiegras.ygyg.global.common.schedulers;


import foiegras.ygyg.post.application.service.PostService;
import foiegras.ygyg.post.infrastructure.entity.UserPostEntity;
import foiegras.ygyg.post.infrastructure.jpa.post.UserPostJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
public class ExpiredPostsDeleteScheduler {

	// repositories
	private final UserPostJpaRepository userPostJpaRepository;
	//services
	private final PostService postService;


	// 미성사 소분글 자동 삭제
	@Scheduled(cron = "0 0,15,30,45 * * * *") // 지정 소분시각 분단위마다 실행됨
	@Transactional
	public void expiredPostsDelete() {
		LocalDateTime now = LocalDateTime.now();
		log.info("[" + now + "] 미성사 소분글 자동삭제");
		List<UserPostEntity> expiredPosts = userPostJpaRepository.findExpiredPosts();
		expiredPosts.forEach(postService::deleteExpiredPosts);
		log.info("삭제된 소분글 개수: " + expiredPosts.size());
	}

}
