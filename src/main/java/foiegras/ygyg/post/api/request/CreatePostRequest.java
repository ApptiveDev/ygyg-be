package foiegras.ygyg.post.api.request;


import foiegras.ygyg.post.application.dto.post.in.PostDataInDto;
import foiegras.ygyg.post.application.dto.post.in.UserPostDataInDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class CreatePostRequest {

	private UserPostDataInDto userPostDataInDto;

	private PostDataInDto postDataInDto;

	private String imageUrl;

	@NotNull
	private Integer unitId;

	@NotNull
	private Integer categoryId;

}
