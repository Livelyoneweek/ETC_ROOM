package kr.co.choi.etc.testenum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/enum")
public class EnumController {

    private final EnumService enumService;

    @GetMapping
    public ResponseEntity<List<String>> getEnumList(@RequestParam(value = "tenantId") String tenantId) {
        log.info("### EnumController.getEnumList");
        List<String> result = enumService.getEnumList(tenantId);
        return ResponseEntity.ok(result);
    }
}
