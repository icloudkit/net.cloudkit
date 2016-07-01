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

package net.cloudkit.enterprises;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015/12/3 15:47
 */
public class Deconde {
    public static void main(String[] args) throws Exception {
        // change("D:/test.txt");
        FileInputStream fis = new FileInputStream("D:/test.txt");      //文件字节流
        InputStreamReader isr = new InputStreamReader(fis, "GBK"); //字节流和字符流的桥梁，可以指定指定字符格式
        String str = null;

//        //直接读取字符，只要编码问题没问题
//        int c = 0;
//        while ((c = isr.read()) != -1)
//            System.out.print((char) c);

        //将InputStreamReader 封装到缓冲流中，需要字符编码正确

        BufferedReader br = new BufferedReader(isr);
        str = br.readLine();
        while (str != null) {
            // System.out.println(str.getBytes().length);
            // System.out.println(new String(str.getBytes(), "UTF-8"));
            // byte[] temp = new byte[30];
            // System.arraycopy(str.getBytes(), 16, temp, 0, 30);
            // System.out.println(new String(temp, "UTF-8"));
            // System.out.println(new String(str.getBytes(), 16, 36));

            Pattern pattern = Pattern.compile("^(.*{10})(.*{6})(.*{30})(.*{5})(.*{5})(.*{12})$");
            Matcher matcher = pattern.matcher(str);
            while(matcher.find()) {
                System.out.println(matcher.group(1));
            }

            str = br.readLine();
        }
//
//        //使用默认编码的InputStreamReader ，当为ANSI的时候没问题，但是读取UTF-8的时候出错。
//        BufferedReader br2 = new BufferedReader(new InputStreamReader(fis));
//        str = br2.readLine();
//        while (str != null) {
//            System.out.println(str);
//            str = br2.readLine();
//        }

    }

    public static void change(String filepath) throws UnsupportedEncodingException, IOException {
        BufferedReader buf = null;
        OutputStreamWriter pw = null;
        String str = null;
        String allstr = "";

        //用于输入换行符的字节码
        byte[] c = new byte[2];
        c[0] = 0x0d;
        c[1] = 0x0a;
        String t = new String(c);

        buf = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "GBK"));
        while ((str = buf.readLine()) != null) {
            allstr = allstr + str + t;
        }

        buf.close();

        pw = new OutputStreamWriter(new FileOutputStream(filepath), "UTF-8");
        pw.write(allstr);
        pw.close();
    }

}
