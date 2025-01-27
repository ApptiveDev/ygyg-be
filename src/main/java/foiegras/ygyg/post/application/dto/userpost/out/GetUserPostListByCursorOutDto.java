package foiegras.ygyg.post.application.dto.userpost.out;


import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;

import java.util.List;


@Getter
public class GetUserPostListByCursorOutDto extends SliceImpl<UserPostItemDto> {

	private Long lastCursor;


	@Builder
	public GetUserPostListByCursorOutDto(List<UserPostItemDto> content, Pageable pageable, boolean hasNext, Long lastCursor) {
		super(content, pageable, hasNext);
		this.lastCursor = lastCursor;
	}

}
