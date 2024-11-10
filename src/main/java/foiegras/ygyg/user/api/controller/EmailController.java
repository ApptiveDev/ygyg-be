package foiegras.ygyg.user.api.controller;


import foiegras.ygyg.global.common.response.BaseResponse;
import foiegras.ygyg.user.api.response.CheckEmailDuplicateResponse;
import foiegras.ygyg.user.application.dto.out.CheckEmailDuplicateOutDto;
import foiegras.ygyg.user.application.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class EmailController {

	// service
	private final EmailService emailService;
	// util
	private final ModelMapper modelMapper;


	/**
	 * EmailController
	 * 1. 이메일 중복 확인
	 */

	// 1. 이메일 중복 확인
	@Operation(summary = "이메일 중복 확인", description = "이메일 중복 확인", tags = { "User Sign" })
	@GetMapping("/duplicate-check/email")
	public BaseResponse<CheckEmailDuplicateResponse> checkDuplicateEmail(@Email @NotBlank @Size(max = 50) @RequestParam("email") String email) {
		CheckEmailDuplicateOutDto checkEmailDuplicateOutDto = emailService.checkDuplicateEmail(email);
		CheckEmailDuplicateResponse checkEmailDuplicateResponse = modelMapper.map(checkEmailDuplicateOutDto, CheckEmailDuplicateResponse.class);
		return new BaseResponse<>(checkEmailDuplicateResponse);
	}

}