package foiegras.ygyg.comment.api.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;


@Getter
public class CreateCommentRequest {

	@NonNull
	private Long userPostId;

	@NonNull
	@NotEmpty
	@NotBlank
	@Size(max = 200)
	private String commentContents;

}
