package foiegras.ygyg.post.application.dto.in;


import lombok.Getter;
import lombok.NoArgsConstructor;


// getPostOutDto의 Post Entity 필드 컴포지션용 outDto
@Getter
@NoArgsConstructor
public class PostDataOutDto {

	private String onlinePurchaseUrl;
	private Integer originalPrice;
	private Integer amount;
	private Integer minEngageCount;
	private Integer maxEngageCount;
	private Integer currentEngageCount;
	private Double portioningPlaceLatitude;
	private Double portioningPlaceLongitude;
	private String description;
	private String portioningPlaceAddress;
	private String portioningPlaceDetailAddress;

}
