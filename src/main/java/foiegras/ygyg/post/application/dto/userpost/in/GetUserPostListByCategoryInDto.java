package foiegras.ygyg.post.application.dto.userpost.in;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserPostListByCategoryInDto {

	private Integer categoryId;
	private String sortBy;
	private Boolean isMinimumPeopleMet;
	private Pageable pageable;

}
