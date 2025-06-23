package kr.co.choi.etc.Trim.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TrimRecord {

    public static class Req {

        public record Person(
                @NotBlank(message = "causeLocationId 값을 입력해 주세요")
                @Size(max = 50, message = "이름은 50자 이내여야 합니다.")
                String name,

                @NotBlank(message = "주소는 필수 입력입니다.")
                @Size(max = 255, message = "주소는 255자 이내여야 합니다.")
                String address
        ) {
        }

    }
}
