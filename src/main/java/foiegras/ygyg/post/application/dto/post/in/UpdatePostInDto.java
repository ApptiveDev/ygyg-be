package foiegras.ygyg.post.application.dto.post.in;


import foiegras.ygyg.post.application.dto.userpost.in.UserPostDataInDto;
import lombok.*;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdatePostInDto {

	Long userPostId;

	private UserPostDataInDto userPostDataInDto;

	private PostDataInDto postDataInDto;

	private String imageUrl;

	private Integer unitId;

	private Integer seasoningCategoryId;

}
