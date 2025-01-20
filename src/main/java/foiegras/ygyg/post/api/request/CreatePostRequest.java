package foiegras.ygyg.post.api.request;


import foiegras.ygyg.post.application.dto.in.PostDataInDto;
import foiegras.ygyg.post.application.dto.in.UserPostDataInDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class CreatePostRequest {

	private UserPostDataInDto userPostDataInDto;

	private PostDataInDto postDataInDto;

	@NotNull
	private String imageUrl;

	@NotNull
	private Integer unitId;

	@NotNull
	private Integer seasoningCategoryId;

}
