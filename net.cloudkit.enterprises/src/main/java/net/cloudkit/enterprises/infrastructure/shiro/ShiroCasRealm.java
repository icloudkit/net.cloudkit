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

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ShiroCasRealm.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年10月30日 上午11:56:14
 */
public class ShiroCasRealm extends CasRealm {

    private static final Logger logger = LoggerFactory.getLogger(ShiroCasRealm.class);

    // 获取授权信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        logger.info("doGetAuthorizationInfo...");
        // ... 与前面 ShiroDbRealm 相同

        return null;
    }

    @Override
    public String getCasServerUrlPrefix() {
        return "http://casserver/login";
    }

    @Override
    public String getCasService() {
        return "http://casclient/shiro-cas";
    }

    /*
     // 获取当前JSESSIONID （不管是从主域还是二级域访问产生）
     String JSESSIONID = request.getSession().getId();

     Cookie cookie = new Cookie("JSESSIONID", JSESSIONID);
     // 将cookie设成主域名访问，确保不同域之间都能获取到该cookie的值，从而确保session统一
     cookie.setDomain(".test.com");
     // 将cookie返回到客户端
     response.addCookie(cookie);
     request.getRequestDispatcher("indes.jsp").forward(request, response);
     */

}
