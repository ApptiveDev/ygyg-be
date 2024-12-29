package foiegras.ygyg.user.application.service;


import foiegras.ygyg.global.common.exception.BaseException;
import foiegras.ygyg.global.common.response.BaseResponseStatus;
import foiegras.ygyg.user.infrastructure.entity.JoinRouteEntity;
import foiegras.ygyg.user.infrastructure.jpa.JoinRouteJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class JoinRouteService {

	// repository
	private final JoinRouteJpaRepository joinRouteJpaRepository;


	/**
	 * JoinRouteService
	 * 1. 서비스 알게된 경로 selectCount 증가
	 */

	// 1. 서비스 알게된 경로 selectCount 증가
	public void increaseSelectRouteCount(Integer routeId) {
		// 조회
		JoinRouteEntity joinRouteEntity = joinRouteJpaRepository.findById(routeId)
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXIST_ROUTE));
		// 업데이트
		joinRouteEntity.increaseSelectCount();
	}

}
