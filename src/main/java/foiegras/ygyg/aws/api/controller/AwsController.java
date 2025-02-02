package foiegras.ygyg.aws.api.controller;


import foiegras.ygyg.aws.api.request.GetPutObjectPreSignedUrlRequest;
import foiegras.ygyg.aws.api.response.GetPreSignedUrlResponse;
import foiegras.ygyg.aws.application.dto.in.GetPutObjectPreSignedUrlInDto;
import foiegras.ygyg.aws.application.dto.out.GetPutObjectPreSignedUrlOutDto;
import foiegras.ygyg.aws.application.service.AwsService;
import foiegras.ygyg.global.common.response.BaseResponse;
import foiegras.ygyg.global.common.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@RestController
@RequestMapping("/api/v1/aws")
@RequiredArgsConstructor
public class AwsController {

	// service
	private final AwsService awsService;
	// util
	private final ModelMapper modelMapper;


	/**
	 * AwsController
	 * 1. S3 Put Object PreSigned URL 생성
	 * 2. S3 Delete Object
	 */

	// 1. S3 Put Object PreSigned URL 생성
	@Operation(summary = "S3 Put Object PreSigned URL 생성", description = "S3 Put Object PreSigned URL 생성", tags = { "Aws" })
	@GetMapping("/presigned-url/put")
	@SecurityRequirement(name = "Bearer Auth")
	public BaseResponse<GetPreSignedUrlResponse> getPutObjectPreSignedUrl(@Valid GetPutObjectPreSignedUrlRequest request, @AuthenticationPrincipal CustomUserDetails authentication) {
		GetPutObjectPreSignedUrlInDto inDto = modelMapper.map(request, GetPutObjectPreSignedUrlInDto.class);
		inDto = inDto.toBuilder()
			.userEmail(authentication.getUserEmail())
			.build();
		GetPutObjectPreSignedUrlOutDto outDto = awsService.getPutObjectPreSignedUrl(inDto);
		return new BaseResponse<>(modelMapper.map(outDto, GetPreSignedUrlResponse.class));
	}


	// 2. S3 Delete Object
	@Operation(summary = "S3 Delete Object", description = "S3에서 객체 삭제", tags = { "Aws" })
	@DeleteMapping("/s3/{imagePath}")
	@SecurityRequirement(name = "Bearer Auth")
	public BaseResponse<Void> deleteObject(@PathVariable("imagePath") String imagePath) {
		awsService.deleteObject(imagePath);
		return new BaseResponse<>();
	}

}
