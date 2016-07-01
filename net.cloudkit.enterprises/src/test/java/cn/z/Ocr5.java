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

package cn.z;

import cn.z.svm.svm_predict;
import cn.z.svm.svm_train;
import cn.z.util.CommonUtil;
import com.jhlabs.image.ScaleFilter;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ocr5 {

    private static String clazz = Ocr5.class.getSimpleName();
    private static Map<BufferedImage, String> trainMap = null;
    private static int whiteThreshold = 600;
    private static boolean useSvm = true;

    public static int getColorBright(int colorInt) {
        Color color = new Color(colorInt);
        return color.getRed() + color.getGreen() + color.getBlue();
    }

    public static int isBlack(int colorInt, int blackThreshold) {
        final Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() <= blackThreshold) {
            return 1;
        }
        return 0;
    }

    public static int isWhite(int colorInt, int whiteThreshold) {
        final Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() > whiteThreshold) {
            return 1;
        }
        return 0;
    }

    public static BufferedImage removeBackgroud(File picFile) throws Exception {

        BufferedImage img = ImageIO.read(picFile);
        final int width = img.getWidth();
        final int height = img.getHeight();

        // int blackThreshold = 300;

        // img.getMinX() img.getMinY()
        for (int x = img.getMinX(); x < width; x++) {
            for (int y = img.getMinY(); y < height; y++) {

                Color color = new Color(img.getRGB(x, y));
                if((color.getBlue() < 120) || ((color.getRed() + color.getGreen() + color.getBlue()) < 50)) { //
                    img.setRGB(x, y, Color.WHITE.getRGB());
                } else if((color.getRed() + color.getGreen() + color.getBlue()) < 400) {
                    img.setRGB(x, y, Color.BLACK.getRGB());
                }

                int nearly = 0;
                int vertical = 0;
                int horizontal = 0;

                if(x > 0) {
                    Color leftColor = new Color(img.getRGB(x - 1, y));
                    if((leftColor.getRed() + leftColor.getGreen() + leftColor.getBlue()) < 400) {
                        nearly ++;
                        horizontal ++;
                    }
                }
                if(x < width - 1) {
                    Color rightColor = new Color(img.getRGB(x + 1, y));
                    if((rightColor.getRed() + rightColor.getGreen() + rightColor.getBlue()) < 400) {
                        nearly ++;
                        horizontal ++;
                    }
                }
                if(y > 0) {
                    Color topColor = new Color(img.getRGB(x, y - 1));
                    if((topColor.getRed() + topColor.getGreen() + topColor.getBlue()) < 400) {
                        nearly ++;
                        vertical ++;
                    }
                }
                if(y < height - 1) {
                    Color bottomColor = new Color(img.getRGB(x, y + 1));
                    if((bottomColor.getRed() + bottomColor.getGreen() + bottomColor.getBlue()) < 400) {
                        nearly ++;
                        vertical ++;
                    }
                }

                if(x > 0 && y > 0) {
                    Color leftTopColor = new Color(img.getRGB(x - 1, y - 1));
                    if((leftTopColor.getRed() + leftTopColor.getGreen() + leftTopColor.getBlue()) < 400) {
                        nearly ++;
                    }
                }
                if(x < width - 1 && y < height - 1) {
                    Color rightBottomColor = new Color(img.getRGB(x + 1, y + 1));
                    if((rightBottomColor.getRed() + rightBottomColor.getGreen() + rightBottomColor.getBlue()) < 400) {
                        nearly ++;
                    }
                }
                if(x < width - 1 && y > 0) {
                    Color rightTopColor = new Color(img.getRGB(x + 1, y - 1));
                    if((rightTopColor.getRed() + rightTopColor.getGreen() + rightTopColor.getBlue()) < 400) {
                        nearly ++;
                    }
                }
                if(x > 0 && y < height - 1) {
                    Color leftBottomColor = new Color(img.getRGB(x - 1, y + 1));
                    if((leftBottomColor.getRed() + leftBottomColor.getGreen() + leftBottomColor.getBlue()) < 400) {
                        nearly ++;
                    }
                }

                if(nearly < 2) {
                    img.setRGB(x, y, Color.WHITE.getRGB());
                }
                /*
                if (horizontal < 1 && vertical > 0) {
                    img.setRGB(x, y, Color.WHITE.getRGB());
                }
                if (horizontal > 0 && vertical < 1) {
                    img.setRGB(x, y, Color.WHITE.getRGB());
                }
                */





                /*
                if (isWhite(img.getRGB(x, y), whiteThreshold) == 1) {
                    img.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    img.setRGB(x, y, Color.BLACK.getRGB());
                }


                if (getColorBright(img.getRGB(x, y)) < 100) {
                    int count = isBlack(img.getRGB(x - 1, y), blackThreshold) + isBlack(img.getRGB(x + 1, y), blackThreshold) + isBlack(img.getRGB(x, y - 1), blackThreshold) + isBlack(img.getRGB(x, y + 1), blackThreshold) + isBlack(img.getRGB(x + 1, y + 1), blackThreshold) + isBlack(img.getRGB(x - 1, y - 1), blackThreshold) + isBlack(img.getRGB(x + 1, y -1 ), blackThreshold) + isBlack(img.getRGB(x - 1, y + 1), blackThreshold);
                    System.out.println(count);
                    if (count < 2) {
                        img.setRGB(x, y, Color.WHITE.getRGB());
                    }
                //     img.setRGB(x, y, Color.WHITE.getRGB());
                }
                */



//                if(getColorBright(img.getRGB(x, y)) > 600) {
//                    img.setRGB(x, y, Color.WHITE.getRGB());
//                } else {
//                    /*
//                    // 获取Graphics2D
//                    Graphics2D g2d = img.createGraphics();
//                    // 设置透明度
//                    // 1.0f为透明度 ，值从0-1.0，依次变得不透明
//                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
//                    // 画图
//                    g2d.setColor(new Color(255,0,0));
//                    g2d.setStroke(new BasicStroke(1));
//                    // g2d.draw
//                    // 释放对象
//                    // 透明度设置 结束
//                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
//                    g2d.dispose();
//                    */
//
//                    img.setRGB(x, y, Color.BLACK.getRGB());
//                    /*
//                    System.out.println(getColorBright(img.getRGB(x, y)) + ":");
//                    System.out.println(getColorBright(img.getRGB(x + 1, y)) + "-" + getColorBright(img.getRGB(x + 1, y + 1)) + "-" + getColorBright(img.getRGB(x, y + 1)));
//                    System.out.println(getColorBright(img.getRGB(x - 1, y)) + "-" + getColorBright(img.getRGB(x - 1, y - 1)) + "-" + getColorBright(img.getRGB(x, y - 1)));
//                    System.out.println(getColorBright(img.getRGB(x - 1, y + 1)) + "-" + getColorBright(img.getRGB(x + 1, y - 1)));
//                    */
//
//                    /*
//                    int i = 0;
//                    i = ((x < width - 1) && getColorBright(img.getRGB(x + 1, y)) < 30)? i + 1 : i;
//                    i = ((x < width - 1) && (y < height - 1) && getColorBright(img.getRGB(x + 1, y + 1)) < 30)? i + 1 : i;
//                    i = ((y < height - 1) && getColorBright(img.getRGB(x, y + 1)) < 30)? i + 1 : i;
//                    i = ((x > 0) && getColorBright(img.getRGB(x - 1, y)) < 30)? i + 1 : i;
//                    i = ((x > 0) && (y > 0) && getColorBright(img.getRGB(x - 1, y - 1)) < 30)? i + 1 : i;
//                    i = ((y > 0) && getColorBright(img.getRGB(x, y - 1)) < 30)? i + 1 : i;
//                    i = ((x < width - 1) && (y > 0) && getColorBright(img.getRGB(x + 1, y - 1)) < 30)? i + 1 : i;
//                    i = ((x > 0) && (y < height - 1) && getColorBright(img.getRGB(x - 1, y + 1)) < 30)? i + 1 : i;
//
//                    if(i > 1) {
//                        img.setRGB(x, y, Color.BLACK.getRGB());
//                    } else {
//                        img.setRGB(x, y, Color.WHITE.getRGB());
//                    }
//                    */
//                }

                /*
                int i = 0;
                i = (getColorBright(img.getRGB(x + 1, y)) == 0)? i + 1 : i;
                i = (getColorBright(img.getRGB(x + 1, y + 1)) == 0)? i + 1 : i;
                i = (getColorBright(img.getRGB(x, y + 1)) == 0)? i + 1 : i;
                i = (getColorBright(img.getRGB(x - 1, y)) == 0)? i + 1 : i;
                i = (getColorBright(img.getRGB(x - 1, y - 1)) == 0)? i + 1 : i;
                i = (getColorBright(img.getRGB(x, y - 1)) == 0)? i + 1 : i;

                System.out.println(getColorBright(img.getRGB(x, y)) + ":");
                System.out.println(getColorBright(img.getRGB(x + 1, y)) + "-" + getColorBright(img.getRGB(x + 1, y + 1)) + "-" + getColorBright(img.getRGB(x, y + 1)));
                System.out.println(getColorBright(img.getRGB(x - 1, y)) + "-" + getColorBright(img.getRGB(x - 1, y - 1)) + "-" + getColorBright(img.getRGB(x, y - 1)));
                System.out.println(getColorBright(img.getRGB(x - 1, y + 1)) + "-" + getColorBright(img.getRGB(x + 1, y - 1)));
                if(getColorBright(img.getRGB(x, y)) == 0 &&  i < 3) {
                    img.setRGB(x, y, Color.WHITE.getRGB());
                }
                */


                /*
                // 图片的像素点其实是个矩阵，这里利用两个for循环来对每个像素进行操作
                // 获取该点像素，并以object类型表示
                Object data = img.getRaster().getDataElements(x, y, null);
                int red = img.getColorModel().getRed(data);
                int blue = img.getColorModel().getBlue(data);
                int green = img.getColorModel().getGreen(data);
                System.out.println((red + blue + green) + "-" + getColorBright(img.getRGB(x, y)));
                red = (red * 3 + green * 6 + blue * 1)/10;
                green = red;
                blue = green;

                // 这里将r、g、b再转化为rgb值，因为bufferedImage没有提供设置单个颜色的方法，只能设置rgb。rgb最大为8388608，当大于这个值时，应减去255*255*255即16777216
                int rgb = (red * 256 + green) * 256 + blue;
                if(rgb > 8388608) {
                    rgb = rgb - 16777216;
                }
                // 将rgb值写回图片
                img.setRGB(x, y, rgb);
                */

            }
        }
        // img = img.getSubimage(1, 1, img.getWidth() - 2, img.getHeight() - 2);
        return img;
    }

    /*
    private static List<BufferedImage> splitImage(BufferedImage img, String filename) throws Exception {
        final List<BufferedImage> subImgs = new ArrayList<BufferedImage>();
        subImgs.add(img.getSubimage(0, 0, 16, 25));
        subImgs.add(img.getSubimage(16, 0, 16, 25));
        subImgs.add(img.getSubimage(32, 0, 16, 25));
        subImgs.add(img.getSubimage(48, 0, 16, 25));

//        // TODO
//        for(int i = 0; i < subImgs.size(); i++) {
//            FileOutputStream fos = new FileOutputStream("D:\\test\\img" + filename + i + ".jpg");
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
//            encoder.encode(subImgs.get(i));
//            fos.close();
//        }
        return subImgs;
    }
    */

    public static List<BufferedImage> splitImage(BufferedImage img, String filename) throws Exception {
        final List<BufferedImage> subImgs = new ArrayList<BufferedImage>();
        final int width = img.getWidth();
        final int height = img.getHeight();
        final List<Integer> weightlist = new ArrayList<Integer>();
        for (int x = 0; x < width; ++x) {
            int count = 0;
            for (int y = 0; y < height; ++y) {
                if (CommonUtil.isWhite(img.getRGB(x, y), whiteThreshold) == 0) {
                    count++;
                }
            }
            weightlist.add(count);
        }
        for (int i = 0; i < weightlist.size(); i++) {
            int length = 0;
            while (i < weightlist.size() && weightlist.get(i) > 0) {
                i++;
                length++;
            }
            if (length > 18) {
                subImgs.add(CommonUtil.removeBlank(img.getSubimage(i - length, 0, length / 2, height), whiteThreshold, 0));
                subImgs.add(CommonUtil.removeBlank(img.getSubimage(i - length / 2, 0, length / 2, height), whiteThreshold, 0));
            } else if (length > 5) {
                subImgs.add(CommonUtil.removeBlank(img.getSubimage(i - length, 0, length, height), whiteThreshold, 0));
            }
        }

//        for(int i = 0; i < subImgs.size(); i++) {
//            FileOutputStream fos = new FileOutputStream("D:\\test\\img" + filename + i + ".jpg");
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
//            encoder.encode(subImgs.get(i));
//            fos.close();
//        }
        return subImgs;
    }

    public static boolean isNotEight(BufferedImage img) {
        final int width = img.getWidth();
        final int height = img.getHeight();
        int minCount = width;
        for (int y = height / 2 - 2; y < height / 2 + 2; ++y) {
            int count = 0;
            for (int x = 0; x < width / 2 + 2; ++x) {
                if (CommonUtil.isWhite(img.getRGB(x, y), whiteThreshold) == 0) {
                    count++;
                }
            }
            minCount = Math.min(count, minCount);
        }
        return minCount < 2;
    }

    public static boolean isNotThree(BufferedImage img) {
        final int width = img.getWidth();
        final int height = img.getHeight();
        int minCount = width;
        for (int y = height / 2 - 3; y < height / 2 + 3; ++y) {
            int count = 0;
            for (int x = 0; x < width / 2 + 1; ++x) {
                if (CommonUtil.isWhite(img.getRGB(x, y), whiteThreshold) == 0) {
                    count++;
                }
            }
            minCount = Math.min(count, minCount);
        }
        return minCount > 0;
    }

    public static boolean isNotFive(BufferedImage img) {
        final int width = img.getWidth();
        final int height = img.getHeight();
        int minCount = width;
        for (int y = 0; y < height / 3; ++y) {
            int count = 0;
            for (int x = width * 2 / 3; x < width; ++x) {
                if (CommonUtil.isWhite(img.getRGB(x, y), whiteThreshold) == 0) {
                    count++;
                }
            }
            minCount = Math.min(count, minCount);
        }
        return minCount > 0;
    }

    public static String getSingleCharOcr(BufferedImage img, Map<BufferedImage, String> map) throws Exception {
        if (useSvm) {
            final String input = new File("img/" + clazz + "/input.txt").getAbsolutePath();
            final String output = new File("result/" + clazz + "/output.txt").getAbsolutePath();
            CommonUtil.imgToSvmInput(img, input, whiteThreshold);
            svm_predict.main(new String[]{input, new File("train/" + clazz + "/data.txt.model").getAbsolutePath(), output});
            final List<String> predict = IOUtils.readLines(new FileInputStream(output));
            if (predict.size() > 0 && predict.get(0).length() > 0) {
                return predict.get(0).substring(0, 1);
            }
            return "#";
        }

        String result = "#";
        img = scaleImage(img);
        final int width = img.getWidth();
        final int height = img.getHeight();
        int min = width * height;
        final boolean bNotEight = isNotEight(img);
        final boolean bNotThree = isNotThree(img);
        final boolean bNotFive = isNotFive(img);
        for (final BufferedImage bi : map.keySet()) {
            if (bNotThree && map.get(bi).startsWith("3")) {
                continue;
            }
            if (bNotEight && map.get(bi).startsWith("8")) {
                continue;
            }
            if (bNotFive && map.get(bi).startsWith("5")) {
                continue;
            }
            final double count1 = getBlackCount(img);
            final double count2 = getBlackCount(bi);
            if (Math.abs(count1 - count2) / Math.max(count1, count2) > 0.25) {
                continue;
            }
            int count = 0;
            if (width < bi.getWidth() && height < bi.getHeight()) {
                for (int m = 0; m <= bi.getWidth() - width; m++) {
                    for (int n = 0; n <= bi.getHeight() - height; n++) {
                        Label1:
                        for (int x = m; x < m + width; ++x) {
                            for (int y = n; y < n + height; ++y) {
                                if (CommonUtil.isWhite(img.getRGB(x - m, y - n), whiteThreshold) != CommonUtil
                                    .isWhite(bi.getRGB(x, y), whiteThreshold)) {
                                    count++;
                                    if (count >= min) {
                                        break Label1;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                final int widthmin = width < bi.getWidth() ? width : bi.getWidth();
                final int heightmin = height < bi.getHeight() ? height : bi.getHeight();
                Label1:
                for (int x = 0; x < widthmin; ++x) {
                    for (int y = 0; y < heightmin; ++y) {
                        if (CommonUtil.isWhite(img.getRGB(x, y), whiteThreshold) != CommonUtil.isWhite(bi.getRGB(x, y),
                            whiteThreshold)) {
                            count++;
                            if (count >= min) {
                                break Label1;
                            }
                        }
                    }
                }
            }
            if (count < min) {
                min = count;
                result = map.get(bi);
            }
        }
        return result;
    }

    public static String getAllOcr(File file, String filename) throws Exception {
        final BufferedImage img = removeBackgroud(file);
        // BufferedImage img = ImageIO.read(file);
        /*
        // TODO
        FileOutputStream fos = new FileOutputStream("D:\\img.jpg");
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
        encoder.encode(img);
        fos.close();
        */

        final List<BufferedImage> listImg = splitImage(img, filename);
        final Map<BufferedImage, String> map = loadTrainData();
        String result = useSvm ? "svm_" : "";
        for (final BufferedImage bi : listImg) {
            result += getSingleCharOcr(bi, map);
        }
        System.out.println(result);
        ImageIO.write(img, "JPG", new File("result/" + clazz + "/" + result + ".jpg"));
        return result;
    }

    public static Map<BufferedImage, String> loadTrainData() throws Exception {
        if (trainMap == null) {
            final Map<BufferedImage, String> map = new HashMap<BufferedImage, String>();
            final File dir = new File("train/" + clazz);
            final File[] files = dir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".jpg");
                }
            });
            for (final File file : files) {
                map.put(scaleImage(ImageIO.read(file)), file.getName().charAt(0) + "");
            }
            trainMap = map;
        }
        return trainMap;
    }

    public static int getBlackCount(BufferedImage img) {
        final int width = img.getWidth();
        final int height = img.getHeight();
        int count = 0;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (CommonUtil.isWhite(img.getRGB(x, y), whiteThreshold) == 0) {
                    count++;
                }
            }
        }
        return count;
    }

    public static BufferedImage scaleImage(BufferedImage img) {
        final ScaleFilter sf = new ScaleFilter(16, 16);
        final BufferedImage imgdest = new BufferedImage(16, 16, img.getType());
        return sf.filter(img, imgdest);
    }

    public static void main(String[] args) throws Exception {
        // ---step1 downloadImage
        // String url = "http://reg.keepc.com/getcode/getCode.php";
        // 下载图片
        // CommonUtil.downloadImage(url, clazz);
        File file = new File("img/" + clazz);
        if(!file.exists()) {
            file.mkdirs();
        }
        new File("train/" + clazz).mkdirs();
        new File("result/" + clazz).mkdirs();

        File[] files = file.listFiles();
        // 先删除result/ocr目录，开始识别
        for (int i = 0; i < files.length; ++i) {
            final String text = getAllOcr(files[i], files[i].getName());
            System.out.println(i + ".jpg = " + text);
        }

//         CommonUtil.scaleTraindata(clazz, whiteThreshold);
//         svm_train train = new svm_train();
//         train.run(new String[] { new File("train/" + clazz + "/data.txt").getAbsolutePath(), new File("train/" + clazz + "/data.txt.model").getAbsolutePath() });
    }

}
