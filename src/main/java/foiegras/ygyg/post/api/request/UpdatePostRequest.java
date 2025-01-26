package foiegras.ygyg.post.api.request;


import foiegras.ygyg.post.application.dto.post.in.PostDataInDto;
import foiegras.ygyg.post.application.dto.userpost.in.UserPostDataInDto;
import lombok.Getter;


@Getter
public class UpdatePostRequest {

	private UserPostDataInDto userPostDataInDto;

	private PostDataInDto postDataInDto;

	private String imageUrl;

	private Integer unitId;

	private Integer seasoningCategoryId;

}
