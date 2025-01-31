package foiegras.ygyg.post.application.dto.userpost.in;


import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;


@Getter
@Builder
public class SearchPostInDto {

	private String keyword;
	private String sortBy;
	private Pageable pageable;
	private Boolean isMinimumPeopleMet;

}
