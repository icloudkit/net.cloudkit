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

package net.cloudkit.relaxation;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;
import java.util.List;

/**
 * CaptchaTest.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016-05-24 11:13
 */
public class CaptchaTest {

    public static void clearNoise(BufferedImage image) {
        final int width = image.getWidth();
        final int height = image.getHeight();

        /*
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, Color.WHITE.getRGB());
            }
        }
        */
        for (int x = image.getMinX(); x < width; x++) {
            for (int y = image.getMinY(); y < height; y++) {

                int point = image.getRGB(x, y);

                // 上
                int top = (y > 0) ? image.getRGB(x, y - 1) : -1;
                // 右
                int right = (x < width - 1) ? image.getRGB(x + 1, y) : -1;
                // 下
                int bottom = (y < height - 1) ? image.getRGB(x, y + 1) : -1;
                // 左
                int left = (x > 0) ? image.getRGB(x - 1, y) : -1;
                // 上右
                int topRight = ((x < width - 1) && (y > 0)) ? image.getRGB(x + 1, y - 1) : -1;
                // 上左
                int topLeft = ((x > 0) && (y > 0)) ? image.getRGB(x - 1, y - 1) : -1;
                // 下右
                int bottomRight = ((x < width - 1) && (y < height - 1)) ? image.getRGB(x + 1, y + 1) : -1;
                // 下左
                int bottomLeft = ((x > 0) && (y < height - 1)) ? image.getRGB(x - 1, y + 1) : -1;

                int i = 0;
                i = (top != -1) ? i + 1 : i;
                i = (right != -1) ? i + 1 : i;
                i = (bottom != -1) ? i + 1 : i;
                i = (left != -1) ? i + 1 : i;

                i = (topRight != -1) ? i + 1 : i;
                i = (topLeft != -1) ? i + 1 : i;
                i = (bottomRight != -1) ? i + 1 : i;
                i = (bottomLeft != -1) ? i + 1 : i;

                // System.out.println("i:" + i + ", top:" + top + ", right:" + right + ", bottom:" + bottom + ", left:" + left + ", topRight:" + topRight + ", topLeft:" + topLeft + ", bottomRight:" + bottomRight + ", bottomLeft:" + bottomLeft);
                if (i < 5) {
                    image.setRGB(x, y, Color.WHITE.getRGB());
                }

                /*
                int leftNearby = 0;
                if(left != -1) {
                    int secondLeft = (x > 1) ? image.getRGB(x - 2, y) : -1;
                    int threeLeft = (x > 2) ? image.getRGB(x - 3, y) : -1;

                    leftNearby = ((left == -1) ? 0 : 1) + ((secondLeft == -1) ? 0 : 1) + ((threeLeft == -1) ? 0 : 1);
                }

                int rightNearby = 0;
                if(right != -1) {
                    int secondRight = (x + 1 < width - 1) ? image.getRGB(x + 2, y) : -1;
                    int threeRight = (x + 2 < width - 1) ? image.getRGB(x + 3, y) : -1;

                    rightNearby = ((right == -1) ? 0 : 1) + ((secondRight == -1) ? 0 : 1) + ((threeRight == -1) ? 0 : 1);
                }

                int topNearby = 0;
                if(top != -1) {
                    int secondTop = (y > 1) ? image.getRGB(x, y - 2) : -1;
                    int threeTop = (y > 2) ? image.getRGB(x, y - 3) : -1;

                    topNearby = ((top == -1) ? 0 : 1) + ((secondTop == -1) ? 0 : 1) + ((threeTop == -1) ? 0 : 1);
                }

                int bottomNearby = 0;
                if(bottom != -1) {
                    int secondBottom = (y + 1 < height - 1) ? image.getRGB(x, y + 2) : -1;
                    int threeBottom = (y + 2 < height - 1) ? image.getRGB(x, y + 3) : -1;

                    bottomNearby = ((bottom == -1) ? 0 : 1) + ((secondBottom == -1) ? 0 : 1) + ((threeBottom == -1) ? 0 : 0);
                }

                System.out.println(leftNearby + " " + rightNearby + " " + topNearby + " " + bottomNearby);
                if (leftNearby != 2 && rightNearby != 2 && topNearby != 2 && bottomNearby != 2) {
                    image.setRGB(x, y, Color.WHITE.getRGB());
                }
                */


                /*
                System.out.println(point - top);
                System.out.println(point - right);
                System.out.println(point - bottom);
                System.out.println(point - left);
                System.out.println(point - topRight);
                System.out.println(point - topLeft);
                System.out.println(point - bottomRight);
                System.out.println(point - bottomLeft);
                */

                // if (point != top && point != right && point != bottom && point != left && point != topRight && point != topLeft && point != bottomRight && point != bottomLeft) {
                //     image.setRGB(x, y, Color.WHITE.getRGB());
                // }

                /*
                Color color = new Color(image.getRGB(x, y));
                if((color.getBlue() < 120) || ((color.getRed() + color.getGreen() + color.getBlue()) < 50)) { //
                    image.setRGB(x, y, Color.WHITE.getRGB());
                } else if((color.getRed() + color.getGreen() + color.getBlue()) < 400) {
                    image.setRGB(x, y, Color.BLACK.getRGB());
                }
                */

                Color color = new Color(image.getRGB(x, y));
                if ((color.getRed() + color.getGreen() + color.getBlue()) < 600) {
                    image.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    image.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }

    }

    public static void clearBorder(BufferedImage image) {
        final int width = image.getWidth();
        final int height = image.getHeight();

        // image.getMinX() image.getMinY()
        // 去除上边框
        for (int x = 0; x < width; x++) {
            image.setRGB(x, 0, Color.WHITE.getRGB());
        }

        // 去除右边框
        for (int y = 0; y < height; y++) {
            image.setRGB(width - 1, y, Color.WHITE.getRGB());
        }

        // 去除下边框
        for (int x = 0; x < width; x++) {
            image.setRGB(x, height - 1, Color.WHITE.getRGB());
        }

        // 去除左边框
        for (int y = 0; y < height; y++) {
            image.setRGB(0, y, Color.WHITE.getRGB());
        }
    }

    public static void clearBackground(BufferedImage image) {

    }

    private static int whiteThreshold = 600;

    public static List<BufferedImage> splitImage(BufferedImage img) throws Exception {
        final List<BufferedImage> subImgs = new ArrayList<BufferedImage>();
        final int width = img.getWidth();
        final int height = img.getHeight();
        final List<Integer> weightList = new ArrayList<Integer>();
        for (int x = 0; x < width; ++x) {
            int count = 0;
            for (int y = 0; y < height; ++y) {
                if (isWhite(img.getRGB(x, y), whiteThreshold) == 0) {
                    count++;
                }
            }
            weightList.add(count);
        }
        // System.out.println(weightList.size());
        for (int i = 0; i < weightList.size(); i++) {
            int length = 0;
            while (i < weightList.size() && weightList.get(i) > 0) {
                i++;
                length++;
            }
            if (length > 18) {
                subImgs.add(removeBlank(img.getSubimage(i - length, 0, length / 2, height), whiteThreshold, 0));
                subImgs.add(removeBlank(img.getSubimage(i - length / 2, 0, length / 2, height), whiteThreshold, 0));
            } else if (length > 2) {
                subImgs.add(removeBlank(img.getSubimage(i - length, 0, length, height), whiteThreshold, 0));
            }
        }

        return subImgs;
    }

    public static int isWhite(int colorInt, int whiteThreshold) {
        final Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() > whiteThreshold) {
            return 1;
        }
        return 0;
    }

    public static BufferedImage removeBlank(BufferedImage img, int whiteThreshold, int white) throws Exception {
        final int width = img.getWidth();
        final int height = img.getHeight();
        int start = 0;
        int end = 0;
        Label1:
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                if (isWhite(img.getRGB(x, y), whiteThreshold) == white) {
                    start = y;
                    break Label1;
                }
            }
        }
        Label2:
        for (int y = height - 1; y >= 0; --y) {
            for (int x = 0; x < width; ++x) {
                if (isWhite(img.getRGB(x, y), whiteThreshold) == white) {
                    end = y;
                    break Label2;
                }
            }
        }
        return img.getSubimage(0, start, width, end - start + 1);
    }

    public static void main(String[] args) {

        // System.out.println(Color.WHITE.getRGB());

        try {
            File fileDirectory = new File("D:\\customs\\");
            File[] files = fileDirectory.isDirectory() ? fileDirectory.listFiles() : new File[0];
            for (int a = 0; a < files.length; a++) {
                if (files[a].isDirectory()) continue;
                InputStream inputStream = new FileInputStream(files[a]);
                BufferedImage bi = ImageIO.read(inputStream);
                clearBorder(bi);
                clearNoise(bi);
                // List<BufferedImage> subImgs = splitImage(bi);

                /*
                for (int i = 0; i < subImgs.size(); i++) {
                    File imageFile = new File("D:\\images\\" + i + ".gif");
                    ImageIO.write(subImgs.get(i), "gif", imageFile);
                }
                */

                FileOutputStream fos = new FileOutputStream("D:\\images\\" + files[a].getName().substring(0, files[a].getName().indexOf(".")) + a + ".jpg");
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
                encoder.encode(bi);
                fos.close();

                // File file2 = new File("D:\\images\\" + a + "_" + files[a].getName());
                // ImageIO.write(bi, "gif", file2);
            }

            /*
            CloseableHttpClient httpclient = HttpClients.createDefault();
            for (int i = 0; i < 1000; i++) {
                HttpGet httpGet = new HttpGet("http://query.customs.gov.cn/MNFTQ/Image.aspx?" + Math.random() * 1000);
                CloseableHttpResponse response1 = httpclient.execute(httpGet);
                // The underlying HTTP connection is still held by the response object
                // to allow the response content to be streamed directly from the network socket.
                // In order to ensure correct deallocation of system resources
                // the user MUST call CloseableHttpResponse#close() from a finally clause.
                // Please note that if response content is not fully consumed the underlying
                // connection cannot be safely re-used and will be shut down and discarded
                // by the connection manager.
                try {
                    System.out.println(response1.getStatusLine());
                    HttpEntity entity1 = response1.getEntity();
                    // do something useful with the response body
                    // and ensure it is fully consumed

                    InputStream input = entity1.getContent();
                    File storeFile = new File("D:\\customs\\customs"+ i +".jpg");
                    FileOutputStream output = new FileOutputStream(storeFile);
                    IOUtils.copy(input, output);
                    output.close();
                    EntityUtils.consume(entity1);
                } finally {
                    response1.close();
                }
            }
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
