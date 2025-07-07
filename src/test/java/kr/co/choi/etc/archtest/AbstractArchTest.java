package kr.co.choi.etc.archtest;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

/**
 * 모든 ArchUnit 테스트의 공통 부모.
 * ─ BASE_PACKAGE: 루트 패키지 상수
 * ─ CLASSES     : 프로젝트 클래스들을 한 번만 스캔해 캐싱
 */
public abstract class AbstractArchTest {

    protected static final String BASE_PACKAGE = "kr.co.choi.etc";

    /**
     * ClassFileImporter는 무겁기 때문에 static 블록에서 한 번만 실행
     */
    protected static final JavaClasses CLASSES = new ClassFileImporter().importPackages(BASE_PACKAGE);
}
