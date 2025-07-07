package kr.co.choi.etc.archtest;

import com.tngtech.archunit.core.domain.JavaCall;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

class LogTest extends AbstractArchTest {

    /**
     * “@RestController 가 붙은 클래스의 public 메소드들은 반드시 첫 번째 호출이 log.info 여야 한다”는 룰
     */
    @Test
    void controllerMethodsShouldStartWithLogInfo() {
        ArchRuleDefinition.methods()
                .that().areDeclaredInClassesThat().areAnnotatedWith(RestController.class)
                .and().arePublic()
                .should(new ArchCondition<JavaMethod>("start with log.info call") {
                    @Override
                    public void check(JavaMethod method, ConditionEvents events) {
                        Set<JavaCall<?>> calls = method.getCallsFromSelf();

                        // 줄 번호로 가장 이른 호출 찾기
                        Optional<JavaCall<?>> firstCall = calls.stream().min(
                                Comparator.comparingInt(call -> call.getSourceCodeLocation().getLineNumber())
                        );

                        boolean ok = firstCall.filter(call ->
                                call.getTarget().getOwner().getSimpleName().toLowerCase().contains("log")).isPresent();

                        if (!ok) {
                            events.add(SimpleConditionEvent.violated(
                                    method, method.getFullName() + " : 첫 호출이 log 가 아닙니다."));
                        }
                    }
                })
                .because("@RestController 메소드는 진입 로그를 반드시 남겨야 합니다.")
                .check(CLASSES);
    }
}
