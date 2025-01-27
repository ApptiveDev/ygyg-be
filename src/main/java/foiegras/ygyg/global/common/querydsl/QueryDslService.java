package foiegras.ygyg.global.common.querydsl;


import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QueryDslService {

	/**
	 * Cursor
	 * 1. content에서 마지막 cursor 가져오기
	 * 2. 다음 페이지 존재 여부 확인
	 */

	// 1. content에서 마지막 cursor 가져오기
	public <T extends CursorBasePaginationProperty<S>, S> S getLastCursor(List<T> content) {
		S lastCursorId = null;
		if (!content.isEmpty()) {
			lastCursorId = content.get(content.size() - 1).findCursor();
		}
		return lastCursorId;
	}


	// 2. 다음 페이지 존재 여부 확인
	public <T> Pair<Boolean, List<T>> getHasNext(Pageable pageable, List<T> content) {
		boolean hasNext = false;
		if (content.size() > pageable.getPageSize()) {
			content.remove(pageable.getPageSize());
			hasNext = true;
		}
		return Pair.of(hasNext, content);
	}

}
