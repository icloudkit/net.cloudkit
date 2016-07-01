///*
// * Copyright (C) 2015 The CloudKit Open Source Project
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
//package net.cloudkit.middlement.enterprise.infrastructure.utilities;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.utils.Assert;
//
///**
// * Cookie 帮助类
// *
// * @author hongquanli <hongquanli@qq.com>
// * @version 1.0 2014年1月8日 下午3:36:10
// */
//public class CookieHelper {
//
//    /**
//     * Add Cookie
//     *
//     * @param request
//     * @param response
//     * @param name
//     * @param value
//     * @param maxAge
//     * @param path
//     * @param domain
//     * @param secure
//     */
//    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer maxAge, String path, String domain, Boolean secure) {
//        // Assert.notNull(request);
//        // Assert.notNull(response);
//       //  Assert.hasText(name);
//        try {
//            name = URLEncoder.encode(name, "UTF-8");
//            value = URLEncoder.encode(value, "UTF-8");
//            Cookie cookie = new Cookie(name, value);
//            if (maxAge != null) {
//                cookie.setMaxAge(maxAge.intValue());
//            }
//            if (StringUtils.isNotEmpty(path)) {
//                cookie.setPath(path);
//            }
//            if (StringUtils.isNotEmpty(domain)) {
//                cookie.setDomain(domain);
//            }
//            if (secure != null) {
//                cookie.setSecure(secure.booleanValue());
//            }
//            response.addCookie(cookie);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Add Cookie
//     *
//     * @param request
//     * @param response
//     * @param name
//     * @param value
//     * @param maxAge
//     */
//    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer maxAge) {
//        // TODO Setting
//        Setting setting = null;
//        addCookie(request, response, name, value, maxAge, setting.getCookiePath(), setting.getCookieDomain(), null);
//    }
//
//    /**
//     * Add Cookie
//     *
//     * @param request
//     * @param response
//     * @param name
//     * @param value
//     */
//    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
//        // TODO Setting
//        Setting setting = null;
//        addCookie(request, response, name, value, null, setting.getCookiePath(), setting.getCookieDomain(), null);
//    }
//
//    /**
//     * Get Cookie
//     *
//     * @param request
//     * @param name
//     * @return
//     */
//    public static String getCookie(HttpServletRequest request, String name) {
//        Assert.notNull(request);
//        Assert.hasText(name);
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            try {
//                name = URLEncoder.encode(name, "UTF-8");
//                for (Cookie cookie : cookies) {
//                    if (name.equals(cookie.getName())) {
//                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
//                    }
//                }
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    /**
//     * Remove Cookie
//     *
//     * @param request
//     * @param response
//     * @param name
//     * @param path
//     * @param domain
//     */
//    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name, String path, String domain) {
//        //  Assert.notNull(request);
//        //  Assert.notNull(response);
//        //  Assert.hasText(name);
//        try {
//            name = URLEncoder.encode(name, "UTF-8");
//            Cookie cookie = new Cookie(name, null);
//            cookie.setMaxAge(0);
//            if (StringUtils.isNotEmpty(path)) {
//                cookie.setPath(path);
//            }
//            if (StringUtils.isNotEmpty(domain)) {
//                cookie.setDomain(domain);
//            }
//            response.addCookie(cookie);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Remove Cookie
//     *
//     * @param request
//     * @param response
//     * @param name
//     */
//    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
//        // TODO Setting
//        Setting setting = null;
//        removeCookie(request, response, name, setting.getCookiePath(), setting.getCookieDomain());
//    }
//}
