package foiegras.ygyg.post.application.dto.userpost.out;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserPostListOutDto {

	private List<UserPostItemDto> items;
	private PageInfoDto pageInfoDto;

}
