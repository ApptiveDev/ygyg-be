package foiegras.ygyg.global.config.security;//package gentledog.vendors.global.config.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig implements WebMvcConfigurer {

	// origins: 허용할 도메인 or ip주소
	@Value("${ORIGINS.DEVELOPMENT_CLIENT_LOCAL}")
	private String DEVELOPMENT_CLIENT_LOCAL;
	@Value("${ORIGINS.DEVELOPMENT_CLIENT_DOCKER}")
	private String DEVELOPMENT_CLIENT_DOCKER;
	@Value("${ORIGINS.DEVELOPMENT_CLIENT_DOMAIN}")
	private String DEVELOPMENT_CLIENT_DOMAIN;
	@Value("${ORIGINS.PRODUCTION_CLIENT_DOCKER}")
	private String PRODUCTION_CLIENT_DOCKER;
	@Value("${ORIGINS.PRODUCTION_CLIENT_DOMAIN}")
	private String PRODUCTION_CLIENT_DOMAIN;


	// CORS 설정
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/**") // 허용할 api uri
			.allowedMethods(CorsConfiguration.ALL)
			.allowedHeaders(CorsConfiguration.ALL)
			.allowedOriginPatterns(
				DEVELOPMENT_CLIENT_LOCAL,
				DEVELOPMENT_CLIENT_DOCKER,
				DEVELOPMENT_CLIENT_DOMAIN,
				PRODUCTION_CLIENT_DOCKER,
				PRODUCTION_CLIENT_DOMAIN
			) // 허용할 도메인 or ip주소
			.allowCredentials(true);
	}

}

