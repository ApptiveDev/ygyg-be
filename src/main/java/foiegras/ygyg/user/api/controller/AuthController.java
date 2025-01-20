package foiegras.ygyg.user.api.controller;


import foiegras.ygyg.global.common.response.BaseResponse;
import foiegras.ygyg.global.common.security.CustomUserDetails;
import foiegras.ygyg.user.api.request.SignInRequest;
import foiegras.ygyg.user.api.request.SignUpRequest;
import foiegras.ygyg.user.api.response.SignInResponse;
import foiegras.ygyg.user.application.dto.in.DeleteAccountInDto;
import foiegras.ygyg.user.application.dto.in.SignInInDto;
import foiegras.ygyg.user.application.dto.in.SignUpInDto;
import foiegras.ygyg.user.application.dto.out.SignInOutDto;
import foiegras.ygyg.user.application.facade.SignInFacade;
import foiegras.ygyg.user.application.facade.SignUpFacade;
import foiegras.ygyg.user.application.service.AuthService;
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
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	// service
	private final SignUpFacade signUpFacade;
	private final SignInFacade signInFacade;
	private final AuthService authService;
	// util
	private final ModelMapper modelMapper;


	/**
	 * AuthController
	 * 1. 회원가입
	 * 2. 로그인
	 * 3. 회원탈퇴
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


	// 3. 회원탈퇴
	@Operation(summary = "회원탈퇴", description = "회원탈퇴", tags = { "Auth" })
	@DeleteMapping("/account")
	@SecurityRequirement(name = "Bearer Auth")
	public BaseResponse<Void> deleteAccount(@AuthenticationPrincipal CustomUserDetails authentication) {
		authService.deleteAccount(new DeleteAccountInDto(authentication.getUserUuid()));
		return new BaseResponse<>();
	}

}
