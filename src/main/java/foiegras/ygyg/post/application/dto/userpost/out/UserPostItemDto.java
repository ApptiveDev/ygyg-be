package foiegras.ygyg.post.application.dto.userpost.out;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPostItemDto {

	private Long userPostId;
	private String postTitle;
	private String imageUrl;
	private LocalDateTime portioningDate;
	private Integer originalPrice;
	private Integer minEngageCount;
	private Integer maxEngageCount;
	private Integer currentEngageCount;

}
