package foiegras.ygyg.global.config.mail;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


@Configuration
public class MailConfig {

	@Value("${spring.mail.host}")
	private String mailServerHost;
	@Value("${spring.mail.port}")
	private int mailServerPort;
	@Value("${spring.mail.username}")
	private String mailServerUsername;
	@Value("${spring.mail.password}")
	private String mailServerPassword;


	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		// host, port, username, password 설정
		mailSender.setHost(mailServerHost);
		mailSender.setPort(mailServerPort);
		mailSender.setUsername(mailServerUsername);
		mailSender.setPassword(mailServerPassword);

		// JavaMailSender의 properties 설정
		Properties properties = mailSender.getJavaMailProperties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		// mailsender return
		return mailSender;
	}

}
