package foiegras.ygyg.post.application.dto.userpost.out;


import com.querydsl.core.annotations.QueryProjection;
import foiegras.ygyg.global.common.querydsl.CursorBasePaginationProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
public class UserPostItemDto implements CursorBasePaginationProperty<Long> {

	private Long userPostId;
	private String postTitle;
	private String imageUrl;
	private LocalDateTime portioningDate;
	private Integer originalPrice;
	private Integer minEngageCount;
	private Integer maxEngageCount;
	private Integer currentEngageCount;


	@QueryProjection
	public UserPostItemDto(Long userPostId, String postTitle, String imageUrl, LocalDateTime portioningDate, Integer originalPrice, Integer minEngageCount, Integer maxEngageCount,
		Integer currentEngageCount) {
		this.userPostId = userPostId;
		this.postTitle = postTitle;
		this.imageUrl = imageUrl;
		this.portioningDate = portioningDate;
		this.originalPrice = originalPrice;
		this.minEngageCount = minEngageCount;
		this.maxEngageCount = maxEngageCount;
		this.currentEngageCount = currentEngageCount;
	}


	@Override
	public Long findCursor() {
		return userPostId;
	}

}
