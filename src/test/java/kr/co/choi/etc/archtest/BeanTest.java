package kr.co.choi.etc.archtest;

import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

class BeanTest extends AbstractArchTest {


    /* ──────────────────────────────────────────────
     * Bean 생성 검증
     * ────────────────────────────────────────────── */

    /**
     * 필드 인젝션(@Autowired) 금지, 생성자 인젝션만 허용
     */
    @Test
    void noFieldInjectionAllowed() {
        ArchRuleDefinition.noFields()
                .should().beAnnotatedWith(Autowired.class)
                .because("필드 인젝션 대신 생성자 인젝션을 사용하세요.")
                .check(CLASSES);
    }


    /* ──────────────────────────────────────────────
     * Dependency 순서 검증
     * ────────────────────────────────────────────── */

    /**
     * “Repository 또는 RepoCustomImpl 으로 끝나는 클래스가 @Service 어노테이션이 붙은 클래스를 의존해서는 안 된다
     */
    @Test
    void repositoriesShouldNotDependOnServices() {
        ArchRuleDefinition.noClasses()
                .that().haveSimpleNameEndingWith("Repository")
                .or().haveSimpleNameEndingWith("RepoCustomImpl")
                .should().dependOnClassesThat().areAnnotatedWith(Service.class)
                .because("Repository 계층은 Service 계층을 참조하면 안 됩니다.")
                .check(CLASSES);
    }

    @Test
    void servicesShouldNotDependOnControllers() {
        ArchRuleDefinition.noClasses()
                .that().haveSimpleNameEndingWith("Service")
                .should().dependOnClassesThat()
                .areAnnotatedWith(RestController.class)
                .because("Service 계층은 Controller 계층을 참조하면 안 됩니다.")
                .check(CLASSES);
    }
}
