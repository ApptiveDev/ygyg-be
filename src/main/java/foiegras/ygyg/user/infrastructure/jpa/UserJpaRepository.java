package foiegras.ygyg.user.infrastructure.jpa;


import foiegras.ygyg.user.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

	/**
	 * UserJpaRepository
	 * 1. 이메일에 해당하는 유저가 존재하는지 확인
	 * 2. uuid로 유저 조회
	 * 3. 닉네임으로 존재 여부 확인
	 * 4. 이메일로 유저 조회
	 */

	// 1. 이메일에 해당하는 유저가 존재하는지 확인
	Boolean existsByUserEmail(String email);

	// 2. uuid로 유저 조회
	Optional<UserEntity> findByUserUuid(UUID userUuid);

	// 3. 닉네임으로 존재 여부 확인
	Boolean existsByUserNickname(String nickname);

	// 4. 이메일로 유저 조회
	Optional<UserEntity> findByUserEmail(String userEmail);

}
