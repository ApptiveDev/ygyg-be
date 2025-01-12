package foiegras.ygyg.post.api.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CreatePostRequest {

	@NotNull
	private String writerUuid;

	@NotBlank
	@Size(max = 50)
	private String postTitle;

	@NotNull
	private String portioningDate;

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

	@NotNull
	private String imageUrl;

	@NotNull
	private String unit;

	@NotNull
	private String categoryName;

}
