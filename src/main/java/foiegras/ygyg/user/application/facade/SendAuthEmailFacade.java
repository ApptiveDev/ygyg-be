package foiegras.ygyg.user.application.facade;


import foiegras.ygyg.user.application.dto.in.SendAuthenticationEmailInDto;
import foiegras.ygyg.user.application.dto.out.SendEmailOutDto;
import foiegras.ygyg.user.application.service.AuthEmailService;
import foiegras.ygyg.user.application.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SendAuthEmailFacade {

	// service
	private final AuthEmailService authEmailService;
	private final EmailService emailService;


	// 인증 메일 전송
	public void sendAuthenticationEmail(SendAuthenticationEmailInDto inDto) {
		// 이메일 내용 생성
		SendEmailOutDto authEmailContent = authEmailService.createAuthEmail(inDto.getUserEmail());
		// 이메일 전송
		emailService.sendEmail(authEmailContent);
		// 레디스에 저장
		authEmailService.saveAuthenticationCode(inDto.getUserEmail(), authEmailContent.getCode());
	}

}
