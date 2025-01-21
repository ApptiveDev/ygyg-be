package foiegras.ygyg.post.application.dto.postList;


import java.time.LocalDateTime;


public class PageListItemDto {

	private Long userPostId;
	private String postTitle;
	private String imageUrl;
	private LocalDateTime portioningDate;
	private Integer originalPrice;
	private Integer minEngageCount;
	private Integer maxEngageCount;
	private Integer currentEngageCount;

}
