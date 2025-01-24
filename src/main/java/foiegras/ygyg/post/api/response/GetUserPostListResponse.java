package foiegras.ygyg.post.api.response;


import foiegras.ygyg.post.application.dto.userpost.out.PageInfoDto;
import foiegras.ygyg.post.application.dto.userpost.out.UserPostItemDto;
import lombok.Getter;

import java.util.List;


@Getter
public class GetUserPostListResponse {

	private List<UserPostItemDto> items;
	private PageInfoDto pageInfoDto;

}
