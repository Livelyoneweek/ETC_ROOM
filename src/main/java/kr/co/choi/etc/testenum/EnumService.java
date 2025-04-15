package kr.co.choi.etc.testenum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EnumService {

    public List<String> getEnumList(String tenantId) {
        log.info("### EnumService.getEnumList");

        return null;
    }
}
