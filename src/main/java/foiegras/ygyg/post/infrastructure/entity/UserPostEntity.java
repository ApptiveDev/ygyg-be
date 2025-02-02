package foiegras.ygyg.post.infrastructure.entity;


import foiegras.ygyg.global.common.basetime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_post")
public class UserPostEntity extends BaseTimeEntity {

	// value
	private final static String JOIN = "join";
	private final static String CANCEL = "cancel";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_post_id")
	private Long id;

	@Version
	@Column(name = "version", nullable = false)
	private Long version;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private PostEntity postEntity;

	// user_post가 Many, category가 One이며 양방향 맛보기로 가보기
	// 양방향의 경우 외래키를 가진 이 엔티티가 연관관계의 주인이다. 보통 다대일에서 다가 fk를 가짐
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seasoning_category_id")
	private SeasoningCategoryEntity seasoningCategoryEntity;

	// 포스트 작성자 uuid
	@Column(name = "writer_uuid", nullable = false, columnDefinition = "BINARY(16)")
	private UUID writerUuid;

	@Column(name = "post_title", nullable = false, length = 50)
	private String postTitle;

	// 예상 최소 소분가 = 원가 / 소분최대인원 으로 계산해 넣기
	// 저가 순 정렬에 쓰임
	@Column(name = "expected_minimun_price", nullable = false)
	private Integer expectedMinimumPrice;

	// 최소소분인원 도달에 남은 인원: [최소인원 - 현재인원]으로 계산하되 음수가 안되게 주의
	// 남은 인원 적은 수 순 정렬에 쓰임
	// 게시자 1명 고려해 db에 넣을 때는 "--최소소분인원" 으로 넣어야 함
	@Column(name = "remain_count", nullable = false, columnDefinition = "TINYINT")
	private Integer remainCount;

	// 필터링 -> 최소 참여 달성된 글만 보기
	// 소분 참여 시 if( isFullMinimum == FALSE && 현재인원 >= 최소참여인원 ) 만족 시 True로 변경
	// todo: remainCount가 본질이 바뀌며 if(remainCount == 0)으로 바꿔도 되는듯
	@Column(name = "is_full_minimum", nullable = false, columnDefinition = "TINYINT(1) DEFAULT false")
	private Boolean isFullMinimum;

	@Column(name = "portioning_date", nullable = false)
	private LocalDateTime portioningDate;


	/**
	 * UserPostEntity
	 * 1. 참여 가능 남은 인원수 업데이트
	 * 2. 최소 참여 인원 달성 여부 업데이트
	 */

	// 1. 참여 가능 남은 인원수 업데이트
	public UserPostEntity updateRemainCount(String type, Integer currentEngageCount, Integer minEngageCount) {
		if (type.equals(JOIN) && this.getRemainCount() > 0) {
			// 음수 방지 위해 remainCount가 1 이상일 때만 감소
			this.remainCount--;
		} else if (type.equals(CANCEL) && currentEngageCount <= minEngageCount) {
			// 현재인원이 최소소분인원과 같거나 적을 때만 remainCount를 증가
			this.remainCount++;
		}
		return this;
	}


	// 2. 최소 참여 인원 달성 여부 업데이트
	public UserPostEntity updateIsFullMinimum(Integer currentEngageCount, Integer minEngageCount) {
		if (currentEngageCount >= minEngageCount) {
			this.isFullMinimum = true;
		}
		return this;
	}

}
