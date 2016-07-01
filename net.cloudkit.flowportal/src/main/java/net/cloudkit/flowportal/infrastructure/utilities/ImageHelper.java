/*
 * Copyright (C) 2015. The CloudKit Open Source Project
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
package net.cloudkit.flowportal.infrastructure.utilities;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;

/**
 * 图像文件工具
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年11月6日 上午9:49:00
 */
public class ImageHelper {

    /**
     * 图片的后缀
     */
    public static final String[] IMAGE_EXT = new String[] { ".jpg", ".jpeg", ".gif", ".png", ".bmp" };

    /**
     * 缩放图像
     *
     * @param sourceFile 源图像文件地址
     * @param targetFile 缩放后的图像地址
     * @param scale 缩放比例
     * @param flag 缩放选择:true 放大; false 缩小;
     */
    public static void scale(File sourceFile, File targetFile, int scale, boolean flag) {
        try {
            BufferedImage src = ImageIO.read(sourceFile); // 读入文件
            int width = src.getWidth(); // 得到源图宽
            int height = src.getHeight(); // 得到源图长
            if (flag) {
                // 放大
                width = width * scale;
                height = height * scale;
            } else {
                // 缩小
                width = width / scale;
                height = height / scale;
            }
            // 这就是原图片的缩放比例对象
            Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            // 下面这个对象其实是一个空白的缓冲字符图象
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 绘制对象
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 开始绘制缩小后的图
            g.dispose();// 最终形成了一个图像

            // 再以输出到文件流的方式保存图像文件
            ImageIO.write(tag, "JPEG", targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 缩放图像,根据长宽最大的长度进行缩放
     *
     * @param sourceFile 源图像文件地址
     * @param targetFile 缩放后的图像地址
     * @param targetFile 缩放比例
     * @param targetWidth 缩放选择:true 放大; false 缩小;
     */
    public static void scale(File sourceFile, File targetFile, int targetWidth) {
        try {
            BufferedImage bufferedImage = ImageIO.read(sourceFile); // 读入文件
            int width = bufferedImage.getWidth(); // 得到源图宽
            int height = bufferedImage.getHeight(); // 得到源图长

            float scale = (float) width / (float) targetWidth;
            float targetHeight = (float) height / scale;

            // 这就是原图片的缩放比例对象
            Image image = bufferedImage.getScaledInstance(targetWidth, (int) targetHeight, Image.SCALE_DEFAULT);
            // 下面这个对象其实是一个空白的缓冲字符图象
            BufferedImage tag = new BufferedImage(targetWidth, (int) targetHeight, BufferedImage.TYPE_INT_RGB);
            // 绘制对象
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 开始绘制缩小后的图
            g.dispose();// 最终形成了一个图像

            // 再以输出到文件流的方式保存图像文件
            ImageIO.write(tag, "PNG", targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 切割图片
     *
     * @param x 截点横坐标 (从左开始计数)
     * @param y 截点纵坐标 (从上开始计数)
     * @param width 截取的宽度
     * @param height 截取的长度
     * @param oldpath 图片位置
     * @param newpath 新生成的图片位置
     */
    public static void cut(int x, int y, int width, int height, String oldpath, String newpath) {

        FileInputStream is = null;
        ImageInputStream iis = null;

        // 这个是获取图片扩展名的方法，比如：jpg。
        String imgType = getFileType(oldpath);

        try {
            is = new FileInputStream(oldpath);
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(imgType);
            ImageReader reader = it.next();
            iis = ImageIO.createImageInputStream(is);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            Point p = new Point();
            p.setLocation(x, y);

            Dimension d = new Dimension();
            d.setSize(width, height);
            Rectangle rect = new Rectangle(p, d);
            param.setSourceRegion(rect);

            BufferedImage bi = reader.read(0, param);
            ImageIO.write(bi, imgType, new File(newpath));

            is.close();
            iis.close();
        } catch (Exception e) {

        }
    }

    /**
     * 图像类型转换 GIF->JPG GIF->PNG PNG->JPG PNG->GIF(X)
     */
    public static void convert(String source, String target) {
        try {
            File f = new File(source);
            f.canRead();
            f.canWrite();
            BufferedImage src = ImageIO.read(f);
            ImageIO.write(src, "JPG", new File(target));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 彩色转为黑白
     *
     * @param source 目标目录文件
     * @param target 存取目录文件
     */
    public static void gray(String source, String target) {
        try {
            BufferedImage src = ImageIO.read(new File(source));
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            src = op.filter(src, null);
            ImageIO.write(src, "JPEG", new File(target));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得图片类型(gif,jpg,png)
     *
     * @param path 图片完整路径
     * @return 图片类型
     */
    public static String getFileType(String path) {

        String type = path.substring(path.lastIndexOf(".") + 1);
        if (type.indexOf("?") != -1) {
            type = type.substring(0, type.indexOf("?"));
        }
        if (type.indexOf("&") != -1) {
            type = type.substring(0, type.indexOf("&"));
        }
        return type;
    }

    /**
     * 获得图片类型(gif,jpg,png)
     *
     * @param str 图片完整路径
     * @return 图片类型
     */
    public static String getImageType(String str) {
        String imgType;
        if (str.indexOf("JFIF") >= 0) {
            imgType = "jpg";
        } else if (str.indexOf("NG") >= 0) {
            imgType = "png";
        } else if (str.indexOf("GIF") >= 0) {
            imgType = "gif";
        } else if (str.indexOf("BM") >= 0) {
            imgType = "bmp";
        } else {
            imgType = "jpg";
        }
        return imgType;
    }

    /**
     * 是否是图片
     *
     * @param ext
     * @return "jpg", "jpeg", "gif", "png", "bmp" 为文件后缀名者为图片
     */
    public static boolean isImage(String ext) {
        ext = ext.toLowerCase(Locale.ENGLISH);
        for (String s : IMAGE_EXT) {
            if (s.equalsIgnoreCase(ext)) {
                return true;
            }
        }
        return false;
    }


//    /**
//     * 获得水印位置
//     *
//     * @param width   原图宽度
//     * @param height  原图高度
//     * @param p 水印位置 1-5，其他值为随机。1：左上；2：右上；3：左下；4：右下；5：中央。
//     * @param offsetX 水平偏移。
//     * @param offsetY 垂直偏移。
//     * @return 水印位置
//     */
//    public static Position markPosition(int width, int height, int p,
//                                        int offsetX, int offsetY) {
//        if (p < 1 || p > 5) {
//            p = (int) (Math.random() * 5) + 1;
//        }
//        int x, y;
//        switch (p) {
//            // 左上
//            case 1:
//                x = offsetX;
//                y = offsetY;
//                break;
//            // 右上
//            case 2:
//                x = width + offsetX;
//                y = offsetY;
//                break;
//            // 左下
//            case 3:
//                x = offsetX;
//                y = height + offsetY;
//                break;
//            // 右下
//            case 4:
//                x = width + offsetX;
//                y = height + offsetY;
//                break;
//            // 中央
//            case 5:
//                x = (width / 2) + offsetX;
//                y = (height / 2) + offsetY;
//                break;
//            default:
//                throw new RuntimeException("never reach ...");
//        }
//        return new Position(x, y);
//    }
//
//    /**
//     * 水印位置
//     * 包含左边偏移量，右边偏移量。
//     */
//    public static class Position {
//        private int x;
//        private int y;
//
//        public Position(int x, int y) {
//            this.x = x;
//            this.y = y;
//        }
//
//        public int getX() {
//            return x;
//        }
//
//        public void setX(int x) {
//            this.x = x;
//        }
//
//        public int getY() {
//            return y;
//        }
//
//        public void setY(int y) {
//            this.y = y;
//        }
//    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String src = "D:\\1-1.png";
        // 超过最大源图片的规格就是全部
        cut(100, 10, 2000, 2000, src, "D:\\1-1-1-1.png");
        // cut("e:/1.jpg", "e:/t/", 200, 150);
    }
}
