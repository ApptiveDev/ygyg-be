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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
	 * 1. S3 PreSigned URL 생성
	 */

	// 1. S3 PreSigned URL 생성
	@Operation(summary = "S3 PreSigned URL 생성", description = "S3 PreSigned URL 생성", tags = { "Aws" })
	@GetMapping("/presigned-url")
	@SecurityRequirement(name = "Bearer Auth")
	public BaseResponse<GetPreSignedUrlResponse> getPutObjectPreSignedUrl(@Valid GetPutObjectPreSignedUrlRequest request, @AuthenticationPrincipal CustomUserDetails authentication) {
		GetPutObjectPreSignedUrlInDto inDto = modelMapper.map(request, GetPutObjectPreSignedUrlInDto.class);
		inDto = inDto.toBuilder()
			.userEmail(authentication.getUserEmail())
			.build();
		GetPutObjectPreSignedUrlOutDto outDto = awsService.getPutObjectPreSignedUrl(inDto);
		return new BaseResponse<>(modelMapper.map(outDto, GetPreSignedUrlResponse.class));
	}

}
