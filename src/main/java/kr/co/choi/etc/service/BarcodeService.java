package kr.co.choi.etc.service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
@Slf4j
public class BarcodeService {

    public void generateQRCode(String data, int width, int height, OutputStream outputStream)
            throws WriterException, IOException {

        // QR 코드 생성 옵션 설정
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // UTF-8 인코딩
        hints.put(EncodeHintType.MARGIN, 0); // 여백 설정
        // hints.put(EncodeHintType.QR_VERSION, 1); // 버전을 1으로 설정
        // 버전 자동 지정

        // QR 코드 생성
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height, hints);

        // BitMatrix를 BufferedImage로 변환
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF); // 검정과 흰색으로 픽셀 채우기
            }
        }

        // 파일로 저장
        ImageIO.write(image, "png", outputStream);

        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        try {
            Result result = new MultiFormatReader().decode(bitmap);

            BarcodeFormat format = result.getBarcodeFormat();
            Map<ResultMetadataType, Object> metadata = result.getResultMetadata();
            log.info("format: {}, text: {}", format, result.getText());

            // 로깅
            Iterator<Map.Entry<ResultMetadataType, Object>> iterator = metadata.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<ResultMetadataType, Object> entry = iterator.next();
                ResultMetadataType key = entry.getKey();
                Object value = entry.getValue();

                if (value instanceof ArrayList<?>) {
                    ArrayList<?> items = (ArrayList<?>) value;
                    for (Object object : items) {
                        log.info("metadata, {}: {}", key, object);
                    }
                } else {
                    log.info("metadata, {}: {}", key, value);
                }
            }

        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
