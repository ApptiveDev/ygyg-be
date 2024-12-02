package foiegras.ygyg.post.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "seasoning_category")
public class SeasoningCategoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seasoning_category_id", nullable = false, columnDefinition = "TINYINT")
	private int id;

	@Column(name = "category_name", nullable = false, length = 20)
	private String categoryName;

	// category는 연관관계 주인이 아니므로 mappedBy 속성 쓰며 속성값은 userPostEntity의 fk 속성명
	// 양방향을 맛보기 용도로 oneToMany 지정
	@OneToMany(mappedBy = "seasoningCategoryEntity")
	private List<UserPostEntity> userPosts = new ArrayList<>();

}
