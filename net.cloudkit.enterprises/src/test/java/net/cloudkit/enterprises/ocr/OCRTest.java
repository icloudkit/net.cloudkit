/*
 * Copyright (C) 2016. The CloudKit Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.cloudkit.enterprises.ocr;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016/4/5 20:53
 */
public class OCRTest {

//    public static BufferedImage rotateImage(BufferedImage image, int degree, Color backgroundColor) throws IOException {
//
//        // 原始图象的宽度
//        int iw = image.getWidth();
//        // 原始图象的高度
//        int ih = image.getHeight();
//        int w = 0;
//        int h = 0;
//        int x = 0;
//        int y = 0;
//        degree = degree % 360;
//        if (degree < 0)
//            // 将角度转换到0-360度之间
//            degree = 360 + degree;
//        // 将角度转为弧度
//        double ang = Math.toRadians(degree);
//
//        /**
//         *确定旋转后的图象的高度和宽度
//         */
//        if (degree == 180 || degree == 0 || degree == 360) {
//            w = iw;
//            h = ih;
//        } else if (degree == 90 || degree == 270) {
//            w = ih;
//            h = iw;
//        } else {
//            int d = iw + ih;
//            w = (int) (d * Math.abs(Math.cos(ang)));
//            h = (int) (d * Math.abs(Math.sin(ang)));
//        }
//
//        // 确定原点坐标
//        x = (w / 2) - (iw / 2);
//        y = (h / 2) - (ih / 2);
//        BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
//        Graphics2D gs = (Graphics2D) rotatedImage.getGraphics();
//        if (backgroundColor == null) {
//            rotatedImage = gs.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
//        } else {
//            gs.setColor(backgroundColor);
//            // 以给定颜色绘制旋转后图片的背景
//            gs.fillRect(0, 0, w, h);
//        }
//
//        AffineTransform at = new AffineTransform();
//        // 旋转图象
//        at.rotate(ang, w / 2, h / 2);
//        at.translate(x, y);
//        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
//        op.filter(image, rotatedImage);
//        image = rotatedImage;
//
//        // ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
//        // ImageOutputStream iamgeOut = ImageIO.createImageOutputStream(byteOut);
//
//        // ImageIO.write(image, "png", iamgeOut);
//        // InputStream inputStream = new ByteArrayInputStream(byteOut.toByteArray());
//
//        return image;
//    }

    public static void main(String[] agrs) throws IOException {

        int width = 25;
        int height = 25;
        int fontSize = 20;

        for(int num = 0; num < 10; num ++) {

            // 创建内存图像
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 获取图形上下文
            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
            // 画背景图
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            // 设定字体
            Font font = new Font("Microsoft YaHei UI", Font.PLAIN, fontSize);
            graphics.setFont(font);
            // graphics.setColor(Color.BLACK);

            // 设置字体在图片中的位置 在这里是居中
            FontRenderContext context = graphics.getFontRenderContext();
            Rectangle2D bounds = font.getStringBounds(String.valueOf(num), context);
            double x = (width - bounds.getWidth()) / 2;
            double y = (height - bounds.getHeight()) / 2;
            System.out.println("x:" + x + ", y:" + y + " " + bounds.getWidth() + "-" + bounds.getHeight());

            // graphics.setPaint(new Color(10, 0, 149));
            graphics.setPaint(Color.BLACK);
            // 将验证码字符显示到图象中
            graphics.drawString(String.valueOf(num), (int) x, (int) bounds.getHeight() - 2);
            graphics.dispose();

            for (int i = -60; i <= 60; i++) {

                // image = rotateImage(bufferedImage, i, Color.WHITE);

                BufferedImage rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D gs = (Graphics2D) rotatedImage.getGraphics();
                gs.setColor(Color.WHITE);
                // 以给定颜色绘制旋转后图片的背景
                gs.fillRect(0, 0, width, height);

                AffineTransform at = new AffineTransform();
                int degree = i % 360;
                if (degree < 0)
                    // 将角度转换到0-360度之间
                    degree = 360 + degree;
                // 将角度转为弧度
                double ang = Math.toRadians(degree);
                // 旋转图象
                // at.rotate(i * Math.PI / 180);
                at.rotate(ang, width / 2, height / 2);
                // at.translate(x, y);
                AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
                op.filter(bufferedImage, rotatedImage);

                FileOutputStream fos = new FileOutputStream("D:\\test\\" + num + "_" + (i + 60) + ".jpg");
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
                encoder.encode(rotatedImage);
                fos.close();
            }
        }

        /*
        // 平移原点到图形环境的中心
        graphics.translate(width / 2, height / 2);

        // 旋转文本
        for (int i = -60; i < 60; i++) {
            graphics.rotate(i * Math.PI / 180);
            graphics.setPaint(Color.BLACK);
            graphics.drawString(num, 0, 0);
            break;
        }
        graphics.dispose();
        */
    }
}
