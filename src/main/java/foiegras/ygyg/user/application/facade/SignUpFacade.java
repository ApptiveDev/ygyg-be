package foiegras.ygyg.user.application.facade;


import foiegras.ygyg.global.common.util.UuidGenerator;
import foiegras.ygyg.user.application.dto.in.CreateUserInDto;
import foiegras.ygyg.user.application.dto.in.SignUpInDto;
import foiegras.ygyg.user.application.service.AuthService;
import foiegras.ygyg.user.application.service.JoinRouteService;
import foiegras.ygyg.user.infrastructure.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SignUpFacade {

	// service
	private final AuthService authService;
	private final JoinRouteService joinRouteService;

	// util
	private final PasswordEncoder passwordEncoder;
	private final UuidGenerator uuidGenerator;
	private final ModelMapper modelMapper;


	/**
	 * SignUpFacade
	 * 1. 회원가입 & 서비스 알게된 경로 카운트 증가
	 */

	// 1. 회원가입 & 서비스 알게된 경로 카운트 증가
	public UserEntity signUp(SignUpInDto inDto) {
		// uuid 생성
		UUID userUuid = uuidGenerator.generateUuid();
		// 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(inDto.getUserPassword());
		// 회원가입
		CreateUserInDto createUserInDto = CreateUserInDto.builder()
			.userUuid(userUuid)
			.userSignInId(inDto.getUserSignInId())
			.userEmail(inDto.getUserEmail())
			.userPassword(encodedPassword)
			.userNickname(inDto.getUserNickname())
			.build();
		UserEntity userEntity = authService.createUser(createUserInDto);
		// 서비스 알게된 경로 저장
		joinRouteService.increaseSelectRouteCount(inDto.getRouteId());
		// 결과 return
		return userEntity;
	}

}
