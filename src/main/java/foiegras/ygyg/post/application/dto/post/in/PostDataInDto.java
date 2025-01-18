package foiegras.ygyg.post.application.dto.post.in;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PostDataInDto {

	@NotNull
	private String onlinePurchaseUrl;

	@NotNull
	@Positive
	private Integer originalPrice;

	@NotNull
	@Positive
	private Integer amount;

	@NotNull
	private Integer minEngageCount;

	@NotNull
	private Integer maxEngageCount;

	@NotNull
	private Integer currentEngageCount;

	@NotNull
	private Double portioningPlaceLatitude;

	@NotNull
	private Double portioningPlaceLongitude;

	@NotNull
	private String description;

	@NotNull
	private String portioningPlaceAddress;

	@NotNull
	private String portioningPlaceDetailAddress;

}