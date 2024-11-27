package foiegras.ygyg.post.infrastructure.entity;


import foiegras.ygyg.global.common.basetime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_post")
public class UserPostEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_post_id")
	private Long id;

	@Column(name = "seasoning_category_id", nullable = false, columnDefinition = "TINYINT")
	private int seasoningCategoryId;

	// 포스트 작성자 uuid
	@Column(name = "writer_uuid", nullable = false, columnDefinition = "BINARY(16)")
	private UUID writerUuid;

	@Column(name = "post_title", nullable = false, length = 50)
	private String postTitle;

	// 예상 최소 소분가 = 원가 / 소분최대인원 으로 계산해 넣기
	// 저가 순 정렬에 쓰임
	@Column(name = "expected_minimun_price", nullable = false)
	private Integer expectedMinimumPrice;

	// 남은 참여가능 인원수 = 최대인원 - 현재인원으로 계산해 넣기
	// 남은 인원 적은 수 순 정렬에 쓰임, 작성자 상시 포함이라 1명으로 초기화
	// db에 넣을 때는 "--최대참여인원" 으로 넣어야 함 for 게시자 1명 기본 카운트
	@Column(name = "remain_count", nullable = false, columnDefinition = "TINYINT")
	private Integer remain_count;

	// 필터링 -> 최소 참여 달성된 글만 보기
	// 추후 TODO : 소분 참여 시 다음 조건 때 True로 변경해야함 => if( isFullMinimum == FALSE && 현재인원 >= 최소참여인원 )
	@Column(name = "is_full_minimum", nullable = false, columnDefinition = "TINYINT(1) DEFAULT false")
	private Boolean isFullMinimum;

	@Column(name = "portioning_date", nullable = false)
	private LocalDateTime portioningDate;

}
