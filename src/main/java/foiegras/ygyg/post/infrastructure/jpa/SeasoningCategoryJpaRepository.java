package foiegras.ygyg.post.infrastructure.jpa;


import foiegras.ygyg.post.infrastructure.entity.SeasoningCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SeasoningCategoryJpaRepository extends JpaRepository<SeasoningCategoryEntity, Long> {

	Optional<SeasoningCategoryEntity> findByCategoryName(String categoryName);

}
