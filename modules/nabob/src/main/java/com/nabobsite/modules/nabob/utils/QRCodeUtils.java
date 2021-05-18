package com.nabobsite.modules.nabob.utils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName nada
 * @Author lihai
 * @Date 2021/1/6 3:37 下午
 * @Version 1.0
 */
public class QRCodeUtils {
    public static void main(String[] args) throws Exception {
        String url = "https://www.cnblogs.com/ChromeT/p/11806909.html";
        String result = makeShareCode(url);
        System.out.println(result);
    }

    /**
     * @desc 制作二维码
     * @author nada
     * @create 2021/1/6 3:38 下午
    */
    public static String makeShareCode(String content)  {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            //容错级别为H
            hints.put(EncodeHintType.ERROR_CORRECTION , ErrorCorrectionLevel.H);
            //白边的宽度，可取0~4
            hints.put(EncodeHintType.MARGIN , 0);
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 150, 150,hints);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            Base64.Encoder encoder = Base64.getEncoder();
            String text = encoder.encodeToString(outputStream.toByteArray());
            return "data:image/png;base64,"+text;
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
