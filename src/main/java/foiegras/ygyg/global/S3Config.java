package foiegras.ygyg.global;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;


@Configuration
public class S3Config {

	@Value("${spring.cloud.aws.region.static}")
	private String region;
	@Value("${spring.cloud.aws.credentials.access-key}")
	private String accessKey;
	@Value("${spring.cloud.aws.credentials.secret-key}")
	private String secretKey;


	// S3Presigner: s3 임시접근 url 생성 도구
	@Bean
	public S3Presigner s3Presigner() {
		AwsCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);
		return S3Presigner.builder()
			.region(Region.of(region))
			.credentialsProvider(() -> awsCredentials)
			.build();
	}

}
