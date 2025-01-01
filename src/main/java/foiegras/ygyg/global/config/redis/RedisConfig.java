package foiegras.ygyg.global.config.redis;


import io.lettuce.core.RedisClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
@EnableRedisRepositories // RedisRepository를 사용하기 위해 필요
public class RedisConfig {

	@Value("${spring.data.redis.port}")
	private int port;

	@Value("${spring.data.redis.host}")
	private String host;


	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(host, port);
	}


	@Bean
	@Primary // 여러 개의 Bean 중에서 우선적으로 사용할 Bean을 지정
	public RedisTemplate<String, String> redisTemplate() {
		// redisTemplate를 받아와서 set, get, delete를 사용
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

		/**
		 * setKeySerializer, setValueSerializer 설정
		 * Serializer: 직렬화로 데이터 저장 시 데이터를 바이트 배열로 변환하는 과정
		 * redis-cli을 통해 직접 데이터를 조회 시 알아볼 수 없는 형태로 출력되는 것을 방지
		 * 아래 두 라인을 작성하지 않으면, key값이 \xac\xed\x00\x05t\x00\x03sol 이렇게 조회된다.
		 */
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());

		// redisTemplate에 redisConnectionFactory를 넣어 Spring Data Redis가 사용할 수 있도록 설정
		redisTemplate.setConnectionFactory(redisConnectionFactory());

		return redisTemplate;
	}


	@Bean
	public RedisClient redisClient() {
		return RedisClient.create("redis://" + host + ":" + port);
	}


	@Bean
	public RedisCacheConfiguration cacheConfiguration() {
		return RedisCacheConfiguration.defaultCacheConfig()
			.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
			.disableCachingNullValues();
	}


	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		return RedisCacheManager.builder(redisConnectionFactory)
			.cacheDefaults(cacheConfiguration())
			.build();
	}


	@Bean
	public RedisTemplate<String, Integer> redisIntegerTemplate() {
		RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		return redisTemplate;
	}

}