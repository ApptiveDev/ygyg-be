package foiegras.ygyg.user.application.service;


import foiegras.ygyg.global.common.exception.BaseException;
import foiegras.ygyg.global.common.response.BaseResponseStatus;
import foiegras.ygyg.user.application.dto.out.CheckEmailDuplicateOutDto;
import foiegras.ygyg.user.application.dto.out.SendEmailOutDto;
import foiegras.ygyg.user.infrastructure.jpa.UserJpaRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {

	// repository
	private final UserJpaRepository userJpaRepository;
	// util
	private final JavaMailSender mailSender;
	// value
	@Value("${spring.mail.username}")
	private String fromEmail;


	/**
	 * EmailService
	 * 1. 이메일 중복 확인
	 * 2. 이메일 전송
	 */

	// 1. 이메일 중복 확인
	@Transactional(readOnly = true)
	public CheckEmailDuplicateOutDto checkDuplicateEmail(String email) {
		return new CheckEmailDuplicateOutDto(userJpaRepository.existsByUserEmail(email));
	}


	// 2. 이메일 전송
	@Async
	public void sendEmail(SendEmailOutDto outDto) {
		// MimeMessage 생성
		MimeMessage email = mailSender.createMimeMessage();
		// 내용 설정
		try {
			email.setSubject(outDto.getSubject());
			email.setText(outDto.getText(), "UTF-8", "html");
			email.setRecipients(MimeMessage.RecipientType.TO, outDto.getReceiverEmail());
			email.setFrom(fromEmail);
			log.info("MimeMessage 생성 성공: {}", email);
		} catch (Exception e) {
			log.error("MimeMessage 생성 실패, 이메일을 다시 확인해주세요: {}", outDto.getReceiverEmail());
			throw new BaseException(BaseResponseStatus.INVALID_EMAIL_ADDRESS);
		}
		// 메일 전송
		mailSender.send(email);
	}

}
