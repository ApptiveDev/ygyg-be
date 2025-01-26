package foiegras.ygyg.post.application.dto.post.in;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeletePostInDto {

	@NotNull
	Long userPostId;

}
