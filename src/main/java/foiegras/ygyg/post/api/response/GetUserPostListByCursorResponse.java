package foiegras.ygyg.post.api.response;


import foiegras.ygyg.post.application.dto.userpost.out.UserPostItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class GetUserPostListByCursorResponse {

	private List<UserPostItemDto> myPost;
	private Long lastCursor;
	private Boolean hasNext;

}
