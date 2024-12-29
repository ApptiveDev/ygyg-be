package foiegras.ygyg.user.infrastructure.entity.enums;


import foiegras.ygyg.global.common.enums.BaseEnum;
import foiegras.ygyg.global.common.enums.BaseEnumConverter;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter
@AllArgsConstructor
public enum UserRole implements BaseEnum<String, String> {

	/**
	 * 1. 코드 작성
	 * 2. field 선언
	 * 3. converter 구현
	 */

	// 1. 코드 작성
	USER("U", "사용자"),
	ADMIN("A", "관리자");

	// 2. field 선언
	private final String code;
	private final String description;

	// 3. converter 구현
	@Converter(autoApply = true)
	static class thisConverter extends BaseEnumConverter<UserRole, String, String> {

		public thisConverter() {
			super(UserRole.class);
		}

	}

}
