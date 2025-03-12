package kr.co.choi.etc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ETCController {

    private final Environment env;

    public static final String PRODUCTION = "prd";
    public static final String POC = "poc"; // proof of concept
    public static final String STAGING = "stg";
    public static final String DEVELOPMENT = "dev";
    public static final String LOCAL = "local";

    @GetMapping("/temp")
    public void TestLog() {
        log.info("### ETCController.TestLog");

        String[] activeProfiles = env.getActiveProfiles();
        for (String activeProfile : activeProfiles) {
            log.info("activeProfile = " + activeProfile);
        }
    }
}
