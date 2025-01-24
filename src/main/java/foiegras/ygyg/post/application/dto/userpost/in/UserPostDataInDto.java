package foiegras.ygyg.post.application.dto.userpost.in;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserPostDataInDto {

	@NotBlank
	@Size(max = 50)
	private String postTitle;

	@NotNull
	private LocalDateTime portioningDate;

}