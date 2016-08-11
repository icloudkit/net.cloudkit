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

package net.cloudkit.enterprises.ws;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Holder;
import javax.xml.ws.Service;
import java.net.URL;

/**
 * 伪造 license认证通过 Web Services
 */
public class SuperPassTest {

	public static void main(String[] args) throws Exception {

		 // Endpoint是发布提供类
		 Endpoint endpoint = Endpoint.publish("http://127.0.0.1:80/SuperPass/SuperPass", new SuperPassImpl());
		 System.out.println("服务已经启动 http://127.0.0.1:80/SuperPass/SuperPass");

		 // 1.Create a web service (of course).
		 // 2.Create a sun-jaxws.xml, defines web service implementation class.
		 // 3.Create a standard web.xml, defines WSServletContextListener, WSServlet and structure of a web project.
		 // 4.Build tool to generate WAR file.
		 // 5.Copy JAX-WS dependencies to “${Tomcat}/lib” folder.
		 // jaxb-impl.jar
		 // jaxws-api.jar
		 // jaxws-rt.jar
		 // gmbal-api-only.jar
		 // management-api.jar`CRITERION_DECLARE`
		 // stax-ex.jar
		 // streambuffer.jar
		 // policy.jar
		 // 6.Copy WAR to “${Tomcat}/webapp” folder.
		 // 7.Start It.


		// 调用测试
		URL url = new URL("http://127.0.0.1:80/SuperPass/SuperPass?wsdl");
		QName qname = new QName("http://pas.chinaport.gov.cn/", "SuperPassImplService");
		Service service = Service.create(url, qname);
		SuperPass superPass = service.getPort(SuperPass.class);

		String serviceName = "eport.superpass.pas.Random";
		byte[] requestContext = "test".getBytes();
		byte[] requestData = "test".getBytes();
		Holder<byte[]> responseData = new Holder<byte[]>("test".getBytes());
		// byte[] responseData = "test".getBytes();
		System.out.println(new String(superPass.service(serviceName, requestContext, requestData, responseData)));


	}
}
