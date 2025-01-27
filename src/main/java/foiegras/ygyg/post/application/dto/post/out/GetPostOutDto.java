package foiegras.ygyg.post.application.dto.post.out;


import foiegras.ygyg.post.application.dto.userpost.out.UserPostDataOutDto;
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
	private Boolean userParticipatingIn;
	private String userNickname;

}
