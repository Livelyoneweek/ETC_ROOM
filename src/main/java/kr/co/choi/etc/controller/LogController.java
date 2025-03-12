package kr.co.choi.etc.controller;

import kr.co.choi.etc.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @GetMapping("/test")
    public void TestLog() {
        log.debug("### LogController.TestLog");
        log.info("### LogController.TestLog");
        log.warn("### LogController.TestLog");
        log.error("### LogController.TestLog");

        logService.saveLog();
    }
}
