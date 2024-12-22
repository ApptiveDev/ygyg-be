package foiegras.ygyg.user.application.service;


import foiegras.ygyg.user.application.dto.in.CreateUserInDto;
import foiegras.ygyg.user.infrastructure.entity.UserEntity;
import foiegras.ygyg.user.infrastructure.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

	// repository
	private final UserJpaRepository userJpaRepository;


	/**
	 * AuthService
	 * 1. 유저 생성
	 * 2. 로그인
	 */

	// 1. 유저 생성
	public UserEntity createUser(CreateUserInDto inDto) {
		UserEntity newUser = UserEntity.createNewUser(inDto.getUserUuid(), inDto.getUserEmail(), inDto.getUserPassword(), inDto.getUserNickname());
		return userJpaRepository.save(newUser);
	}

}
