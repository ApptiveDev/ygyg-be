package foiegras.ygyg.post.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
public class PostEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;

	// 상품소분단위 테이블과 다대일 매핑
	@ManyToOne
	@JoinColumn(name = "item_portining_unit_id")
	private ItemPortioningUnitEntity itemPortioningUnitEntity;

	//url 최대길이로 길이지정
	@Column(name = "online_purchase_url", nullable = true, columnDefinition = "TEXT")
	private String onlinePurchaseUrl;

	@Column(name = "original_price", nullable = false)
	private Integer originalPrice;

	@Column(name = "amount", nullable = false)
	private Integer amount;

	@Column(name = "min_engage_count", nullable = false, columnDefinition = "TINYINT")
	private Integer minEngageCount;

	@Column(name = "max_engage_count", nullable = false, columnDefinition = "TINYINT")
	private Integer maxEngageCount;

	// 게시자 포함이므로 초기값 1
	// 엔티티 변수 직접 초기화는 스키마 생성 DDL에 영향없으니 ColumnDefault나 다른 방식으로 초기화해야함
	@Column(name = "current_engage_count", nullable = false, columnDefinition = "TINYINT")
	@ColumnDefault("1")
	@Builder.Default
	private Integer currentEngageCount = 1;

	@Column(name = "portioning_place_latitude", nullable = false)
	private Double portioningPlaceLatitude;

	@Column(name = "portioning_place_longitude", nullable = false)
	private Double portioningPlaceLongitude;

	@Column(name = "description", nullable = false, columnDefinition = "TEXT")
	private String description;

	// 단순 길이제한은 columnDefinition 대신 length 옵션으로~
	@Column(name = "portioning_place_address", length = 100, nullable = false)
	private String portioningPlaceAddress;

	@Column(name = "portioning_place_detail_address", length = 100, nullable = false)
	private String portioningPlaceDetailAddress;

}
