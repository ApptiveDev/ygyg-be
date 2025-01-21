package foiegras.ygyg.post.infrastructure.jpa.post;


import foiegras.ygyg.post.infrastructure.entity.UserPostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserPostJpaRepository extends JpaRepository<UserPostEntity, Long> {

	Page<UserPostEntity> findAllBySeasoningCategoryEntityId(Integer categoryId, Pageable pageable);

}
