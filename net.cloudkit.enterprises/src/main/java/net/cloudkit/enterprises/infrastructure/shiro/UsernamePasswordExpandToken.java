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
package net.cloudkit.enterprises.infrastructure.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

import java.util.Map;

/**
 * CaptchaUsernamePasswordToken
 * UsernamePasswordExpandToken.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年10月28日 下午2:49:15
 */
public class UsernamePasswordExpandToken extends UsernamePasswordToken {

    private static final long serialVersionUID = 3467440250499959398L;

    // 验证码 用户输入的验证码需要与SESSION中的比对
    private String captcha;

    // 其它数据
    private Map<String, Object> otherDataMap;

    public UsernamePasswordExpandToken(String username, char[] password, boolean rememberMe, String host, String captcha, Map<String, Object> otherDataMap) {
        super(username, password, rememberMe, host);

        // 验证码
        this.captcha = captcha;
        this.otherDataMap = otherDataMap;
    }

    /**
     * @return the captcha
     */
    public String getCaptcha() {
        return captcha;
    }

    /**
     * @param captcha the captcha to set
     */
    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    /**
     * @return the otherDataMap
     */
    public Map<String, Object> getOtherDataMap() {
        return otherDataMap;
    }

    /**
     * @param otherDataMap the otherDataMap to set
     */
    public void setOtherDataMap(Map<String, Object> otherDataMap) {
        this.otherDataMap = otherDataMap;
    }

    @Override
    public String toString() {
        return "{captcha:" + captcha + "}";
    }

}
