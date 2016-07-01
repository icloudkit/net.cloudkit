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
package net.cloudkit.flowportal.infrastructure.commons;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ExecuteTimeInterceptor.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2014年1月8日 下午1:05:44
 */
public class ExecuteTimeInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ExecuteTimeInterceptor.class);

    private static final String START_TIME = ExecuteTimeInterceptor.class.getName() + ".START_TIME";
    public static final String EXECUTE_TIME_ATTRIBUTE_NAME = ExecuteTimeInterceptor.class.getName() + ".EXECUTE_TIME";
    private static final String redirect = "redirect:";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Long startTime = (Long) request.getAttribute(START_TIME);
        if (startTime == null) {
            startTime = Long.valueOf(System.currentTimeMillis());
            request.setAttribute(START_TIME, startTime);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        Long startTime = (Long) request.getAttribute(EXECUTE_TIME_ATTRIBUTE_NAME);
        Object localObject;
        if (startTime == null) {
            localObject = request.getAttribute(START_TIME);
            Long endTime = Long.valueOf(System.currentTimeMillis());
            startTime = Long.valueOf(endTime.longValue() - ((Long) localObject).longValue());
            request.setAttribute(START_TIME, localObject);
        }
        if (modelAndView != null) {
            localObject = modelAndView.getViewName();
            if (!StringUtils.startsWith((String) localObject, redirect)) {
                modelAndView.addObject(EXECUTE_TIME_ATTRIBUTE_NAME, startTime);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("[" + handler + "] executeTime: " + startTime + "ms");
        }
    }
}


////before the actual handler will be executed
//public boolean preHandle(HttpServletRequest request,
//                HttpServletResponse response, Object handler)
//    throws Exception {
//
//        long startTime = System.currentTimeMillis();
//        request.setAttribute("startTime", startTime);
//
//        return true;
//}
//
////after the handler is executed
//public void postHandle(
//                HttpServletRequest request, HttpServletResponse response,
//                Object handler, ModelAndView modelAndView)
//                throws Exception {
//
//        long startTime = (Long)request.getAttribute("startTime");
//
//        long endTime = System.currentTimeMillis();
//
//        long executeTime = endTime - startTime;
//
//        //modified the exisitng modelAndView
//        modelAndView.addObject("executeTime",executeTime);
//
//        logger.debug("[" + handler + "] executeTime : " + executeTime + "ms");
//}



//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//public class ExecuteTimeInterceptor extends HandlerInterceptorAdapter{
//
//    private static final Logger logger = Logger.getLogger(ExecuteTimeInterceptor.class);
//
//    //before the actual handler will be executed
//    public boolean preHandle(HttpServletRequest request,
//        HttpServletResponse response, Object handler)
//        throws Exception {
//
//        long startTime = System.currentTimeMillis();
//        request.setAttribute("startTime", startTime);
//
//        return true;
//    }
//
//    //after the handler is executed
//    public void postHandle(
//        HttpServletRequest request, HttpServletResponse response,
//        Object handler, ModelAndView modelAndView)
//        throws Exception {
//
//        long startTime = (Long)request.getAttribute("startTime");
//
//        long endTime = System.currentTimeMillis();
//
//        long executeTime = endTime - startTime;
//
//        //modified the exisitng modelAndView
//        modelAndView.addObject("executeTime",executeTime);
//
//        //log it
//        if(logger.isDebugEnabled()){
//           logger.debug("[" + handler + "] executeTime : " + executeTime + "ms");
//        }
//    }
//}
