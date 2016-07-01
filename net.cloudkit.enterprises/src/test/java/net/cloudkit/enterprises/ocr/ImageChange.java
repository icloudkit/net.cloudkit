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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

/**
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016/4/6 19:52
 */
public class ImageChange {

    public static BufferedImage rotateImage(BufferedImage image, int degree, Color bgcolor) throws IOException {

        // 原始图象的宽度
        int iw = image.getWidth();
        // 原始图象的高度
        int ih = image.getHeight();
        int w = 0;
        int h = 0;
        int x = 0;
        int y = 0;
        degree = degree % 360;
        if (degree < 0)
            // 将角度转换到0-360度之间
            degree = 360 + degree;
        // 将角度转为弧度
        double ang = Math.toRadians(degree);

        /**
         *确定旋转后的图象的高度和宽度
         */
        if (degree == 180 || degree == 0 || degree == 360) {
            w = iw;
            h = ih;
        } else if (degree == 90 || degree == 270) {
            w = ih;
            h = iw;
        } else {
            int d = iw + ih;
            w = (int) (d * Math.abs(Math.cos(ang)));
            h = (int) (d * Math.abs(Math.sin(ang)));
        }

        // 确定原点坐标
        x = (w / 2) - (iw / 2);
        y = (h / 2) - (ih / 2);
        BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
        Graphics2D gs = (Graphics2D) rotatedImage.getGraphics();
        if (bgcolor == null) {
            rotatedImage = gs.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        } else {
            gs.setColor(bgcolor);
            // 以给定颜色绘制旋转后图片的背景
            gs.fillRect(0, 0, w, h);
        }

        AffineTransform at = new AffineTransform();
        // 旋转图象
        at.rotate(ang, w / 2, h / 2);
        at.translate(x, y);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
        op.filter(image, rotatedImage);
        image = rotatedImage;

        // ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        // ImageOutputStream iamgeOut = ImageIO.createImageOutputStream(byteOut);

        // ImageIO.write(image, "png", iamgeOut);
        // InputStream inputStream = new ByteArrayInputStream(byteOut.toByteArray());

        return image;
    }


}

