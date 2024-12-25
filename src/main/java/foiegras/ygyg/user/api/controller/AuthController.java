package foiegras.ygyg.user.api.controller;


import foiegras.ygyg.global.common.response.BaseResponse;
import foiegras.ygyg.user.api.request.SignInRequest;
import foiegras.ygyg.user.api.request.SignUpRequest;
import foiegras.ygyg.user.api.response.SignInResponse;
import foiegras.ygyg.user.application.dto.in.SignInInDto;
import foiegras.ygyg.user.application.dto.in.SignUpInDto;
import foiegras.ygyg.user.application.dto.out.SignInOutDto;
import foiegras.ygyg.user.application.facade.SignInFacade;
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
	private final SignInFacade signInFacade;
	// util
	private final ModelMapper modelMapper;


	/**
	 * AuthController
	 * 1. 회원가입
	 * 2. 로그인
	 */

	// 1. 회원가입
	@Operation(summary = "회원가입", description = "회원가입", tags = { "Auth" })
	@PostMapping("/signup")
	public BaseResponse<Void> signUp(@Valid @RequestBody SignUpRequest request) {
		SignUpInDto inDto = modelMapper.map(request, SignUpInDto.class);
		signUpFacade.signUp(inDto);
		return new BaseResponse<>();
	}


	// 2. 로그인
	@Operation(summary = "로그인", description = "로그인", tags = { "Auth" })
	@PostMapping("/signin")
	public BaseResponse<SignInResponse> signIn(@Valid @RequestBody SignInRequest request) {
		SignInInDto inDto = modelMapper.map(request, SignInInDto.class);
		SignInOutDto outDto = signInFacade.signIn(inDto);
		SignInResponse response = modelMapper.map(outDto, SignInResponse.class);
		return new BaseResponse<>(response);
	}

}
