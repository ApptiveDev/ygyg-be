package foiegras.ygyg.post.infrastructure.jpa;


import foiegras.ygyg.post.infrastructure.entity.ItemPortioningUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ItemPortioningUnitJpaRepository extends JpaRepository<ItemPortioningUnitEntity, Long> {

	Optional<ItemPortioningUnitEntity> findByUnit(String unit);

}
