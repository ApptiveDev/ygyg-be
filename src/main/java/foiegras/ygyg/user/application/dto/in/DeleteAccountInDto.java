package foiegras.ygyg.user.application.dto.in;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAccountInDto {

	private UUID userUuid;
	private LocalDateTime deletedAt;

}
