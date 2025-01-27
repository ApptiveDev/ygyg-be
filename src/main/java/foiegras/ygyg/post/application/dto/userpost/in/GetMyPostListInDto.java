package foiegras.ygyg.post.application.dto.userpost.in;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class GetMyPostListInDto {

	private String type;
	private Long lastCursor;
	private Pageable pageable;
	private String order;
	private UUID userUuid;
	private LocalDateTime currentTime;

}
