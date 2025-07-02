package kr.co.choi.etc.company.dto;

import org.hibernate.annotations.Comment;

import java.util.UUID;

public class CompanyDto {

    public static class Query {

        public record CompanyBasic(
                @Comment("company id")
                UUID id,
                @Comment("company name")
                String name) {
        }
    }
}
