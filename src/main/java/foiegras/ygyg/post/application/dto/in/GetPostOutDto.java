package foiegras.ygyg.post.application.dto.in;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPostOutDto {

	private UserPostDataOutDto userPostDataOutDto;
	private PostDataOutDto postDataOutDto;
	private String imageUrl;
	private String unitName;
	private String categoryName;
	private Boolean writerActiveState;

}
