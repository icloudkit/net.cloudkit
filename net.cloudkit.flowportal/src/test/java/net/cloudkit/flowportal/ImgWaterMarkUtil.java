///*
// * Copyright (C) 2015. The CloudKit Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package net.cloudkit.flowportal;
//
//import java.awt.Image;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//
//import javax.imageio.ImageIO;
//
///**
// * 图片
// *
// * @author Administrator
// *
// */
//public class ImgWaterMarkUtil {
//
//    public static void main(String[] args) {
//        try {
//            String sourcepath = "d://ExpCustoms.png";
//            String icconpath = "d://3.jpg";
//
//            File file = new File("d://5s.jpg");
//            OutputStream out = new FileOutputStream(file);
//            new ImgWaterMarkUtil().ImgWaterMarkUtil(sourcepath, icconpath, out,300,0);
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 水印图片
//     *
//     * @param sourceImg1
//     * @param sourceImg2
//     * @param os
//     * @throws Exception
//     */
//    public int ImgWaterMarkUtil(String sourcepath, String icconpath,
//                                OutputStream os , int deviationX, int deviationY)  {
//        int result = -1;
//        try {
//            BufferedImage sourceImg1 = ImageIO.read(new File(sourcepath));
//            BufferedImage sourceImg2 = ImageIO.read(new File(icconpath));
//            BufferedImage backgroundImg = null, waterMarkImg = null;
//            // 确认背景图片和水印
//            if (sourceImg1.getWidth() > sourceImg2.getWidth()
//                && sourceImg1.getHeight() > sourceImg2.getHeight()) {
//                backgroundImg = sourceImg1;
//                waterMarkImg = sourceImg2;
//            } else {
//                System.err.println("水印图片宽高不能小于背景图宽高");
//                return result;
//            }
//            //	this.getGrayPicture(backgroundImg);
//            this.getGrayPicture(waterMarkImg);
////		 backgroundImg = this.compressImg(backgroundImg,600,600);
////		 waterMarkImg = this.compressImg(waterMarkImg,200,200);
//            backgroundImg = this.watermarkImg(backgroundImg, waterMarkImg,
//                deviationX, deviationY);
//            this.exportImg(backgroundImg, os);
//            result = 1;
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    /**
//     * 去掉img杂质 和空白
//     *
//     * @param originalImage
//     * @return
//     */
//    public BufferedImage getGrayPicture(BufferedImage originalImage) {
//        int green = 0, red = 0, blue = 0, rgb;
//        int imageWidth = originalImage.getWidth();
//        int imageHeight = originalImage.getHeight();
//        int minX = 0, minY = 0;
//        int maxX = 0, maxY = 0;
//        for (int i = originalImage.getMinX(); i < imageWidth; i++) {
//            for (int j = originalImage.getMinY(); j < imageHeight; j++) {
//                Object data = originalImage.getRaster().getDataElements(i, j,
//                    null);
//                red = originalImage.getColorModel().getRed(data);
//                blue = originalImage.getColorModel().getBlue(data);
//                green = originalImage.getColorModel().getGreen(data);
//
//                if ((Math.abs(red - blue) >= 50 || Math.abs(red - green) >= 50 || Math
//                    .abs(blue - green) >= 50)
//                    || (red <= 150 && blue <= 150 && green <= 150)) {
//                    if (minX == 0 || minX > i) {
//                        minX = i;
//                    }
//                    if (minY == 0 || minY > j) {
//                        minY = j;
//                    }
//
//                    if (maxX < i) {
//                        maxX = i;
//                    }
//                    if (maxY < j) {
//                        maxY = j;
//                    }
//                } else {
//                    rgb = (255 * 256 + 255) * 256 + 255;
//                    if (rgb > 8388608) {
//                        rgb = rgb - 16777216;
//                    }
//                    originalImage.setRGB(i, j, rgb);
//                }
//            }
//        }
//        originalImage = originalImage.getSubimage(minX < 2 ? 0 : minX - 1,
//            minY < 2 ? 0 : minY - 1, minX - 2 <= 0 ? maxX + 1 : maxX - minX
//                + 4, maxY - minY + 1);
//        return originalImage;
//    }
//
//    /**
//     * 压缩图片
//     *
//     * @param sourceImg
//     * @return
//     */
//    public BufferedImage compressImg(BufferedImage sourceImg, int width,
//                                     int height) {
//        BufferedImage tagImg = new BufferedImage(width, height,
//            BufferedImage.TYPE_INT_RGB);
//        tagImg.getGraphics().drawImage(
//            sourceImg.getScaledInstance(width, height, Image.SCALE_SMOOTH),
//            0, 0, null);
//        sourceImg = tagImg;
//        return tagImg;
//    }
//
//    /**
//     * 水印圖片
//     *
//     * @param backgroundImg
//     * @param waterMarkImg
//     * @param deviationX 偏移量x
//     * @param deviationY 偏移量Y
//     * @return
//     */
//    public BufferedImage watermarkImg(BufferedImage backgroundImg,
//                                      BufferedImage waterMarkImg, int deviationX, int deviationY) {
//        // 打水印
//        BufferedImage resultImg = new BufferedImage(backgroundImg.getWidth(),
//            backgroundImg.getHeight(), BufferedImage.TYPE_INT_RGB);
//        for (int i = backgroundImg.getMinX(); i < backgroundImg.getWidth(); i++) {
//            for (int j = backgroundImg.getMinY(); j < backgroundImg.getHeight(); j++) {
//                resultImg.setRGB(i, j, backgroundImg.getRGB(i, j));
//            }
//        }
//
//        int beginX = deviationX, beginY = 0;
//        if (backgroundImg.getHeight() - deviationY - waterMarkImg.getHeight() > 0) {
//            beginY = backgroundImg.getHeight() - deviationY - waterMarkImg.getHeight();
//        }
//
//        int green = 0, red = 0, blue = 0;
//        for (int i = waterMarkImg.getMinX(); i < waterMarkImg.getWidth(); i++) {
//            for (int j = waterMarkImg.getMinY(); j < waterMarkImg.getHeight(); j++) {
//                Object data = waterMarkImg.getRaster().getDataElements(i, j,
//                    null);// 获取该点像素，并以object类型表示
//                red = waterMarkImg.getColorModel().getRed(data);
//                blue = waterMarkImg.getColorModel().getBlue(data);
//                green = waterMarkImg.getColorModel().getGreen(data);
//                if (!(red == 255 && blue == 255 && green == 255) && (beginX + i < resultImg.getWidth() &&  beginY + j <  resultImg.getHeight())) {
//                    resultImg.setRGB(deviationX + i, beginY + j,
//                        waterMarkImg.getRGB(i, j));
//                }
//            }
//        }
//        return resultImg;
//    }
//
//    /**
//     * 输出图片
//     *
//     * @param sourceImg
//     * @param out
//     * @return
//     */
//    public int exportImg(BufferedImage sourceImg, OutputStream out) {
//        // 1代表成功 -1代表失败
//        int result = -1;
//        try {
//            ImageIO.write(sourceImg, "png", out);
//            sourceImg.flush();
//            result = 1;
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally{
//            try {
//                if(out != null){
//                    out.close();
//                }
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//        return result;
//    }
//}
