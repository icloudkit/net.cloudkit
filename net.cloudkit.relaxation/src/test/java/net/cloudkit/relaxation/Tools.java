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

package net.cloudkit.relaxation;

public class Tools {

    public static final int VALUE = 0x80040154;


    public static void main(String[] args) {
        String bString = "1010101111001101";
        System.out.println(binary2hex(bString));

        String hexString = "ABCD";
        System.out.println(hex2binary(hexString));

        // 十进制转化为十六进制，结果为C8。
        System.out.println(Integer.toHexString(200));

        // 十六进制转化为十进制，结果140。
        System.out.println(Integer.parseInt("8C", 16));

        // 小写十六进制（等价于0x002f）
        int a = 0x2f;
        System.out.println(Integer.toBinaryString(a));

        // 大写十六进制
        int b = 0x2F;
        System.out.println(Integer.toBinaryString(b));

        // 标准十进制
        int c = 10;
        System.out.println(Integer.toBinaryString(c));

        // 以零开头，表示八进制
        int d = 010;
        System.out.println(Integer.toBinaryString(d));

        // char为2个字节，16位
        char e = 0xff;
        // byte为8位
        byte f = 0xf;
        // short为2个字节，16位
        short g = 0xff;
        System.out.println(Integer.toBinaryString(e));
        System.out.println(Integer.toBinaryString(f));
        System.out.println(Integer.toBinaryString(g));
    }

    public static String binary2hex(String s) {
        if (s == null || s.equals("") || s.length() % 8 != 0)
            return null;
        StringBuffer stringBuffer = new StringBuffer();
        int i1 = 0;
        for (int i = 0; i < s.length(); i += 4) {
            i1 = 0;
            for (int j = 0; j < 4; j++) {
                i1 += Integer.parseInt(s.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            stringBuffer.append(Integer.toHexString(i1));
        }
        return stringBuffer.toString();
    }

    public static String hex2binary(String s) {
        if (s == null || s.length() % 2 != 0)
            return null;
        String s1 = "", tmp;
        for (int i = 0; i < s.length(); i++) {
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(s
                    .substring(i, i + 1), 16));
            s1 += tmp.substring(tmp.length() - 4);
        }
        return s1;
    }

}
