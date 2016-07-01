//package net.cloudkit.ecological.enterprises.experiment;
//
//import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.http.converter.ByteArrayHttpMessageConverter;
//import org.springframework.http.converter.FormHttpMessageConverter;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class HttpClientTest {
//
//    public static HttpClient getHttpClient(){
//        SchemeRegistry registry = new SchemeRegistry();//创建schema
//        SSLContext sslContext = null;
//        try{
//            sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, null, null);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        SSLSocketFactory sslFactory = new SSLSocketFactory(sslContext,SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
//        registry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
//        registry.register(new Scheme("https", 443,sslFactory));
//        PoolingClientConnectionManager cm = new PoolingClientConnectionManager(registry);//创建connectionManager
//        cm.setDefaultMaxPerRoute(100);//对每个指定连接的服务器（指定的ip）可以创建并发20socket进行访问
//        cm.setMaxTotal(200);//创建socket的上线是200
//
//        HttpClient httpClient = new DefaultHttpClient(cm);//使用连接池创建连接
//        HttpParams params = httpClient.getParams();
//        HttpConnectionParams.setSoTimeout(params, 60*1000);
//        HttpConnectionParams.setConnectionTimeout(params, 60*1000);
//        return httpClient;
//    }
//
//    public static void main(String[] args) {
//        //创建httpclient
//        HttpClient httpClient = getHttpClient();
//        //创建HttpComponentsClientHttpRequestFactory HttpComponentsAsyncClientHttpRequestFactory
//        HttpComponentsClientHttpRequestFactory httpFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//        //创建resttemplate
//        RestTemplate template = new RestTemplate(httpFactory);
//
//        //设置message的处理种类
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//        messageConverters.add(new StringHttpMessageConverter());
//        messageConverters.add(new ByteArrayHttpMessageConverter());
//        messageConverters.add(new FormHttpMessageConverter());
//        template.setMessageConverters(messageConverters);
//
//        //访问远程请求
//        System.out.println(template.getForObject("http://m.weather.com.cn/data/101010100.html", String.class));
//
//    }
//}
