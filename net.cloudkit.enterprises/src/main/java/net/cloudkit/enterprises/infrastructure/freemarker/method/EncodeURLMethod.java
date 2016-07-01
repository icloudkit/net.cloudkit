package net.cloudkit.enterprises.infrastructure.freemarker.method;///*
//* Copyright (C) 2015 The CloudKit Open Source Project
//*
//* Licensed under the Apache License, Version 2.0 (the "License");
//* you may not use this file except in compliance with the License.
//* You may obtain a copy of the License at
//*
//*      http://www.apache.org/licenses/LICENSE-2.0
//*
//* Unless required by applicable law or agreed to in writing, software
//* distributed under the License is distributed on an "AS IS" BASIS,
//* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//* See the License for the specific language governing permissions and
//* limitations under the License.
//*/
//package net.cloudkit.workbench.infrastructure.freemarker.method;
//
//import java.utils.List;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Component;
//
//import freemarker.template.TemplateMethodModelEx;
//import freemarker.template.TemplateModelException;
//
///**
//* Freemarker自定义方法 实现response.encodeURL(url)功能
//*
//* 输出模版数据模型时，将EncodeURLMethod的实例对象放入模型中： dataMap.put("encodeURL", new EncodeURLMethod(context.getResponse()));
//*
//* 在Freemarker模版文件中这样使用： ${encodeURL("/news/show/?id=123456")}
//*/
//@Component("encodeURLMethod")
//public class EncodeURLMethod implements TemplateMethodModelEx {
//
//    private HttpServletResponse response;
//
//    /**
//     * 带参数的构造函数
//     *
//     * @param response HttpServletResponse对象
//     */
//    public EncodeURLMethod(HttpServletResponse response) {
//        this.response = response;
//    }
//
//    /**
//     * 执行方法
//     *
//     * @param argList 方法参数列表
//     * @return Object 方法返回值
//     * @throws TemplateModelException
//     */
//    @SuppressWarnings("rawtypes")
//    public Object exec(List argList) throws TemplateModelException {
//        // 限定方法中必须且只能传递一个参数
//        if (argList.size() != 1) {
//            throw new TemplateModelException("Wrong arguments!");
//        }
//        // 返回response.encodeURL执行结果
//        return response.encodeURL((String) argList.get(0));
//    }
//}
