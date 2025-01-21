package foiegras.ygyg.post.application.dto.postList;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPostListInDto {

	String sortBy;
	Integer page;
	Integer size;

}
