package foiegras.ygyg.user.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "join_route")
public class JoinRouteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "join_route_id", columnDefinition = "tinyint")
	private Integer id;

	@Column(name = "route_name", length = 20, nullable = false)
	private String routeName;

	@Column(name = "select_count", nullable = false, columnDefinition = "int default 0")
	private Integer selectCount;


	/**
	 * JoinRoute
	 * 1. selectCount 증가
	 */

	// 1. selectCount 증가
	public void increaseSelectCount() {
		this.selectCount++;
	}

}
