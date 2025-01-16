package foiegras.ygyg.post.application.dto.in;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


// getPostOutDto의 UserPost Entity 필드 컴포지션용 outDto
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPostDataOutDto {

	private Long id;
	private UUID writerUuid;
	private String postTitle;
	private LocalDateTime portioningDate;
	private Integer expectedMinimumPrice;
	private Integer remainCount;
	private Boolean isFullMinimum;

}
