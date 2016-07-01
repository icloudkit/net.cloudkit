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
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//
//import com.lowagie.text.Image;
//import com.lowagie.text.pdf.PdfContentByte;
//import com.lowagie.text.pdf.PdfReader;
//import com.lowagie.text.pdf.PdfStamper;
//
//public class PdfWaterMarkUtil {
//
//    public static void main(String[] args) {
//        try {
//            File file = new File("d://5s.pdf");
//            OutputStream out = new FileOutputStream(file);
//            new PdfWaterMarkUtil().PdfWaterMarkUtil("d://55.pdf", "d://3a.jpg",out,100,100);
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 给pdf文件添加水印
//     *
//     * @param InPdfFile 要加水印的原pdf文件路径
//     * @param outPdfFile 加了水印后要输出的路径
//     * @param markImagePath 水印图片路径
//     * @param pageSize 原pdf文件的总页数（该方法是我当初将数据导入excel中然后再转换成pdf所以我这里的值是用excel的行数计算出来的，
//     *                 如果不是我这种可以 直接用reader.getNumberOfPages()获取pdf的总页数）
//     * @throws Exception
//     */
//    public int PdfWaterMarkUtil(String InPdfFile, String markImagePath,OutputStream out,int deviationX,int deviationY) {
//        int result = -1;
//        try {
//            PdfReader reader = new PdfReader(InPdfFile, "PDF".getBytes());
//            int pageSize = reader.getNumberOfPages();
//
//            PdfStamper stamp = new PdfStamper(reader, out);
//
//            // 插入水印
//            Image img = Image.getInstance(markImagePath);
//
//            img.setAbsolutePosition(deviationX,deviationY);
//
//            for (int i = 1; i <= pageSize; i++) {
//
//                PdfContentByte under = stamp.getUnderContent(i);
//
//                under.addImage(img);
//            }
//
//            // 关闭
//            stamp.close();
//			/*
//			 * File tempfile = new File(InPdfFile);
//			 * if(tempfile.exists()) {
//			 *     tempfile.delete();
//			 * }
//			 */
//            result = 1;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                out.close();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return result;
//    }
//
//}
