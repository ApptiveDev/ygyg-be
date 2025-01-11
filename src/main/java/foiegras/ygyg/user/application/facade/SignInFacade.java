package foiegras.ygyg.user.application.facade;


import foiegras.ygyg.user.application.dto.in.SignInInDto;
import foiegras.ygyg.user.application.dto.out.SignInOutDto;
import foiegras.ygyg.user.application.service.AuthService;
import foiegras.ygyg.user.application.service.UserService;
import foiegras.ygyg.user.infrastructure.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SignInFacade {

	// service
	private final AuthService authService;
	private final UserService userService;


	/**
	 * SignInFacade
	 * 1. 로그인
	 */

	// 1. 로그인
	public SignInOutDto signIn(SignInInDto inDto) {
		// user 조회
		UserEntity user = userService.findByUserEmail(inDto.getUserEmail());
		// user, pw로 인증 진행
		authService.authenticate(user, inDto.getUserPassword());
		// 인증에 성공했다면 토큰 생성
		String accessToken = authService.createAccessToken(user);
		// 결과 return
		return SignInOutDto.builder()
			.userUuid(user.getUserUuid())
			.userNickname(user.getUserNickname())
			.accessToken(accessToken)
			.userName(user.getUserName())
			.userEmail(user.getUserEmail())
			.build();
	}

}
