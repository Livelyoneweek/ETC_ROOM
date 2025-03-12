package kr.co.choi.etc.controller;

import kr.co.choi.etc.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LogController2 {

    private final LogService logService;

    @GetMapping("/test2")
    public void TestLog() {
        log.debug("### LogController2.TestLog");
        log.info("### LogController2.TestLog");
        log.warn("### LogController2.TestLog");
        log.error("### LogController2.TestLog");
    }

    @GetMapping("/error")
    public void TestLogError() {
        log.error("### LogController2.TestLog");
        log.error("### bjchoi");
        logService.saveErrorLog();
    }

    @GetMapping("/warn")
    public void TestLogWarn() {
        log.warn("### LogController2.TestLog");
    }

    @GetMapping("/info")
    public void TestLogInfo() {
        log.warn("### LogController2.TestLog");
    }
}
