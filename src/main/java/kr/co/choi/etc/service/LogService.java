package kr.co.choi.etc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogService {


    public void saveLog() {
        log.info("### LogService.saveLog");
    }

    public void saveErrorLog() {
        log.error("### LogService.saveErrorLog");
    }
}
