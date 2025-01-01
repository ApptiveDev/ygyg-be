package foiegras.ygyg.post.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "item_image_url")
public class ItemImageUrl {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_image_url_id")
	private Long id;

	@Column(name = "image_url", length = 100)
	private String imageUrl;

	@ManyToOne
	@JoinColumn(name = "post_detail_id")
	private PostEntity postEntity;

}
