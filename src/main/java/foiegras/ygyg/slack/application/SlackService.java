package foiegras.ygyg.slack.application;


import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import com.slack.api.webhook.Payload;
import foiegras.ygyg.global.common.exception.BaseException;
import foiegras.ygyg.global.common.response.BaseResponseStatus;
import foiegras.ygyg.slack.domain.NotificationColor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.slack.api.webhook.WebhookPayloads.payload;


@Service
public class SlackService {

	private final Slack slackClient = Slack.getInstance();
	@Value("${webhook.slack.url}")
	private String SLACK_WEBHOOK_URL;


	/**
	 * SlackService
	 * 1. Slack 메시지 전송
	 * 2. payload 생성
	 * 3. slack field 생성
	 */

	// 1. Slack 메시지 전송
	public void sendMessage(String title, String data) {
		try {
			slackClient.send(SLACK_WEBHOOK_URL, this.createPayload(title, data));
		} catch (Exception e) {
			throw new BaseException(BaseResponseStatus.SLACK_SEND_MESSAGE_ERROR);
		}
	}


	// 2. payload 생성
	private Payload createPayload(String title, String data) {
		return payload(p -> p
			.text(title) // 메시지 제목
			.attachments(
				List.of(
					Attachment.builder()
						// 알림 색상
						.color(NotificationColor.RED.getCode())
						// 메시지 본문 내용
						.fields(List.of(generateField("Error Details", data)))
						.build()
				)
			)
		);
	}


	// 3. slack field 생성
	private Field generateField(String title, String value) {
		return Field.builder()
			.title(title)
			.value(value)
			.valueShortEnough(false)
			.build();
	}

}
