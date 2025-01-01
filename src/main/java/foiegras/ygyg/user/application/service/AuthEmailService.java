package foiegras.ygyg.user.application.service;


import foiegras.ygyg.global.common.redis.RedisUtil;
import foiegras.ygyg.user.application.dto.out.SendEmailOutDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthEmailService {

	// util
	private final RedisUtil redisUtil;
	// value
	@Value("${spring.mail.template}")
	private String emailContent;
	@Value("${spring.mail.properties.auth-code-expiration-sec}")
	private Long expireMillis;
	@Value("${spring.mail.subject}")
	private String subject;


	/**
	 * AuthEmailSenderImpl
	 * 1. 인증 이메일 내용 생성
	 * 2. redis 에 인증코드 저장
	 */

	// 1. 인증 이메일 내용 생성
	public SendEmailOutDto createAuthEmail(String userEmail) {
		// 인증 코드 생성
		String code = String.valueOf((int) (Math.random() * 900000) + 100000);
		// 이메일 내용 설정
		String codeInjectedContent = emailContent.replace("{code}", code);
		// 이메일 설정
		return SendEmailOutDto.builder()
			.receiverEmail(userEmail)
			.subject(subject)
			.text(codeInjectedContent)
			.code(code)
			.build();
	}


	// 2. redis 에 인증코드 저장
	public void saveAuthenticationCode(String userEmail, String code) {
		String codeKey = "AUTH_EMAIL::" + userEmail;
		// redis에서 이메일 중복 여부 확인
		if (redisUtil.existData(codeKey)) {
			redisUtil.deleteData(userEmail);
		}
		redisUtil.createDataExpire(codeKey, code, expireMillis);
	}

}