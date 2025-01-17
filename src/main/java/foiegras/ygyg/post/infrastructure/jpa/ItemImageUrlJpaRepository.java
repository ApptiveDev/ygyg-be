package foiegras.ygyg.post.infrastructure.jpa;


import foiegras.ygyg.post.infrastructure.entity.ItemImageUrlEntity;
import foiegras.ygyg.post.infrastructure.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemImageUrlJpaRepository extends JpaRepository<ItemImageUrlEntity, Long> {

	List<ItemImageUrlEntity> findByPostEntity(PostEntity postEntity);

}
