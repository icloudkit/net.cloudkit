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

package net.cloudkit.enterprises.infrastructure.shiro;

import org.apache.shiro.aop.MethodInterceptor;
import org.apache.shiro.aop.MethodInvocation;

import java.rmi.server.RemoteServer;
import java.util.Set;

/**
 * SecurityInterceptor.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年10月30日 上午11:56:14
 */
public class SecurityInterceptor implements MethodInterceptor {

    private Set allowed;

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        String clienthost = RemoteServer.getClientHost();
        // InetAddress inetAddress = java.net.InetAddress.getByName(clienthost);
        // String clentIp = inetAddress.getHostAddress();
        if (allowed != null && allowed.contains(clienthost)) {
            return methodInvocation.proceed();
        } else {
            throw new SecurityException("非法访问。");
        }
    }

    public void setAllowed(Set allowed) {
        this.allowed = allowed;
    }
}
