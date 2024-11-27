package foiegras.ygyg;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing // createdAt, postedAt 자동화 위해 붙인 어노테이션
public class YgygApplication {

	public static void main(String[] args) {
		SpringApplication.run(YgygApplication.class, args);
	}

}
