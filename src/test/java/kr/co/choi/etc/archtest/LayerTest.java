package kr.co.choi.etc.archtest;

import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

class LayerTest extends AbstractArchTest {


    /* ──────────────────────────────────────────────
     * Controller 계층
     * ────────────────────────────────────────────── */
    @Test
    void restControllersMustBeInsideControllerPackage() {
        ArchRuleDefinition.classes()
                .that().areAnnotatedWith(RestController.class)
                .should().resideInAPackage("..controller..")
                .because("@RestController 클래스는 controller 패키지에 위치해야 합니다.")
                .check(CLASSES);
    }

    @Test
    void controllerNamedClassesMustHaveRestControllerAnnotation() {
        ArchRuleDefinition.classes()
                .that().haveSimpleNameEndingWith("Controller")
                .should().beAnnotatedWith(RestController.class)
                .because("이름이 *Controller 로 끝나면 @RestController 를 붙여야 합니다.")
                .check(CLASSES);
    }

    /* ──────────────────────────────────────────────
     * Service 계층
     * ────────────────────────────────────────────── */
    @Test
    void servicesMustBeInsideServicePackage() {
        ArchRuleDefinition.classes()
                .that().areAnnotatedWith(Service.class)
                .should().resideInAPackage("..service..")
                .because("@Service 클래스는 service 패키지에 위치해야 합니다.")
                .check(CLASSES);
    }

    @Test
    void serviceNamedClassesMustHaveServiceAnnotation() {
        ArchRuleDefinition.classes()
                .that().haveSimpleNameEndingWith("Service")
                .should().beAnnotatedWith(Service.class)
                .because("이름이 *Service 로 끝나면 @Service 어노테이션을 붙여야 합니다.")
                .check(CLASSES);
    }

    /* ──────────────────────────────────────────────
     * Repository 계층
     * ────────────────────────────────────────────── */
    @Test
    void repositoriesMustBeInsideRepositoryPackage() {
        ArchRuleDefinition.classes()
                .that().areAnnotatedWith(Repository.class)
                .should().resideInAPackage("..repository..")
                .because("@Repository 클래스/인터페이스는 repository 패키지에 위치해야 합니다.")
                .check(CLASSES);
    }

//    @Test
//    void repositoryNamedClassesMustHaveRepositoryAnnotation() {
//        JavaClasses classes = new ClassFileImporter().importPackages(BASE_PACKAGE);
//
//        ArchRuleDefinition.classes()
//                .that().haveSimpleNameEndingWith("Repository")
//                .should().beAnnotatedWith(Repository.class)
//                .because("이름이 *Repository 로 끝나면 @Repository 어노테이션을 붙여야 합니다.")
//                .check(classes);
//    }

}
