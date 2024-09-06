package kr.co.choi.etc.log;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestLog {

    public static void main(String[] args) {
        log.info("### TestLog.main");

        log.warn("test warn");
        log.debug("test debug");
    }
}
