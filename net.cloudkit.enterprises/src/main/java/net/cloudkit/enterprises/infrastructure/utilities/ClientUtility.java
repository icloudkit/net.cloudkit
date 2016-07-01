/*
 * Copyright (C) 2015 The Workbench Open Source Project
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
package net.cloudkit.enterprises.infrastructure.utilities;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * ClientUtility.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年10月31日 上午9:16:49
 */
public class ClientUtility {

    public static String getRemoteAddr(ServletRequest request) {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getHeader("x-forwarded-for") == null) {
            return httpRequest.getRemoteAddr();
        }
        return httpRequest.getHeader("x-forwarded-for");
    }

    // public String getRemoteAddr(HttpServletRequest request) {
    //
    //     String ip = request.getHeader("x-forwarded-for");
    //     if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    //         ip = request.getHeader("Proxy-Client-IP");
    //     }
    //     if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    //         ip = request.getHeader("WL-Proxy-Client-IP");
    //     }
    //     if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    //         ip = request.getRemoteAddr();
    //     }
    //     return ip;
    // }

    /**
     * IP long to String
     *
     * @param ipAddress
     * @return
     */
    public static String longToIp(long ipAddress) {
        StringBuffer sb = new StringBuffer("");
        sb.append(String.valueOf((ipAddress >>> 24)));
        sb.append(".");
        sb.append(String.valueOf((ipAddress & 0x00FFFFFF) >>> 16));
        sb.append(".");
        sb.append(String.valueOf((ipAddress & 0x0000FFFF) >>> 8));
        sb.append(".");
        sb.append(String.valueOf((ipAddress & 0x000000FF)));
        return sb.toString();
    }

    /**
     * String ip to long
     *
     * @param ipAddress
     * @return
     */
    public static long ipToLong(String ipAddress) {
        long[] ip = new long[4];
        int position1 = ipAddress.indexOf(".");
        int position2 = ipAddress.indexOf(".", position1 + 1);
        int position3 = ipAddress.indexOf(".", position2 + 1);
        ip[0] = Long.parseLong(ipAddress.substring(0, position1));
        ip[1] = Long.parseLong(ipAddress.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(ipAddress.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(ipAddress.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    public static void main(String[] args) {
        System.out.println(ipToLong("127.0.0.1"));
    }

}
