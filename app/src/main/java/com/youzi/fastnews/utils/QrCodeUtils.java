package com.youzi.fastnews.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

/**
 * Created by ywb on 2016/7/20.
 */
public class QrCodeUtils {

    private QrCodeUtils() {
    }

    /**
     * 根据字符串生成二维码 如果二维码带有logo图片，字符串不应过于简单，否则会造成难以扫描,不带logo没有问题
     */
    public static Bitmap Create2DCode(String str, Resources resources, Boolean needLogo, int resourcesID) throws WriterException,
            UnsupportedEncodingException {
        //调整生成二维码时候，白色背景过大的问题。
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);
        // 调整下面的参数可调整二维码logo的大小（1000,1000）
        BitMatrix matrix = new MultiFormatWriter().encode(new String(str.getBytes("UTF8"), "ISO-8859-1"), BarcodeFormat.QR_CODE, 1000, 1000, hints);

        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff010101;
                } else {
                    pixels[y * width + x] = 0xffffffff;//为了防止存储在本地的时候，二维码变成黑色图片
                }

            }
        }

        Bitmap qrCodeBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        qrCodeBitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        // ------------------添加logo部分------------------//
        if (needLogo) {
            Bitmap logoBmp = BitmapFactory.decodeResource(resources, resourcesID);

            // 二维码和logo合并
            Bitmap bitmapWithLogo = Bitmap.createBitmap(qrCodeBitmap.getWidth(), qrCodeBitmap.getHeight(),
                    qrCodeBitmap.getConfig());

            Canvas canvas = new Canvas(bitmapWithLogo);
            // 二维码
            canvas.drawBitmap(qrCodeBitmap, 0, 0, null);
            // logo绘制在二维码中央
            canvas.drawBitmap(logoBmp, qrCodeBitmap.getWidth() / 2 - logoBmp.getWidth() / 2, qrCodeBitmap.getHeight()
                    / 2 - logoBmp.getHeight() / 2, null);
            return bitmapWithLogo;
        } else {
            return qrCodeBitmap;
        }

    }

}
