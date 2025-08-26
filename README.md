# ETC Spring Boot Project

This project is a Spring Boot application that demonstrates various features and best practices.
It serves as a collection of examples for building modern Java applications.

## Tech Stack

* **Framework:** Spring Boot 3.2.5
* **Language:** Java 17
* **Build Tool:** Gradle
* **Database:**
    * Spring Data JPA
    * QueryDSL for type-safe queries
    * H2 (In-memory database)
* **Libraries:**
    * Lombok
    * ZXing (for QR Code generation)
    * P6Spy (for SQL query logging)
    * ArchUnit (for architecture testing)

## Key Features

### 1. 고급 로깅 및 요청 추적 (Advanced Logging & Request Tracing)
*   **기능**: 모든 API 요청에 고유 ID를 부여하고, 로그에 해당 ID를 포함시켜 요청 처리 과정을 추적합니다.
*   **특징**:
    *   `RequestIdInterceptor`: HTTP 요청을 가로채 `txId`라는 고유 ID를 생성하고 로깅 컨텍스트(MDC)에 추가합니다.
    *   **P6Spy**: JPA가 실행하는 SQL 쿼리의 파라미터를 포함한 전체 쿼리 문장을 로그로 확인할 수 있게 설정했습니다. (`OnlyEffectiveSqlFormat`으로 가독성 개선)
    *   `LogController`: 로깅이 어떻게 동작하는지 직접 테스트하기 위한 컨트롤러입니다.

### 2. DTO 데이터 처리 (문자열 공백 자동 제거)
*   **기능**: API 요청 시 JSON 본문에 포함된 문자열 데이터의 앞뒤 공백을 자동으로 제거합니다.
*   **특징**:
    *   `TrimStringDeserializer`와 `JacksonTrimConfig`: Jackson 라이브러리의 역직렬화 과정을 커스터마이징하여, `@NoGlobalTrim` 어노테이션이 없는 한 모든 문자열의 공백을 제거합니다.
    *   `TrimController`: 이 기능이 잘 동작하는지 테스트하는 엔드포인트입니다.

### 3. Enum(열거형) 처리 방법
*   **기능**: Java의 Enum 타입을 API 요청/응답 및 내부 로직에서 효과적으로 사용하는 방법을 테스트합니다.
*   **특징**:
    *   `enumstring` 패키지: Enum에 특정 문자열 값을 부여하고 변환하는 방법을 보여줍니다.
    *   `testenum` 패키지: Enum을 DTO 필드로 사용하여 API 요청을 받는 예제를 구현했습니다. (`EnumController`)

### 4. 보안 및 인증 기능 (TOTP)
*   **기능**: 시간 기반 일회용 비밀번호(TOTP)를 생성하고 검증하는 기능을 구현했습니다. (Google Authenticator 등에서 사용)
*   **특징**:
    *   `TOTPUtil`: TOTP를 생성, 검증하고, 인증 앱에서 스캔할 수 있는 QR 코드용 URL을 만드는 로직이 포함되어 있습니다.

### 5. 유틸리티 기능 (QR 코드 생성)
*   **기능**: 문자열을 입력받아 QR 코드 이미지를 생성합니다.
*   **특징**:
    *   `QRController`와 `BarcodeService`: ZXing 라이브러리를 사용하여 QR 코드를 생성하고 이미지 데이터(byte array)로 반환하는 API를 구현했습니다.

### 6. 최신 Java 기능 테스트 (Java Record)
*   **기능**: Java 14부터 도입된 `record` 타입을 DTO로 사용하는 방법을 테스트합니다.
*   **특징**:
    *   `RecordTestController`: 기존 클래스 기반 `TestDto`와 `record`로 선언된 `RecordDto`를 비교하며 테스트합니다.

### 7. 아키텍처 테스트 (Architectural Testing)
*   **기능**: 프로젝트가 정해진 아키텍처 규칙을 잘 따르는지 자동으로 검증합니다.
*   **특징**:
    *   `archtest` 패키지: ArchUnit 라이브러리를 사용하여 "컨트롤러는 리포지토리를 직접 호출하면 안 된다" 와 같은 아키텍처 규칙을 코드로 정의하고 테스트합니다.

## How to Run

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd etc
   ```

2. **Build the project:**
   ```bash
   ./gradlew build
   ```

3. **Run the application:**
   ```bash
   ./gradlew bootRun
   ```
   The application will start on the port specified in `application.properties` (default: 23512).
