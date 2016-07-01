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

package net.cloudkit.enterprises;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016/1/6 9:57
 */
public class FileLockTest {

    public static void main(String[] args) {

        try {
            /*
            RandomAccessFile raf = new RandomAccessFile("file.txt", "rw");
            FileChannel fc = raf.getChannel();
            FileLock fl = fc.tryLock();
            if(fl.isValid()) {
                System.out.println("You have got the file lock.");
            }
            */

            // RandomAccessFile不支持只写模式，因为把参数设为“w”是非法的
            FileChannel fc = new RandomAccessFile("D:\\file.lock", "rw").getChannel();

            // FileOutputStream fos = new FileOutputStream("D:\\file.lock");
            // 获取FileChannel对象
            // FileChannel fc = fos.getChannel();

            // or fc.lock();
            FileLock fl = fc.tryLock();
            if (null != fl)
                System.out.println("You have got file lock.");
            // TODO write content to file
            // TODO write end, should release this lock
            while (true) {
                fc.position(0);
                fc.write(ByteBuffer.wrap("lock".getBytes()));
            }
            // 释放文件锁  注意：释放锁要在文件写操作之前，否则会出异常
            // fl.release();
            // 关闭文件写操作
            //fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
