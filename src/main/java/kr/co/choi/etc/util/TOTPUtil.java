package kr.co.choi.etc.util;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * TOTP 유틸
 *
 * <pre>
 * 사양: RFC 6238 시간 기반 일회용 패스워드(Time-Based One-Time Password, TOTP) 알고리즘을 정의한 표준
 * 문서( Request for Comments )
 *
 * 기능검증 테스트결과: Google Authenticator 앱과 정상 연동 확인
 *
 * QR 포멧 예제
 * otpauth://totp/Example:alice@google.com?secret=JBSWY3DPEHPK3PXP&issuer=Example
 *
 * <pre>
 */
public class TOTPUtil {
    private TOTPUtil() {

    }

    private static final String BASE32_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
    private static final String HMAC_ALGORITHM = "HmacSHA1"; // RFC 6238 기본 HMAC-SHA1

    private static final int TIME_STEP_SECONDS = 30; // TOTP 시간 간격
    private static final int DEFAULT_DIGITS = 6; // 기본 OTP 자리수
    private static final int DEFAULT_TIME_STEP = 0; // 기본 타임스텝 허용 범위

    /**
     * TOTP 생성
     *
     * @param secretKey Base32로 인코딩된 시크릿 키
     * @return 생성된 TOTP
     */
    public static String generateTOTP(String secretKey) {
        return generateTOTP(secretKey, Instant.now().toEpochMilli(), DEFAULT_DIGITS);
    }

    /**
     * TOTP QR 코드 주소
     *
     * @param title     표시이름
     * @param id        OTP 코드 소유자 아이디
     * @param secretKey Base32로 인코딩된 시크릿 키
     * @param issuer    발행자
     * @return
     */
    public static String generateQrCode(String title, String id, String secretKey, String issuer) {
        return "otpauth://totp/" + title + ":" + id + "?secret=" + secretKey + "&issuer=" + issuer;
    }

