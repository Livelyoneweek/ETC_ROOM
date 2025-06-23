package kr.co.choi.etc.Trim.controller;

import kr.co.choi.etc.Trim.dto.TrimDto;
import kr.co.choi.etc.Trim.dto.TrimRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trim/v1")
@Slf4j
public class TrimController {


    @GetMapping("/path/{name}")
    public String getTest(@PathVariable(value = "name") String name) {
        log.info("### RecordTestController.getTest");
        log.info("### name ={}", name);
        return name;
    }


    @GetMapping("/param")
    public String getTest(@RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "address", required = false) String address) {
        log.info("### RecordTestController.getTest");
        log.info("### name ={}, address ={}", name, address);
        return name;
    }

    @PostMapping
    public String postTest(@RequestBody @Validated TrimDto.Req.Person person) {
        log.info("### RecordTestController.postTest");
        log.info("### person = {}", person);
        return person.getName();
    }


    @PostMapping("/record")
    public String postRecordTest(@RequestBody @Validated TrimRecord.Req.Person person) {
        log.info("### RecordTestController.postRecordTest");
        log.info("### person = {}", person);
        return person.name();
    }


}
