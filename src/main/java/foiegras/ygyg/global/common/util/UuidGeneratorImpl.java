package foiegras.ygyg.global.common.util;


import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class UuidGeneratorImpl implements UuidGenerator {

	// UUID 생성
	@Override
	public UUID generateUuid() {
		return UUID.randomUUID();
	}
	
}
