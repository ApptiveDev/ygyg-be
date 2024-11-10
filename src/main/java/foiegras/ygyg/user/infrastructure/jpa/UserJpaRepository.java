package foiegras.ygyg.user.infrastructure.jpa;


import foiegras.ygyg.user.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

	/**
	 * UserJpaRepository
	 * 1. 이메일에 해당하는 유저가 존재하는지 확인
	 */

	// 1. 이메일에 해당하는 유저가 존재하는지 확인
	Boolean existsByUserEmail(String email);

}
