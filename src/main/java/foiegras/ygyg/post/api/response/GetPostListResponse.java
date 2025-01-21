package foiegras.ygyg.post.api.response;


import foiegras.ygyg.post.application.dto.postList.PageInfoDto;
import foiegras.ygyg.post.application.dto.postList.PageListItemDto;
import lombok.Getter;

import java.util.List;


@Getter
public class GetPostListResponse {

	private List<PageListItemDto> items;
	private PageInfoDto pageInfoDto;

}
