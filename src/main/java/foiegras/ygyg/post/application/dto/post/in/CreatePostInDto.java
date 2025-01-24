package foiegras.ygyg.post.application.dto.post.in;


import foiegras.ygyg.post.application.dto.userpost.in.UserPostDataInDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostInDto {

	private UserPostDataInDto userPostDataInDto;

	private PostDataInDto postDataInDto;

	// ## itemImageUrlEntity
	private String imageUrl;

	// ## ItemPortioningUnitEntity
	private Integer unitId;

	//## SeasoningCategoryEntity
	private Integer seasoningCategoryId;

	private UUID writerUuid;

}


