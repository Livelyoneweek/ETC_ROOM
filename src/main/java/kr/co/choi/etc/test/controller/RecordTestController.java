package kr.co.choi.etc.test.controller;

import kr.co.choi.etc.test.RecordDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/record/v1")
public class RecordTestController {

    @PostMapping
    public String testRecord(@RequestBody @Validated RecordDto.Req.Person person) {
        log.info("### RecordTestController.testRecord");
        log.info("### person = {}", person);
        return person.name();
    }
}
