package foiegras.ygyg.post.api.response;


import foiegras.ygyg.post.application.dto.in.PostDataOutDto;
import foiegras.ygyg.post.application.dto.in.UserPostDataOutDto;
import lombok.Getter;


@Getter
public class GetPostResponse {

	private UserPostDataOutDto getUserPostDataOutDto;
	private PostDataOutDto getPostDataOutDto;

	private String imageUrl;

	private String unit;
	private String category;

	private Boolean isActivate;

}
