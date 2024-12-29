package foiegras.ygyg.global.config.security;


import foiegras.ygyg.global.common.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@RequiredArgsConstructor
public class SecurityAppConfig {

	private final CustomUserDetailsService customUserDetailsService;


	/**
	 * Security: Security 동작에 필요한 인증 제공자, 인증 관리자, 패스워드 인코더를 설정 및 등록한다
	 * - authenticationProvider: Dao Provider와 customUserDetailsService를 사용
	 * - authenticationManager: 인증 관리자를 Bean으로 등록. Spring 기본 인증 관리자를 return
	 * - passwordEncoder: BCrypt 방식의 비밀번호 인코더 사용
	 */

	// 인증 제공자 : 인증을 처리하는데 사용하는 authenticationProvider를 생성한다.
	// DaoAuthenticationProvider는 가장 일반적인 인증 제공자 형태로, UserDetailsService에서 제공받은 사용자 이름과 비밀번호를 기반으로 인증을 수행한다
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); // DAO 기반의 인증 제공자 생성
		authenticationProvider.setUserDetailsService(customUserDetailsService); // 사용자의 세부 정보 서비스 설정
		authenticationProvider.setPasswordEncoder(passwordEncoder()); // 비밀번호 인코더 설정
		return authenticationProvider;
	}


	// 인증 관리자를 Bean으로 등록. Spring Security에서 중요한 역할을 하는 인터페이스
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager(); // 기본 인증 관리자를 가져옴
	}


	// 비밀번호 인코더를 제공. 여기서는 BCrypt 방식의 비밀번호 인코딩 사용
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
