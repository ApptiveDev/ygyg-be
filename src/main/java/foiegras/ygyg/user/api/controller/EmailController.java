package foiegras.ygyg.user.api.controller;


import foiegras.ygyg.global.common.response.BaseResponse;
import foiegras.ygyg.user.api.request.SendAuthenticationEmailRequest;
import foiegras.ygyg.user.api.request.VerifyAuthCodeRequest;
import foiegras.ygyg.user.api.response.CheckEmailDuplicateResponse;
import foiegras.ygyg.user.api.response.VerifyAuthCodeResponse;
import foiegras.ygyg.user.application.dto.in.SendAuthenticationEmailInDto;
import foiegras.ygyg.user.application.dto.in.VerifyAuthCodeInDto;
import foiegras.ygyg.user.application.dto.out.CheckEmailDuplicateOutDto;
import foiegras.ygyg.user.application.dto.out.VerifyAuthCodeOutDto;
import foiegras.ygyg.user.application.facade.SendAuthEmailFacade;
import foiegras.ygyg.user.application.service.AuthEmailService;
import foiegras.ygyg.user.application.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {

	// service
	private final EmailService emailService;
	private final SendAuthEmailFacade sendAuthEmailFacade;
	private final AuthEmailService authEmailService;
	// util
	private final ModelMapper modelMapper;


	/**
	 * EmailController
	 * 1. 이메일 중복 확인
	 * 2. 인증 메일 전송
	 * 3. 이메일 인증 코드 확인
	 */

	// 1. 이메일 중복 확인
	@Operation(summary = "이메일 중복 확인", description = "이메일 중복 확인", tags = { "Email" })
	@GetMapping("/duplicate-check")
	public BaseResponse<CheckEmailDuplicateResponse> checkDuplicateEmail(@Email @NotBlank @Size(max = 50) @RequestParam("email") String email) {
		CheckEmailDuplicateOutDto checkEmailDuplicateOutDto = emailService.checkDuplicateEmail(email);
		CheckEmailDuplicateResponse checkEmailDuplicateResponse = modelMapper.map(checkEmailDuplicateOutDto, CheckEmailDuplicateResponse.class);
		return new BaseResponse<>(checkEmailDuplicateResponse);
	}


	// 2. 인증 메일 전송
	@Operation(summary = "인증 이메일 전송", description = "인증 이메일 전송", tags = { "Email" })
	@PostMapping("/auth")
	public BaseResponse<Void> sendAuthenticationEmail(@Valid @RequestBody SendAuthenticationEmailRequest request) {
		SendAuthenticationEmailInDto inDto = modelMapper.map(request, SendAuthenticationEmailInDto.class);
		sendAuthEmailFacade.sendAuthenticationEmail(inDto);
		return new BaseResponse<>();
	}


	// 3. 이메일 인증 코드 확인
	@Operation(summary = "이메일 인증 코드 확인", description = "이메일 인증 코드 확인", tags = { "Email" })
	@GetMapping("/verify/auth-code")
	public BaseResponse<VerifyAuthCodeResponse> verifyAuthCode(@Valid VerifyAuthCodeRequest request) {
		VerifyAuthCodeInDto inDto = modelMapper.map(request, VerifyAuthCodeInDto.class);
		VerifyAuthCodeOutDto outDto = authEmailService.verifyAuthCode(inDto);
		VerifyAuthCodeResponse responseDto = modelMapper.map(outDto, VerifyAuthCodeResponse.class);
		return new BaseResponse<>(responseDto);
	}

}