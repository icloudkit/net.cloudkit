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
import net.cloudkit.relaxation.fonts.Fonts;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.ColorConvertOp;
import java.awt.image.LookupOp;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016-05-19 18:48
 */
public class VerifyImage {

    private static final int LEN = 5;
    int count;

    public VerifyImage() {
    }

    public void convBW(BufferedImage image) {
        byte[] threshold = new byte[256];

        for (int thresholdOp = 0; thresholdOp < 256; ++thresholdOp) {
            threshold[thresholdOp] = (byte) (thresholdOp < 128 ? 0 : -1);
        }

        LookupOp var4 = new LookupOp(new ByteLookupTable(0, threshold), (RenderingHints) null);
        var4.filter(image, image);
    }

    private static int getRed(int rgb) {
        return rgb >> 16 & 255;
    }

    private static int getGreen(int rgb) {
        return rgb >> 8 & 255;
    }

    private static int getBlue(int rgb) {
        return rgb & 255;
    }

    private void outToImage(BufferedImage image, String name) {
        try {
            ImageIO.write(image, "png", new File(name));
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    private void unknowToImage(BufferedImage image, String name) {
        try {
            ImageIO.write(image, "bmp", new File(name));
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public void clearNoise(BufferedImage image) {
        /*
        int w = image.getWidth();
        int h = image.getHeight();

        int p1;
        int p2;
        for (p1 = 0; p1 < w; ++p1) {
            for (p2 = 0; p2 < h; ++p2) {
                if (p1 == 0 || p2 == 0 || p1 == w - 1 || p2 == h - 1) {
                    image.setRGB(p1, p2, Color.WHITE.getRGB());
                }
            }
        }

        for (int x = 0; x < w; ++x) {
            for (int y = 0; y < h; ++y) {
                p1 = x == 0 ? 255 : getRed(image.getRGB(x - 1, y));
                p2 = x == w - 1 ? 255 : getRed(image.getRGB(x + 1, y));
                int p3 = y == 0 ? 255 : getRed(image.getRGB(x, y - 1));
                int p4 = y == h - 1 ? 255 : getRed(image.getRGB(x, y + 1));
                int p5 = x != 0 && y != 0 ? getRed(image.getRGB(x - 1, y - 1)) : 255;
                int p6 = x != w - 1 && y != 0 ? getRed(image.getRGB(x + 1, y - 1)) : 255;
                int p7 = x != 0 && y != h - 1 ? getRed(image.getRGB(x - 1, y + 1)) : 255;
                int p8 = x != w - 1 && y != h - 1 ? getRed(image.getRGB(x + 1, y + 1)) : 255;
                if (p1 == 255 && p1 == p2 && p2 == p3 && p3 == p4 && p4 == p5 && p5 == p6 && p7 == p8) {
                    image.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }
        */


        final int width = image.getWidth();
        final int height = image.getHeight();

        // int blackThreshold = 300;

        // img.getMinX() img.getMinY()
        for (int x = image.getMinX(); x < width; x++) {
            for (int y = image.getMinY(); y < height; y++) {

//                Color color = new Color(image.getRGB(x, y));
//                if((color.getBlue() < 120) || ((color.getRed() + color.getGreen() + color.getBlue()) < 50)) { //
//                    image.setRGB(x, y, Color.WHITE.getRGB());
//                } else if((color.getRed() + color.getGreen() + color.getBlue()) < 400) {
//                    image.setRGB(x, y, Color.BLACK.getRGB());
//                }

                int nearly = 0;
                int vertical = 0;
                int horizontal = 0;

                if(x > 0) {
                    Color leftColor = new Color(image.getRGB(x - 1, y));
                    if((leftColor.getRed() + leftColor.getGreen() + leftColor.getBlue()) < 400) {
                        nearly ++;
                        horizontal ++;
                    }
                }
                if(x < width - 1) {
                    Color rightColor = new Color(image.getRGB(x + 1, y));
                    if((rightColor.getRed() + rightColor.getGreen() + rightColor.getBlue()) < 400) {
                        nearly ++;
                        horizontal ++;
                    }
                }
                if(y > 0) {
                    Color topColor = new Color(image.getRGB(x, y - 1));
                    if((topColor.getRed() + topColor.getGreen() + topColor.getBlue()) < 400) {
                        nearly ++;
                        vertical ++;
                    }
                }
                if(y < height - 1) {
                    Color bottomColor = new Color(image.getRGB(x, y + 1));
                    if((bottomColor.getRed() + bottomColor.getGreen() + bottomColor.getBlue()) < 400) {
                        nearly ++;
                        vertical ++;
                    }
                }

                if(x > 0 && y > 0) {
                    Color leftTopColor = new Color(image.getRGB(x - 1, y - 1));
                    if((leftTopColor.getRed() + leftTopColor.getGreen() + leftTopColor.getBlue()) < 400) {
                        nearly ++;
                    }
                }
                if(x < width - 1 && y < height - 1) {
                    Color rightBottomColor = new Color(image.getRGB(x + 1, y + 1));
                    if((rightBottomColor.getRed() + rightBottomColor.getGreen() + rightBottomColor.getBlue()) < 400) {
                        nearly ++;
                    }
                }
                if(x < width - 1 && y > 0) {
                    Color rightTopColor = new Color(image.getRGB(x + 1, y - 1));
                    if((rightTopColor.getRed() + rightTopColor.getGreen() + rightTopColor.getBlue()) < 400) {
                        nearly ++;
                    }
                }
                if(x > 0 && y < height - 1) {
                    Color leftBottomColor = new Color(image.getRGB(x - 1, y + 1));
                    if((leftBottomColor.getRed() + leftBottomColor.getGreen() + leftBottomColor.getBlue()) < 400) {
                        nearly ++;
                    }
                }

                if(nearly < 2) {
                    image.setRGB(x, y, Color.WHITE.getRGB());
                }

            }
        }
    }

    public String getResult(Map<String, byte[][]> fontMods, BufferedImage src) {
        int w = src.getWidth();
        int h = src.getHeight();
        byte[][] p = imgToByteArray(src);
        ArrayList offsetXs = new ArrayList(9);
        offsetXs.add(Integer.valueOf(0));
        ArrayList offsetYs = new ArrayList(9);
        offsetYs.add(Integer.valueOf(0));
        if (this.sum(p[0]) < 3) {
            offsetXs.add(Integer.valueOf(1));
        }

        if (this.sum(p[w - 1]) < 3) {
            offsetXs.add(Integer.valueOf(-1));
        }

        int s1 = 0;
        int s2 = 0;

        for (int fontSets = 0; fontSets < w; ++fontSets) {
            s1 += p[fontSets][0];
            s2 += p[fontSets][h - 1];
        }

        if (s1 < 3) {
            offsetYs.add(Integer.valueOf(1));
        }

        if (s2 < 3) {
            offsetYs.add(Integer.valueOf(-1));
        }

        Set var29 = fontMods.entrySet();
        Map.Entry rs = null;
        int minDiff = -1;
        Iterator i$ = offsetXs.iterator();

        while (i$.hasNext()) {
            Integer osx = (Integer) i$.next();
            Iterator i$1 = offsetYs.iterator();

            label103:
            while (i$1.hasNext()) {
                Integer osy = (Integer) i$1.next();
                Iterator i$2 = var29.iterator();

                while (true) {
                    Map.Entry font;
                    int diff;
                    do {
                        int mw;
                        int mh;
                        do {
                            do {
                                if (!i$2.hasNext()) {
                                    continue label103;
                                }

                                font = (Map.Entry) i$2.next();
                                diff = 0;
                                mw = ((byte[][]) font.getValue()).length;
                                mh = ((byte[][]) font.getValue())[0].length;
                            } while (Math.abs(h + osy.intValue() - mh) > 3);
                        } while (Math.abs(w + osx.intValue() - mw) > 3);

                        int minH = Math.min(h + osy.intValue(), mh);
                        int minW = Math.min(w + osx.intValue(), mw);
                        byte[][] fontV = (byte[][]) font.getValue();

                        for (int yi = 0; yi < minH; ++yi) {
                            for (int xi = 0; xi < minW; ++xi) {
                                int tx = xi + osx.intValue();
                                int ty = yi + osy.intValue();
                                if (tx >= 0 && ty >= 0 && tx < w && ty < h && fontV[xi][yi] != p[tx][ty]) {
                                    ++diff;
                                }
                            }
                        }
                    } while (minDiff != -1 && minDiff <= diff);

                    minDiff = diff;
                    rs = font;
                }
            }
        }

        if (minDiff > 10) {
            System.out.println("未找到字典匹配字符：差异都过大：" + minDiff);
            return "";
        } else {
            return rs == null ? "" : ((String) rs.getKey()).substring(0, 1);
        }
    }

    public Point getFirstPoint(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        for (int x = 0; x < w; ++x) {
            for (int y = 0; y < h; ++y) {
                int rgb = getRed(image.getRGB(x, y));
                if (rgb != 255) {
                    return new Point(x, y);
                }
            }
        }

        return new Point(-1, -1);
    }

    public void getFont(BufferedImage image, int x, int y, BufferedImage draw, BufferedImage outDraw) {
        int w = image.getWidth();
        int h = image.getHeight();
        int c = getRed(image.getRGB(x, y));
        if (c != 255) {
            int c1 = x == 0 ? 255 : getRed(image.getRGB(x - 1, y));
            int c2 = x == w - 1 ? 255 : getRed(image.getRGB(x + 1, y));
            int c3 = y == 0 ? 255 : getRed(image.getRGB(x, y - 1));
            int c4 = y == h - 1 ? 255 : getRed(image.getRGB(x, y + 1));
            int c5 = x != 0 && y != 0 ? getRed(image.getRGB(x - 1, y - 1)) : 255;
            int c6 = x != w - 1 && y != 0 ? getRed(image.getRGB(x + 1, y - 1)) : 255;
            int c7 = x != 0 && y != h - 1 ? getRed(image.getRGB(x - 1, y + 1)) : 255;
            int c8 = x != w - 1 && y != h - 1 ? getRed(image.getRGB(x + 1, y + 1)) : 255;
            outDraw.setRGB(x, y, image.getRGB(x, y));
            draw.setRGB(x, y, Color.WHITE.getRGB());
            ++this.count;
            if (c1 != 255) {
                this.getFont(image, x - 1, y, draw, outDraw);
            }

            if (c2 != 255) {
                this.getFont(image, x + 1, y, draw, outDraw);
            }

            if (c3 != 255) {
                this.getFont(image, x, y - 1, draw, outDraw);
            }

            if (c4 != 255) {
                this.getFont(image, x, y + 1, draw, outDraw);
            }

            if (c5 != 255) {
                this.getFont(image, x - 1, y - 1, draw, outDraw);
            }

            if (c6 != 255) {
                this.getFont(image, x + 1, y - 1, draw, outDraw);
            }

            if (c7 != 255) {
                this.getFont(image, x - 1, y + 1, draw, outDraw);
            }

            if (c8 != 255) {
                this.getFont(image, x + 1, y + 1, draw, outDraw);
            }

        }
    }

    public BufferedImage autoCrop(BufferedImage image) {
        int iw = image.getWidth();
        int ih = image.getHeight();
        int x = iw;
        int y = ih;
        int x2 = 0;
        int y2 = 0;

        for (int i = 0; i < iw; ++i) {
            for (int j = 0; j < ih; ++j) {
                int rgb = getRed(image.getRGB(i, j));
                if (rgb != 255) {
                    x = Math.min(i, x);
                    y = Math.min(j, y);
                    x2 = Math.max(i, x2);
                    y2 = Math.max(j, y2);
                }
            }
        }

        return image.getSubimage(x, y, x2 - x + 1, y2 - y + 1);
    }

    private int sum(byte[] a) {
        int rs = 0;
        byte[] arr$ = a;
        int len$ = a.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            byte b = arr$[i$];
            rs += b;
        }

        return rs;
    }

    public static byte[][] imgToByteArray(BufferedImage src) {
        int w = src.getWidth();
        int h = src.getHeight();
        byte[][] result = new byte[w][h];

        for (int x = 0; x < w; ++x) {
            for (int y = 0; y < h; ++y) {
                int rgb = getRed(src.getRGB(x, y));
                result[x][y] = (byte) (rgb == 255 ? 0 : 1);
            }
        }

        return result;
    }

    private BufferedImage getWhiteImage(int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, 12);

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                bi.setRGB(x, y, Color.WHITE.getRGB());
            }
        }

        return bi;
    }

