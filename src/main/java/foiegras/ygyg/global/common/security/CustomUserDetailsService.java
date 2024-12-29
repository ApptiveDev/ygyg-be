package foiegras.ygyg.global.common.security;


import foiegras.ygyg.user.infrastructure.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserJpaRepository userJpaRepository;


	// uuid로 Users 조회 후, CustomUserDetails로 변환하여 반환
	@Override
	public UserDetails loadUserByUsername(String userUuidString) throws UsernameNotFoundException {
		UUID userUuid = UUID.fromString(userUuidString);
		return new CustomUserDetails(userJpaRepository.findByUserUuid(userUuid).orElseThrow(() -> new UsernameNotFoundException(userUuidString)));
	}

}
