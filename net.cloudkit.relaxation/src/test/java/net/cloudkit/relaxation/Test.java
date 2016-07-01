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

import java.io.*;

/**
 * Test.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015/11/14 10:45:45
 */
public class Test {

    public static void main(String[] args) {
        System.out.printf(String.valueOf("123456789".charAt(8)));

        // InputStreamReader isr = new InputStreamReader(new BufferedInputStream(""));
        // BufferedInputStream bis = new BufferedInputStream();

        ByteArrayInputStream bais = new ByteArrayInputStream("".getBytes());
        // FileInputStream PipedInputStream BufferedInputStream ObjectInputStream
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // outStream.toByteArray()

        // FileOutputStream fos = new FileOutputStream("D:/test.txt");
    }
}
