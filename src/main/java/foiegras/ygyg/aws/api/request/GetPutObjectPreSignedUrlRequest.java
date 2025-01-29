package foiegras.ygyg.aws.api.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class GetPutObjectPreSignedUrlRequest {

	@NotNull
	@NotEmpty
	@NotBlank
	private String fileName;

	@NotNull
	@NotEmpty
	@NotBlank
	private String contentType;

}
