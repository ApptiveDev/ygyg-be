package foiegras.ygyg.post.infrastructure.jpa.post;


import foiegras.ygyg.post.infrastructure.entity.SeasoningCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SeasoningCategoryJpaRepository extends JpaRepository<SeasoningCategoryEntity, Integer> {
}
