package foiegras.ygyg.user.application.service;


import foiegras.ygyg.user.application.dto.out.CheckEmailDuplicateOutDto;
import foiegras.ygyg.user.infrastructure.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {

	private final UserJpaRepository userJpaRepository;
	// util
	private final JavaMailSender mailSender;


	/**
	 * EmailService
	 * 1. 이메일 중복 확인
	 */

	// 1. 이메일 중복 확인
	@Transactional(readOnly = true)
	public CheckEmailDuplicateOutDto checkDuplicateEmail(String email) {
		return new CheckEmailDuplicateOutDto(userJpaRepository.existsByUserEmail(email));
	}

}
