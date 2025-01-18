package foiegras.ygyg.post.application.dto.post.in;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserPostDataInDto {

	@NotNull
	private UUID writerUuid;

	@NotBlank
	@Size(max = 50)
	private String postTitle;

	@NotNull
	private LocalDateTime portioningDate;

	private Integer expectedMinimumPrice;
	private Integer remainCount;
	private Boolean isFullMinimum;

}