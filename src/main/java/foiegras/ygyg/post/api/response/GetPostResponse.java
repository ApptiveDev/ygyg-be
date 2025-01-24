package foiegras.ygyg.post.api.response;


import foiegras.ygyg.post.application.dto.post.out.PostDataOutDto;
import foiegras.ygyg.post.application.dto.userpost.out.UserPostDataOutDto;
import lombok.Getter;


@Getter
public class GetPostResponse {

	private UserPostDataOutDto UserPostDataOutDto;
	private PostDataOutDto PostDataOutDto;
	private String imageUrl;
	private String unitName;
	private String categoryName;
	private Boolean writerActiveState;

}
