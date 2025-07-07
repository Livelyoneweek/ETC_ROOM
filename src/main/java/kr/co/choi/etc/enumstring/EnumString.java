package kr.co.choi.etc.enumstring;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

public abstract class EnumString<E extends Enum<E>, T extends EnumString<E, T>>
        implements Comparable<EnumString<E, T>>, Serializable {

    @JsonValue                    // 직렬화 시 그대로 노출
    private final String value;

    protected EnumString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * 하위 클래스마다 매핑되는 Enum 타입을 알려준다
     */
    protected abstract Class<E> getEnumType();

    /* ---------- Enum ⇄ String 변환 유틸 ---------- */

    public Optional<E> toEnumOrNull() {
        try {
            return Optional.of(Enum.valueOf(getEnumType(), value));
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }

    public E toEnumOrElse(Supplier<E> defaultSupplier) {
        return toEnumOrNull().orElseGet(defaultSupplier);
    }

    public E toEnumOrThrow() {
        return toEnumOrThrow(() ->
                new IllegalStateException(
                        "\"" + value + "\" 에 해당하는 "
                                + getEnumType().getSimpleName()
                                + " 을(를) 찾을 수 없습니다. 의존성 버전을 확인하세요."
                )
        );
    }

    public E toEnumOrThrow(Supplier<? extends RuntimeException> exSupplier) {
        return toEnumOrNull().orElseThrow(exSupplier);
    }

    /* ---------- Enum → EnumString 빠른 변환 (리플렉션 캐싱) ---------- */

    private static final ConcurrentMap<Class<?>, Constructor<?>> CTOR_CACHE =
            new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>, T extends EnumString<E, T>> T fromEnum(
            E sourceEnum, Class<T> targetClass) {

        try {
            Constructor<?> ctor = CTOR_CACHE.computeIfAbsent(targetClass, cls -> {
                try {
                    Constructor<?> c = cls.getDeclaredConstructor(String.class);
                    c.setAccessible(true);
                    return c;
                } catch (NoSuchMethodException e) {
                    throw new IllegalStateException(
                            cls + " 는 (String) 생성자를 가져야 합니다.", e);
                }
            });
            return (T) ctor.newInstance(sourceEnum.name());
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("EnumString 인스턴스화 실패: " + targetClass, e);
        }
    }

    /* ---------- Comparable & Object 구현 ---------- */

    @Override
    public int compareTo(EnumString<E, T> o) {
        return this.value.compareTo(o.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnumString<?, ?> other)) return false;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
