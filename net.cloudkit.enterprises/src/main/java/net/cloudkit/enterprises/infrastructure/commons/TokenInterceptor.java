/*
 * Copyright (C) 2015 The CloudKit Open Source Project
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
package net.cloudkit.enterprises.infrastructure.commons;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * TokenInterceptor.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年11月1日 下午4:16:21
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null) {
                boolean needSaveSession = annotation.save();
                if (needSaveSession) {
                    request.getSession(true).setAttribute("token", UUID.randomUUID().toString());
                }
                boolean needRemoveSession = annotation.remove();
                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                        return false;
                    }
                    request.getSession(false).removeAttribute("token");
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession(false).getAttribute("token");
        if (serverToken == null) {
            return true;
        }
        String clinetToken = request.getParameter("token");
        if (clinetToken == null) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }

    //    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
    //
    //        String tokenCookie = CookieHelper.getCookie(request, "token");
    //
    //        if (request.getMethod().equalsIgnoreCase("POST")) {
    //            String xRequestedWith = request.getHeader("X-Requested-With");
    //            if ((xRequestedWith != null) && (xRequestedWith.equalsIgnoreCase("XMLHttpRequest"))) {
    //                if ((tokenCookie != null) && (tokenCookie.equals(request.getHeader("token")))) {
    //                    return true;
    //                }
    //                response.addHeader("tokenStatus", "accessDenied");
    //            } else if ((tokenCookie != null) && (tokenCookie.equals(request.getParameter("token")))) {
    //                return true;
    //            }
    //            if (tokenCookie == null) {
    //                tokenCookie = UUID.randomUUID().toString();
    //                CookieHelper.addCookie(request, response, "token", tokenCookie);
    //            }
    //            response.sendError(403, "Bad or missing token!");
    //            return false;
    //        }
    //        if (tokenCookie == null) {
    //            tokenCookie = UUID.randomUUID().toString();
    //            CookieHelper.addCookie(request, response, "token", tokenCookie);
    //        }
    //        request.setAttribute("token", tokenCookie);
    //        return true;
    //    }
}
