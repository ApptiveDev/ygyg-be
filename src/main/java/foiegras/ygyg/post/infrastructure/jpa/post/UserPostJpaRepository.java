package foiegras.ygyg.post.infrastructure.jpa.post;


import foiegras.ygyg.post.infrastructure.entity.UserPostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserPostJpaRepository extends JpaRepository<UserPostEntity, Long> {

	Page<UserPostEntity> findAllBySeasoningCategoryEntityId(Integer categoryId, Pageable pageable);

	// 최소소분인원 미충족 소분글 중 (소분일시+1일)이 지난 게시글들 불러오기
	@Query(value = "SELECT * FROM user_post WHERE DATE_ADD(portioning_date, INTERVAL 1 DAY) <NOW()"
		+ "AND is_full_minimum = false", nativeQuery = true)
	List<UserPostEntity> findExpiredPosts();

  Page<UserPostEntity> findByIsFullMinimumTrue(Pageable pageable);

	Page<UserPostEntity> findAllBySeasoningCategoryEntityIdAndIsFullMinimumTrue(Integer categoryId, Pageable pageable);


}
