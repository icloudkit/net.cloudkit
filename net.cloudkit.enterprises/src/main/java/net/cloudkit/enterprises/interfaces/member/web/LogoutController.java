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
package net.cloudkit.enterprises.interfaces.member.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * LogoutController.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015年05月13日 上午11:38:34
 */
@Controller
public class LogoutController {

    private static final Logger logger = LoggerFactory.getLogger(LogoutController.class);

    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest request) {

        ModelAndView mv = new ModelAndView();

        // HttpServletRequest.getSession(boolean create)意思是返回当前reqeust中的HttpSession ，如果当前reqeust中的HttpSession 为null，当create为true，就创建一个新的Session，否则返回null；
        // HttpServletRequest.getSession(true) 等同于 HttpServletRequest.getSession()
        // HttpServletRequest.getSession(false) 等同于 如果当前Session没有就为null；
        // WebUtils.getSessionAttribute(request, "");
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }

        // HttpSession session = request.getSession();
        session = request.getSession();
        session.setAttribute("username", "coffee");
        logger.info("Save session!");

        // mv.setViewName("redirect:/");
        mv.setViewName(InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/signin.html");
        return mv;
    }
}
