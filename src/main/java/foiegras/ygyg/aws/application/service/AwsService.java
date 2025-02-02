package foiegras.ygyg.aws.application.service;


import foiegras.ygyg.aws.application.dto.in.GetPutObjectPreSignedUrlInDto;
import foiegras.ygyg.aws.application.dto.out.GetPutObjectPreSignedUrlOutDto;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;


@Service
@RequiredArgsConstructor
public class AwsService {

	// aws
	private final S3Template s3Template;
	private final S3Presigner s3Presigner;

	@Value("${aws.s3.bucket}")
	private String bucket;

	@Value("${aws.s3.duration}")
	private Long duration;


	/**
	 * AwsService
	 * 1. S3 PutObject PreSigned URL 생성
	 * 2. S3 Delete Object
	 */

	// 1. S3 PutObject PreSigned URL 생성
	public GetPutObjectPreSignedUrlOutDto getPutObjectPreSignedUrl(GetPutObjectPreSignedUrlInDto inDto) {
		// key: 저장될 파일명
		String key = inDto.getUserEmail() + "-" + inDto.getFileName();
		// PutObjectRequest
		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
			.bucket(bucket)
			.key(key)
			.contentType(inDto.getContentType())
			.build();
		// preSignRequest
		PutObjectPresignRequest preSignRequest = PutObjectPresignRequest.builder()
			.signatureDuration(Duration.ofMinutes(duration))
			.putObjectRequest(putObjectRequest)
			.build();
		return new GetPutObjectPreSignedUrlOutDto(s3Presigner.presignPutObject(preSignRequest).url().toString());
	}


	// 2. S3 Delete Object: key = email + "-" + fileName
	public void deleteObject(String key) {
		s3Template.deleteObject(bucket, key);
	}

}
