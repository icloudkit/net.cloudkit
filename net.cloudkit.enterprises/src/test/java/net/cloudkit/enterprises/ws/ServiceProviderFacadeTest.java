package net.cloudkit.enterprises.ws;

import net.cloudkit.enterprises.interfaces.ServiceProviderFacade;
import net.cloudkit.enterprises.interfaces.ServiceProviderFacadeImpl;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Holder;
import javax.xml.ws.Service;
import java.net.URL;

/**
 * JAX-WS
 */
public class ServiceProviderFacadeTest {

    public static void main(String[] args) throws Exception {

        // Endpoint是发布提供类
        Endpoint endpoint = Endpoint.publish("http://127.0.0.1:80/interfaces/ServiceProviderFacade", new ServiceProviderFacadeImpl());
        System.out.println("服务已经启动 http://127.0.0.1:80/interfaces/ServiceProviderFacade");

        // 1.Create a web service (of course).
        // 2.Create a sun-jaxws.xml, defines web service implementation class.
        // 3.Create a standard web.xml, defines WSServletContextListener, WSServlet and structure of a web project.
        // 4.Build tool to generate WAR file.
        // 5.Copy JAX-WS dependencies to “${Tomcat}/lib” folder.
        // jaxb-impl.jar
        // jaxws-api.jar
        // jaxws-rt.jar
        // gmbal-api-only.jar
        // management-api.jar
        // stax-ex.jar
        // streambuffer.jar
        // policy.jar
        // 6.Copy WAR to “${Tomcat}/webapp” folder.
        // 7.Start It.

        // 调用测试
        URL url = new URL("http://127.0.0.1:80/interfaces/ServiceProviderFacade?wsdl");
        QName qname = new QName("http://www.cloudkit.net/jax-ws/", "ServiceProviderFacadeImplService");
        Service service = Service.create(url, qname);
        ServiceProviderFacade serviceProviderFacade = service.getPort(ServiceProviderFacade.class);

        // Service name
        String serviceName = "net.cloudkit.ecological.enterprises.TestService";

        String contentType = "xml";

        // Request context
        byte[] requestContext = "requestContext".getBytes();
        // Request data
        byte[] requestData = "requestData".getBytes();

        // Response data
        Holder<byte[]> responseData = new Holder<byte[]>("test".getBytes());

        byte[] responseContext = serviceProviderFacade.service(serviceName, contentType, requestContext, requestData, responseData);
        System.out.println(new String(responseContext));

    }
}
