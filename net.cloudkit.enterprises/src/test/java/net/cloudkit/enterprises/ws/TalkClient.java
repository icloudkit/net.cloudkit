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

package net.cloudkit.enterprises.ws;

import java.io.*;
import java.net.Socket;

/**
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016/1/28 12:49
 */
public class TalkClient {

    public static final String IP_ADDR = "192.168.1.16";//服务器地址
    public static final int PORT = 8885;//服务器端口号

    public static String getInstallId(String args) {
        String installId = "";
        Socket socket = null;
        try {
            // 创建一个流套接字并将其连接到指定主机上的指定端口号
            socket = new Socket(IP_ADDR, PORT);

            // 向服务器端发送数据
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.write(args.getBytes());

            // 读取服务器端数据
            DataInputStream input = new DataInputStream(socket.getInputStream());
            byte[] bytes = new byte[1024];
            input.read(bytes);
            // System.out.println("服务器端返回过来的是: " + new String(bytes, "UTF-8").substring(0, 44));
            installId = new String(bytes, "UTF-8").substring(0, 64);
            out.close();
            input.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("客户端异常:" + e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    socket = null;
                    System.out.println("客户端 finally 异常:" + e.getMessage());
                }
            }
        }
        return installId;
    }
}
