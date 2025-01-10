package foiegras.ygyg.post.api.request;


import jakarta.validation.constraints.*;


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
	private String originalPrice;

	@NotNull
	@Positive
	private String amount;

	@NotNull
	@Min(value = 2, message = "최소 소분인원은 2명 이상이어야 합니다")
	@Max(value = 10, message = "최대 소분인원은 10명 이하여야 합니다")
	private Integer minEngageCount;

	@NotNull
	private Integer maxEngageCount;

	@NotNull
	private Integer currentEngageCount;

	@NotNull
	private String portioningPlaceLatitude;

	@NotNull
	private String portioningPlaceLongitude;

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
