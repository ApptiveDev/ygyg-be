package foiegras.ygyg.user.application.dto.event;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@AllArgsConstructor
public class DeleteAccountEvent {

	private UUID userUuid;
	private LocalDateTime deletedAt;

}
