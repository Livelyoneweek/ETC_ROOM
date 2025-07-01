package kr.co.choi.etc.config.logging;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * p6spy 로그 설정
 */
public class OnlyEffectiveSqlFormat implements MessageFormattingStrategy {

    /**
     * @param connectionId 커넥션 ID
     * @param now          현재 시간 문자열
     * @param elapsed      실행 시간(ms)
     * @param category     카테고리 (statement, resultset 등)
     * @param prepared     플레이스홀더 SQL
     * @param sql          바인딩 후 실제 실행된 SQL
     * @param url          JDBC URL
     * @return 로그로 남길 문자열
     * yml 설정에서 클래스 사용 지정필요
     */
    @Override
    public String formatMessage(int connectionId, String now, long elapsed,
                                String category, String prepared, String sql, String url) {
        // SQL이 비어 있거나 null인 경우
        if (sql == null || sql.trim().isEmpty()) {
            if ("commit".equals(category)) {
                return String.format("[%s] [COMMIT] | took %dms | %s | connection %d", now, elapsed, category, connectionId);
            } else if ("rollback".equals(category)) {
                return String.format("[%s] [ROLLBACK] | took %dms | %s | connection %d", now, elapsed, category, connectionId);
            }
            return null;
        }
        if ("statement".equals(category) || "prepared".equals(category)) {
            return String.format("[%s] [QUERY] | took %dms | %s | connection %d | %s", now, elapsed, category, connectionId, sql);
        }
        // 그 외 카테고리는 출력 안 함
        return null;
    }
}
