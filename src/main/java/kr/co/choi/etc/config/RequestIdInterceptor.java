package kr.co.choi.etc.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

/**
 * MDC(Mapped Diagnostic Context)는 SLF4J와 Logback 등에서 제공하는 스레드 로컬 기반의 데이터 저장소
 * 로그 콘솔 패턴 -> [%highlight(%-5level)] [%d{yyyy-MM-dd HH:mm:ss}] [%thread] [%X{txId}] [%logger{0}:%line] %msg%n , 해당 부분 [%X{txId}] 에서 값 표출
 */
@Component
@Slf4j
public class RequestIdInterceptor implements HandlerInterceptor {

    private static final String TX_ID = "txId";
    private static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // txId 생성 & MDC 에 저장
        String txId = UUID.randomUUID().toString();
        MDC.put(TX_ID, txId);

        // 시작 시간 기록
        request.setAttribute(START_TIME, System.currentTimeMillis());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            long start = (Long) request.getAttribute(START_TIME);
            long latency = System.currentTimeMillis() - start;

            int status = response.getStatus();
            String method = request.getMethod();
            String uri = request.getRequestURI();
//            String domain = DomainEnum.getDomainByUri(uri);

            // SecurityContext 에서 userId 추출
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            String userId = (auth != null && auth.isAuthenticated())
//                    ? auth.getName()
//                    : "anonymous";

            // 로그 출력 (structured logging 사용 시 JSON encoder 자동 포맷팅)
            log.info("### ☘️ [API_RES_INFO] : mobile={}, method={},  uri={}, status={}, latencyMs={}",
                    MDC.get(TX_ID),
                    method,
                    uri,
                    status,
                    latency);
        } finally {
            // MDC 정리
            MDC.remove(TX_ID);
        }
    }
}
