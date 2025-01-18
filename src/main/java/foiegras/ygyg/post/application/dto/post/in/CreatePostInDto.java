package foiegras.ygyg.post.application.dto.post.in;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
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
	private Integer categoryId;

}


