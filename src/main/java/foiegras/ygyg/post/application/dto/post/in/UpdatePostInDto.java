package foiegras.ygyg.post.application.dto.post.in;


import foiegras.ygyg.post.application.dto.userpost.in.UserPostDataInDto;
import lombok.*;

import java.util.UUID;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdatePostInDto {

	private Long userPostId;
	
	private UUID userUuid;

	private UserPostDataInDto userPostDataInDto;

	private PostDataInDto postDataInDto;

	private String imageUrl;

	private Integer unitId;

	private Integer seasoningCategoryId;

}
