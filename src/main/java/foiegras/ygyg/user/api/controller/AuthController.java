package foiegras.ygyg.user.api.controller;


import foiegras.ygyg.global.common.response.BaseResponse;
import foiegras.ygyg.user.api.request.SignUpRequest;
import foiegras.ygyg.user.application.dto.in.SignUpInDto;
import foiegras.ygyg.user.application.facade.SignUpFacade;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	// service
	private final SignUpFacade signUpFacade;
	// util
	private final ModelMapper modelMapper;


	/**
	 * AuthController
	 * 1. 회원가입
	 */

	// 1. 회원가입
	@Operation(summary = "회원가입", description = "회원가입", tags = { "Auth" })
	@PostMapping("/signup")
	public BaseResponse<Void> signUp(@Valid @RequestBody SignUpRequest request) {
		SignUpInDto inDto = modelMapper.map(request, SignUpInDto.class);
		signUpFacade.signUp(inDto);
		return new BaseResponse<>();
	}

}
