package foiegras.ygyg.aws.application.dto.out;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPutObjectPreSignedUrlOutDto {

	private String preSignedUrl;

}
