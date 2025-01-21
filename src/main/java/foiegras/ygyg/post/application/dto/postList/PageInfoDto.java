package foiegras.ygyg.post.application.dto.postList;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoDto {

	private Long totalItemsLength;
	private Integer currentPage;
	private Integer size;

}
