package kr.co.choi.etc.enumstring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestEnumString {

    public static void main(String[] args) throws JsonProcessingException {

        // 직렬화
        AnimalString dogStr = EnumString.fromEnum(Animal.DOG, AnimalString.class);
        log.info("### dogStr = {}", dogStr);
        String json = new ObjectMapper().writeValueAsString(dogStr);   // →  "DOG"
        log.info("### json = {}", json);

        // 역직렬화 + 안전 변환
        AnimalString x = new ObjectMapper().readValue("\"CAT\"", AnimalString.class);
        log.info("### json = {}", json);

        Animal cat = x.toEnumOrThrow();             // OK
        log.info("### cat = {}", cat);
        Animal fallback = x.toEnumOrElse(() -> Animal.RABBIT);
        log.info("### fallback = {}", fallback);

        AnimalString unknown = AnimalString.of("ELEPHANT");
        log.info("### unknown = {}", unknown);
        Animal defaultAnimal = unknown.toEnumOrElse(() -> Animal.DOG); // fallback
        log.info("### defaultAnimal = {}", defaultAnimal);

        unknown.toEnumOrThrow();                    // 예외 발생
    }
}
