package foiegras.ygyg.user.api.controller;


import foiegras.ygyg.global.common.response.BaseResponse;
import foiegras.ygyg.user.api.response.NicknameDuplicationCheckResponse;
import foiegras.ygyg.user.application.dto.in.NicknameDuplicationCheckInDto;
import foiegras.ygyg.user.application.dto.out.NicknameDuplicationCheckOutDto;
import foiegras.ygyg.user.application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

	// service
	private final UserService userService;
	// util
	private final ModelMapper modelMapper;


	/**
	 * UserController
	 * 1. 닉네임 중복 확인
	 */

	// 1. 닉네임 중복 확인
	@Operation(summary = "닉네임 중복 확인", description = "닉네임 중복 확인", tags = { "User" })
	@GetMapping("/duplicate-check/nickname/{nickname}")
	public BaseResponse<NicknameDuplicationCheckResponse> nicknameDuplicationCheck(@NotBlank @PathVariable("nickname") String nickname) {
		NicknameDuplicationCheckInDto inDto = new NicknameDuplicationCheckInDto(nickname);
		NicknameDuplicationCheckOutDto outDto = userService.nicknameDuplicationCheck(inDto);
		NicknameDuplicationCheckResponse response = modelMapper.map(outDto, NicknameDuplicationCheckResponse.class);
		return new BaseResponse<>(response);
	}

}
