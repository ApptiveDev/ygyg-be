package foiegras.ygyg.post.api.controller;


import foiegras.ygyg.global.common.response.BaseResponse;
import foiegras.ygyg.global.common.security.CustomUserDetails;
import foiegras.ygyg.post.application.dto.in.JoinPortioningInDto;
import foiegras.ygyg.post.application.facade.JoinPortioningFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class UserPostController {

	// service
	private final JoinPortioningFacade joinPortioningFacade;
	// util
	private final ModelMapper modelMapper;


	/**
	 * UserPostController
	 * 1. 소분글 참여하기
	 */

	// 1. 소분글 참여하기
	@Operation(summary = "소분글 참여하기", description = "소분글 참여하기", tags = { "Post" })
	@PostMapping("/join/{userPostId}")
	@SecurityRequirement(name = "Bearer Auth")
	public BaseResponse<Void> joinPortioning(@PathVariable("userPostId") Long userPostId, @AuthenticationPrincipal CustomUserDetails authentication) {
		joinPortioningFacade.joinPortioning(new JoinPortioningInDto(userPostId, authentication));
		return new BaseResponse<>();
	}

}
