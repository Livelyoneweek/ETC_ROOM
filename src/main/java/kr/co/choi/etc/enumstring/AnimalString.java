package kr.co.choi.etc.enumstring;

import com.fasterxml.jackson.annotation.JsonCreator;

public final class AnimalString
        extends EnumString<Animal, AnimalString> {

    /* 생성자는 숨기고 String 하나만 받도록 */
    private AnimalString(String value) {
        super(value);
    }

    /* Enum 타입 매핑 */
    @Override
    protected Class<Animal> getEnumType() {
        return Animal.class;
    }

    /* Jackson 역직렬화용 팩토리 */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static AnimalString of(String value) {
        return new AnimalString(value);
    }
}
