package foiegras.ygyg.post.application.dto.userpost.out;


import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;

import java.util.List;


@Getter
public class UserPostListQueryDataByCursorOutDto extends SliceImpl<UserPostItemDto> {

	private Long lastCursor;


	public UserPostListQueryDataByCursorOutDto(List<UserPostItemDto> content, Pageable pageable, boolean hasNext, Long lastCursor) {
		super(content, pageable, hasNext);
		this.lastCursor = lastCursor;
	}

}
