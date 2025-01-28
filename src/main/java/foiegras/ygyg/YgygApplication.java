package foiegras.ygyg;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableRetry
@EnableJpaAuditing // createdAt, postedAt 자동화 위해 붙인 어노테이션
@EnableScheduling // 만료 소분글 자동삭제 스케쥴링 어노테이션
@SpringBootApplication
public class YgygApplication {

	public static void main(String[] args) {
		SpringApplication.run(YgygApplication.class, args);
	}

}
