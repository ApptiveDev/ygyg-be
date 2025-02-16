package foiegras.ygyg.user.application.service;


import foiegras.ygyg.global.common.exception.BaseException;
import foiegras.ygyg.global.common.response.BaseResponseStatus;
import foiegras.ygyg.global.common.security.CustomUserDetails;
import foiegras.ygyg.global.common.security.jwt.JwtTokenProvider;
import foiegras.ygyg.user.application.dto.event.DeleteAccountEvent;
import foiegras.ygyg.user.application.dto.in.CreateUserInDto;
import foiegras.ygyg.user.application.dto.in.DeleteAccountInDto;
import foiegras.ygyg.user.infrastructure.entity.UserEntity;
import foiegras.ygyg.user.infrastructure.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

	// repository
	private final UserJpaRepository userJpaRepository;
	// util
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final ApplicationEventPublisher eventPublisher;


	/**
	 * AuthService
	 * 1. 유저 생성
	 * 2. 유저 인증
	 * 3. 액세스 토큰 생성
	 * 4. 회원탈퇴
	 * 5. 이벤트 발행
	 */

	// 1. 유저 생성
	public UserEntity createUser(CreateUserInDto inDto) {
		UserEntity newUser = UserEntity.createNewUser(inDto.getUserUuid(), inDto.getUserName(), inDto.getUserEmail(), inDto.getUserPassword(), inDto.getUserNickname());
		return userJpaRepository.save(newUser);
	}


	// 2. 유저 인증
	public void authenticate(UserEntity user, String inputPassword) {
		// 인증 객체 생성
		CustomUserDetails userDetails = new CustomUserDetails(user);
		// authentication manager 를 통해 인증
		try {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userDetails.getUserUuid(), inputPassword));
		} catch (Exception e) {
			throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
		}
	}


	// 3. 액세스 토큰 생성
	public String createAccessToken(UserEntity user) {
		// 인증 객체 생성
		CustomUserDetails userDetails = new CustomUserDetails(user);
		// 토큰 반환
		return jwtTokenProvider.generateToken(userDetails);
	}


	// 4. 회원탈퇴
	public void deleteAccount(DeleteAccountInDto inDto) {
		// uuid에 해당하는 유저 조회
		UserEntity userEntity = userJpaRepository.findByUserUuid(inDto.getUserUuid())
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));
		// hard delete
		userJpaRepository.delete(userEntity);
		// 이벤트 발행
		this.publishEvent(new DeleteAccountEvent(inDto.getUserUuid(), inDto.getDeletedAt()));
	}


	// 5. 이벤트 발행
	private <T> void publishEvent(T event) {
		eventPublisher.publishEvent(event);
	}

}
