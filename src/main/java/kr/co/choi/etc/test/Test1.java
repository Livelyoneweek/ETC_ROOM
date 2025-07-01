package kr.co.choi.etc.test;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class Test1 {

    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();
        UUID uuid4 = UUID.randomUUID();
        log.info("### uuid = {}", uuid);
        log.info("### uuid2 = {}", uuid2);
        log.info("### uuid3 = {}", uuid3);
        log.info("### uuid4 = {}", uuid4);
    }
}
