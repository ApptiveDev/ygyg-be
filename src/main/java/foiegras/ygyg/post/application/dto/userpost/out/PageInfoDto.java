package foiegras.ygyg.post.application.dto.userpost.out;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoDto {

	private Long totalItemsLength;
	private Integer currentPage;
	private Integer size;

}
