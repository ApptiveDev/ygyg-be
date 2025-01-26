package foiegras.ygyg.comment.api.response;


import foiegras.ygyg.comment.application.dto.out.GetCommentListItemOutDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentResponse {

	private List<GetCommentListItemOutDto> comments;

}
