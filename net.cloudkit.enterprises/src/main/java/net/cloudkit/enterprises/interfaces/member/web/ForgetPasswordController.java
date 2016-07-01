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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ForgetPasswordController.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015年05月13日 上午11:38:34
 */
@Controller("/forget_password")
public class ForgetPasswordController {

    private static final Logger logger = LoggerFactory.getLogger(ForgetPasswordController.class);

    /**
     * 根据发送邮件的链接设置密码。
     *
     * @param token
     * @return
     */
    @RequestMapping(value = {"/set_password_for_email"}, method = RequestMethod.GET)
    public String setPasswordForEmail(@RequestParam("token") String token) {
        try {
            // TODO 根据邮箱，IP查询token是否有效

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "/error";
        }
        return "/set_password_for_email";
    }

    /**
     * 根据发送短信验证码设置密码。
     *
     * @param token
     * @param model
     * @return
     */
    @RequestMapping(value = {"/set_password_for_mobie"}, method = RequestMethod.POST)
    public Model setPasswordForMobie(@RequestParam("token") String token, Model model) {

        model.addAttribute("success", false);
        try {
            // TODO 根据手机号，IP查询token是否有效
            model.addAttribute("success", true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return model;
    }

    /**
     * 重置密码。
     *
     * @param password
     * @param model
     * @return
     */
    @RequestMapping(value = {"/rest_password"}, method = RequestMethod.POST)
    @ResponseBody
    public Model restPassword(@RequestParam("token ") String token, @RequestParam("password") String password, Model model) {

        model.addAttribute("success", false);
        try {
            // TODO 根据用户名，IP查询token是否有效
            // TODO 重置密码
            // TODO 将token过期
            model.addAttribute("success", true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return model;
    }


}
