package foiegras.ygyg.slack.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum NotificationColor {

	RED("#FF0000"),
	;

	private final String code;

}
