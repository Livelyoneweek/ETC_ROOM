package kr.co.choi.etc.controller;

import kr.co.choi.etc.service.LogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * LogController 단위 슬라이스 테스트
 */
@WebMvcTest(LogController.class)
@AutoConfigureMockMvc(addFilters = false)   // SecurityFilterChain 제거
@ExtendWith(OutputCaptureExtension.class)
class LogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogService logService;

    @Test
    @DisplayName("GET /test → 200 OK & logService.saveLog() 호출")
    void testLog_callsServiceAndReturnsOk() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk());

        verify(logService, times(1)).saveLog();
    }

    @Test
    @DisplayName("로그 레벨: INFO·WARN·ERROR 만 기록되고 DEBUG 는 제외된다")
    void testLog_writesExpectedLogLevels(CapturedOutput output) throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk());

        assertThat(output)
                .contains("### LogController.testLog")
                .doesNotContain("DEBUG");
    }
}