package kr.co.choi.etc.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

/**
 * MDC(Mapped Diagnostic Context)는 SLF4J와 Logback 등에서 제공하는 스레드 로컬 기반의 데이터 저장소
 * 로그 콘솔 패턴 -> [%highlight(%-5level)] [%d{yyyy-MM-dd HH:mm:ss}] [%thread] [%X{txId}] [%logger{0}:%line] %msg%n , 해당 부분 [%X{txId}] 에서 값 표출
 */
@Component
public class RequestIdInterceptor implements HandlerInterceptor {

    private static final String TX_ID = "txId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String txId = UUID.randomUUID().toString();
        MDC.put(TX_ID, txId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.remove(TX_ID);
    }
}
