package foiegras.ygyg.user.application.service;


import foiegras.ygyg.global.common.exception.BaseException;
import foiegras.ygyg.global.common.response.BaseResponseStatus;
import foiegras.ygyg.user.application.dto.in.NicknameDuplicationCheckInDto;
import foiegras.ygyg.user.application.dto.out.NicknameDuplicationCheckOutDto;
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
public class UserService {

	// repository
	private final UserJpaRepository userJpaRepository;


	/**
	 * UserService
	 * 1. 닉네임 중복 확인
	 * 2. 이메일로 유저 조회
	 */

	// 1. 닉네임 중복 확인
	public NicknameDuplicationCheckOutDto nicknameDuplicationCheck(NicknameDuplicationCheckInDto inDto) {
		Boolean isDuplicated = userJpaRepository.existsByUserNickname(inDto.getNickname());
		return new NicknameDuplicationCheckOutDto(isDuplicated);
	}


	// 2. 로그인 Id로 유저 조회
	public UserEntity findByUserEmail(String userEmail) {
		return userJpaRepository.findByUserEmail(userEmail)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN));
	}

}
