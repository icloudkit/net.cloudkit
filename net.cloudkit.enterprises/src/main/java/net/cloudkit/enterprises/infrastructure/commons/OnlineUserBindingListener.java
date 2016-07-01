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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.ArrayList;
import java.util.List;

/**
 * OnlineUserBindingListener.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年11月6日 下午2:10:20
 */
public class OnlineUserBindingListener implements HttpSessionBindingListener {

    private String username;

    public OnlineUserBindingListener(String username) {
        this.username = username;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void valueBound(HttpSessionBindingEvent event) {

        HttpSession session = event.getSession();
        ServletContext application = session.getServletContext();

        // 把用户名放入在线列表
        List<String> onlineUserList = (List<String>) application.getAttribute("onlineUserList");

        // 第一次使用前，需要初始化
        if (onlineUserList == null) {
            onlineUserList = new ArrayList<String>();
            application.setAttribute("onlineUserList", onlineUserList);
        }
        if(!onlineUserList.contains(this.username)){
            onlineUserList.add(this.username);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();

        ServletContext application = session.getServletContext();

        // 从在线列表中删除用户名
        List<String> onlineUserList = (List<String>) application.getAttribute("onlineUserList");

        onlineUserList.remove(this.username);
        // System.out.println(this.username + "退出。");
    }
}
