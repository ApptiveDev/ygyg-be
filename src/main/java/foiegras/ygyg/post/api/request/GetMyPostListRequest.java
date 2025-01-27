package foiegras.ygyg.post.api.request;


import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class GetMyPostListRequest {

	private String type;
	private Long lastCursor;
	private String order;

}
