package foiegras.ygyg.post.application.dto.postList;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPostListOutDto {

	private List<PostListItemDto> items;
	private PageInfoDto pageInfoDto;

}