    /**
     * TOTP 생성
     *
     * @param secretKey       Base32로 인코딩된 시크릿 키
     * @param timestampMillis 타임스탬프 (밀리초)
     * @param digits          OTP 자릿수
     * @return 생성된 TOTP
     */
    public static String generateTOTP(String secretKey, long timestampMillis, int digits) {
        try {
            // Base32 디코딩된 시크릿 키
            byte[] keyBytes = decodeBase32(secretKey);

            // 타임스텝 계산
            long timeStep = timestampMillis / TimeUnit.SECONDS.toMillis(TIME_STEP_SECONDS);

            // 타임스텝을 바이트 배열로 변환
            byte[] timeStepBytes = ByteBuffer.allocate(8).putLong(timeStep).array();

            // HMAC 계산
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, HMAC_ALGORITHM);
            mac.init(keySpec);
            byte[] hmacResult = mac.doFinal(timeStepBytes);

            // 동적 오프셋 계산
            int offset = hmacResult[hmacResult.length - 1] & 0xF;

            // OTP 생성
            int binaryCode = ((hmacResult[offset] & 0x7F) << 24) | ((hmacResult[offset + 1] & 0xFF) << 16)
                    | ((hmacResult[offset + 2] & 0xFF) << 8) | (hmacResult[offset + 3] & 0xFF);

            int otp = binaryCode % (int) Math.pow(10, digits);
            return String.format("%0" + digits + "d", otp);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Error generating TOTP", e);
        }
    }

    /**
     * TOTP 검증
     *
     * @param secretKey Base32로 인코딩된 시크릿 키
     * @param otp       입력받은 OTP
     * @return true: 유효한 OTP, false: 유효하지 않은 OTP
     */
    public static boolean validateTOTP(String secretKey, String otp) {
        return validateTOTP(secretKey, otp, Instant.now().toEpochMilli());
    }

    /**
     * TOTP 검증
     *
     * @param secretKey       Base32로 인코딩된 시크릿 키
     * @param otp             입력받은 OTP
     * @param timestampMillis 검증 시간 (밀리초)
     * @return true: 유효한 OTP, false: 유효하지 않은 OTP
     */
    public static boolean validateTOTP(String secretKey, String otp, long timestampMillis) {
        return validateTOTP(DEFAULT_TIME_STEP, secretKey, otp, timestampMillis);
    }

    /**
     * TOTP 검증
     *
     * @param timeStep        ±timeStep 타임스텝 허용 범위
     * @param secretKey       Base32로 인코딩된 시크릿 키
     * @param otp             입력받은 OTP
     * @param timestampMillis 검증 시간 (밀리초)
     * @return true: 유효한 OTP, false: 유효하지 않은 OTP
     */
    public static boolean validateTOTP(int timeStep, String secretKey, String otp, long timestampMillis) {
        // ±timeStep 타임스텝 허용 범위
        for (int i = (timeStep * -1); i <= timeStep; i++) {
            long adjustedTimestamp = timestampMillis + (i * TimeUnit.SECONDS.toMillis(TIME_STEP_SECONDS));
            String expectedOtp = generateTOTP(secretKey, adjustedTimestamp, DEFAULT_DIGITS);
            if (otp.equals(expectedOtp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Base32 디코딩
     *
     * @param base32 Base32로 인코딩된 문자열
     * @return 디코딩된 바이트 배열
     */
    private static byte[] decodeBase32(String base32) {

        base32 = base32.toUpperCase().replaceAll("=", ""); // Base32는 '=' 패딩 제거
        ByteBuffer buffer = ByteBuffer.allocate(base32.length() * 5 / 8);
        int bits = 0, value = 0;
        for (char c : base32.toCharArray()) {
            int index = BASE32_CHARS.indexOf(c);
            if (index == -1)
                throw new IllegalArgumentException("Invalid Base32 character: " + c);
            value = (value << 5) | index;
            bits += 5;
            if (bits >= 8) {
                buffer.put((byte) (value >> (bits - 8)));
                bits -= 8;
            }
        }
        return buffer.array();
    }

    /**
     * 랜덤 바이너리 데이터를 생성
     *
     * @param length 원하는 바이트 길이
     * @return 바이트 배열
     */
    public static byte[] generateRandomBytes(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * 랜덤 바이너리 데이터를 생성
     *
     * @return 바이트 배열
     */
    public static byte[] generateRandomBytes() {
        return generateRandomBytes(20);
    }

    /**
     * Base32로 인코딩
     *
     * @param data 바이너리 데이터
     * @return Base32로 인코딩된 문자열
     */
    public static String encodeBase32(byte[] data) {
        StringBuilder base32 = new StringBuilder();
        int buffer = 0, bitsLeft = 0;
        for (byte b : data) {
            buffer = (buffer << 8) | (b & 0xFF); // 바이트를 버퍼에 추가
            bitsLeft += 8; // 버퍼에 추가된 비트 수

            while (bitsLeft >= 5) {
                // 상위 5비트를 추출하여 Base32 문자 추가
                base32.append(BASE32_CHARS.charAt((buffer >> (bitsLeft - 5)) & 0x1F));
                bitsLeft -= 5; // 사용된 비트 제거
            }
        }

        if (bitsLeft > 0) {
            // 남은 비트를 처리
            base32.append(BASE32_CHARS.charAt((buffer << (5 - bitsLeft)) & 0x1F));
        }

        return base32.toString();
    }

    // Simplified main method
    public static void main(String[] args) {
        String secretKey = "DT2NT7BPQJPNHMW2UQP5AJQVS4EOFGBE";
        String otp = generateTOTP(secretKey);
        System.out.println("Generated OTP: " + otp);
//        boolean isValid = validateTOTP(secretKey, "761668");
//        System.out.println("Is OTP valid? " + isValid);
    }

    /*
     * public static void main(String[] args) { // Google Authenticator 호환 Base32 키
     * // 20바이트 길이의 랜덤 시크릿 키 생성 //byte[] randomBytes = generateRandomBytes(20);
     * //String secretKey = encodeBase32(randomBytes);
     *
     * // Base32로 인코딩 String secretKey = "DT2NT7BPQJPNHMW2UQP5AJQVS4EOFGBE"; //
     * encodeBase32(randomBytes); System.out.println(secretKey);
     *
     * // TOTP 생성 String otp = generateTOTP(secretKey);
     * System.out.println("Generated OTP: " + otp);
     *
     * // TOTP 검증 boolean isValid = validateTOTP(secretKey, "761668");
     * System.out.println("Is OTP valid? " + isValid); }
     */
}