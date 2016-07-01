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
package net.cloudkit.enterprises.interfaces;

import org.springframework.stereotype.Service;

import javax.jws.WebService;
import javax.xml.ws.Holder;

/**
 * ServiceProviderFacadeImpl.java
 *  extends SpringBeanAutowiringSupport
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2015年08月26日 上午11:38:34
 */
//Service Implementation Bean
@Service("serviceProviderFacade")
@WebService(endpointInterface = "net.cloudkit.enterprises.interfaces.ServiceProviderFacade", targetNamespace = "http://www.cloudkit.net/jax-ws/")
public class ServiceProviderFacadeImpl implements ServiceProviderFacade {

    @Override
    public byte[] service(String serviceName, String contentType, byte[] requestContext, byte[] requestData, Holder<byte[]> responseData) {

        // 服务名称 (包名.类名.方法名)
        System.out.println("serviceName:" + serviceName);



        // 请求上下文
        System.out.println("requestContext:" + requestContext);

        // TODO 根据服务名称执行相应服务,调用可以是本地调用或通过RMI调用分布式服务端
        // 响应数据
        responseData.value = "responseData".getBytes();
        // 响应上下文
        byte[] responseContext = ("responseContext").getBytes();

        return  responseContext;
    }
}
