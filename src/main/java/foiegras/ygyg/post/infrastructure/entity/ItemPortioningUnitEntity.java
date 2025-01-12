package foiegras.ygyg.post.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "item_portioning_unit")
public class ItemPortioningUnitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_portioning_unit_id")
	private Long id;

	@Column(name = "unit", length = 2)
	private String unit;

}