    private BufferedImage getMaxWidthImage(java.util.List<BufferedImage> images) {
        BufferedImage image = null;
        Iterator i$ = images.iterator();

        while (true) {
            BufferedImage bi;
            do {
                if (!i$.hasNext()) {
                    return image;
                }

                bi = (BufferedImage) i$.next();
            } while (image != null && image.getWidth() >= bi.getWidth());

            image = bi;
        }
    }

//    public BufferedImage[] splitImage(BufferedImage image) {
//        byte leftPad = 6;
//        ArrayList images = new ArrayList(4);
//        int w = image.getWidth();
//        int h = image.getHeight();
//        // BufferedImage draw = image.getSubimage(leftPad, 0, w - leftPad, h);
//
//        for (int i = 0; i < 4; ++i) {
//            BufferedImage outdraw = image.getSubimage(i * 8 + 6, 0, 8, h);
//            images.add(outdraw);
//        }
//
//        return (BufferedImage[]) images.toArray(new BufferedImage[0]);
//    }

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
        for (int i = 0; i < weightList.size(); i++) {
            int length = 0;
            while (i < weightList.size() && weightList.get(i) > 0) {
                i++;
                length++;
            }
            if (length > 18) {
                subImgs.add(removeBlank(img.getSubimage(i - length, 0, length / 2, height), whiteThreshold, 0));
                subImgs.add(removeBlank(img.getSubimage(i - length / 2, 0, length / 2, height), whiteThreshold, 0));
            } else if (length > 5) {
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
        Label1: for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                if (isWhite(img.getRGB(x, y), whiteThreshold) == white) {
                    start = y;
                    break Label1;
                }
            }
        }
        Label2: for (int y = height - 1; y >= 0; --y) {
            for (int x = 0; x < width; ++x) {
                if (isWhite(img.getRGB(x, y), whiteThreshold) == white) {
                    end = y;
                    break Label2;
                }
            }
        }
        return img.getSubimage(0, start, width, end - start + 1);
    }

    private BufferedImage grayImage(BufferedImage image) {
        ColorConvertOp filterObj = new ColorConvertOp(ColorSpace.getInstance(1003), (RenderingHints) null);
        filterObj.filter(image, image);
        return image;
    }

    public String getVerifyNumber(BufferedImage image, String name) {
        String result = "";
        image = this.grayImage(image);
        // this.convBW(image);
        this.clearNoise(image);
        // BufferedImage[] ims = new BufferedImage[0];
        List<BufferedImage> ims = new ArrayList<>();
        try {
            ims = this.splitImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for (int i = 0; i < ims.size(); i++) {
                // FileOutputStream fos = new FileOutputStream("D:\\images\\" + name + i + ".jpg");
                // JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
                // encoder.encode(ims.get(i));
                // fos.close();

                File file2 = new File("D:\\images\\" + name + i + ".gif");
                ImageIO.write(ims.get(i), "gif", file2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        if (ims.size() != 4) {
//            System.out.println("分离验证码失败");
//            return result;
//        } else {
//            BufferedImage[] arr$ = ims.toArray(new BufferedImage[0]);
//            int len$ = ims.size();
//
//            for (int i$ = 0; i$ < len$; ++i$) {
//                BufferedImage im = arr$[i$];
//                String r = this.getResult(Fonts.getInstance().getFonts(), im);
//                if (r != null && !r.isEmpty()) {
//                    result = result + r;
//                }
//            }
//
//            return result;
//        }
        return null;
    }

    public static void convert(File source, String formatName, String result) {
        try {
            // File f = new File(source);
            source.canRead();
            BufferedImage src = ImageIO.read(source);
            ImageIO.write(src, formatName, new File(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static InputStream downloadImage() throws IOException {
//
//        HttpClient httpClient = new HttpClient();
//        GetMethod getMethod = new GetMethod("http://www.szvsc.com/verifyCodeServlet?" + (new Date()).toGMTString());
//        int statusCode = httpClient.executeMethod(getMethod);
//        if(statusCode != 200) {
//            System.err.println("Method failed: " + getMethod.getStatusLine());
//        }
//
//        InputStream inputStream = getMethod.getResponseBodyAsStream();
//        System.out.println("Has Download.!");
//        return inputStream;
//    }

    public static void main(String[] args) throws Exception {
        // InputStream inputStream = downloadImage();

        /*
        InputStream inputStream = new FileInputStream("D:\\test4.jpg");
        VerifyImage vi = new VerifyImage();
        System.out.println(vi.getVerifyNumber(ImageIO.read(inputStream)));
        */

//        File fileDirectory = new File("D:\\customs\\");
//        File[] files = fileDirectory.isDirectory()?  fileDirectory.listFiles() : new File[0];
//        for (int i = 0; i < files.length; i++) {
//              // convert(files[i], "jpg", "D:\\customs\\" + File.separator + "jpg" + File.separator + files[i].getName().replace(".gif", ".jpg"));
//            InputStream inputStream = new FileInputStream(files[i]);
//            VerifyImage vi = new VerifyImage();
//            System.out.println(vi.getVerifyNumber(ImageIO.read(inputStream), files[i].getName()));
//        }

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
    }
}
