package kr.co.choi.etc.config.trim;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.micrometer.common.util.StringUtils;

import java.io.IOException;

/**
 * 역직렬화 모듈
 */
public class TrimStringDeserializer extends StdDeserializer<String>
        implements ContextualDeserializer {

    private static final TrimStringDeserializer ENABLED = new TrimStringDeserializer(true);
    private static final TrimStringDeserializer DISABLED = new TrimStringDeserializer(false);

    private final boolean enabled;

    private TrimStringDeserializer(boolean enabled) {
        super(String.class);
        this.enabled = enabled;
    }

    /**
     * DTO 클래스에 @NoGlobalTrim 붙어 있으면 trim 비활성화
     */
    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty prop) {
        boolean skip = prop != null
                && prop.getMember() != null
                && prop.getMember().getDeclaringClass().isAnnotationPresent(NoGlobalTrim.class);
        return skip ? DISABLED : ENABLED;
    }

    /**
     * 실제 역직렬화
     */
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // 1) JSON null 토큰인지 먼저 확인
        if (p.getCurrentToken() == JsonToken.VALUE_NULL) {
            return null;
        }
        // 2) 문자열 값 읽기
        String v = p.getValueAsString();
        // 3) trim 옵션이 꺼져 있으면 그대로 반환
        if (!enabled) {
            return v;
        }
        // 4) 공백만 있으면 null, 아니면 trim
        return StringUtils.isBlank(v) ? null : v.trim();
    }

    /**
     * 편의 메서드 – 모듈 생성
     */
    public static Module asModule() {
        // ENABLED 인스턴스 한 개만 등록
        return new SimpleModule().addDeserializer(String.class, ENABLED);
    }
}
