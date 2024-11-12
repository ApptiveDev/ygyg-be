package foiegras.ygyg.global.common.security;


import foiegras.ygyg.user.infrastructure.entity.UserEntity;
import foiegras.ygyg.user.infrastructure.entity.enums.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;


@Getter
public class CustomUserDetails implements UserDetails {

	private UUID userUuid;
	private String password;
	private String userEmail;
	private UserRole role;


	public CustomUserDetails(UserEntity user) {
		this.userUuid = user.getUserUuid();
		this.password = user.getUserPassword();
		this.userEmail = user.getUserEmail();
		this.role = user.getUserRole();
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}


	@Override
	public String getPassword() {
		return this.password;
	}


	@Override
	public String getUsername() {
		// token에 userUUID를 넘겨주기 위해, userUUID를 return
		return this.userUuid.toString();
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}


	@Override
	public boolean isEnabled() {
		return true;
	}

}
