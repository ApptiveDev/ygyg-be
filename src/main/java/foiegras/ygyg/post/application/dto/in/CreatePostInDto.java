package foiegras.ygyg.post.application.dto.in;


public class CreatePostInDto {

	// ## UserPostEntity ##
	// 주석지정 칼럼은 request엔 없어서 이 dto에서 추가한 칼럼
	private String writerUuid;
	private String postTitle;

	// 예상최소소분가 = 원가/최대인원
	private Integer expectedMinimumPrice;

	// 참여가능 남은인원 수 = 최대인원 - 1
	private Integer remainCount;

	// 최소참여인원 달성여부
	private Boolean isFullMinimum;

	private String portioningDate;

	// 생성,수정일은 영속성 컨텍스트로 넘어갈 때 자동생성됨.
	// 비영속성인 dto에서 명시할 필요가 없음!!

	// ## PostEntity ##
	private String onlinePurchaseUrl;
	private String originalPrice;
	private String amount;
	private Integer minEngageCount;
	private Integer maxEngageCount;
	private Integer currentEngageCount;
	private String portioningPlaceLatitude;
	private String portioningPlaceLongitude;
	private String description;
	private String portioningPlaceAddress;
	private String portioningPlaceDetailAddress;

	// ## itemImageUrlEntity
	private String imageUrl;

	// ## ItemPortioningUnitEntity
	private String unit;

	//## SeasoningCategoryEntity
	private String categoryName;

}
