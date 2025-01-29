package foiegras.ygyg.aws.application.dto.in;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class GetPutObjectPreSignedUrlInDto {

	private String fileName;
	private String contentType;
	private String userEmail;

}
