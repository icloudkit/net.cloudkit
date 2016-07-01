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

package net.cloudkit.relaxation;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * HttpClient
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016-05-26 9:39
 */
public class HttpClientTest {

    public static void main(String[] args) throws Exception {


        InetAddress[] addresss = InetAddress.getAllByName("google.com");
        for (InetAddress address : addresss) {

            System.out.println(address);

        }

        CloseableHttpClient httpclient = HttpClients.createDefault();

        String __VIEWSTATE = "";
        String __EVENTVALIDATION = "";

        HttpGet httpGet = new HttpGet("http://query.customs.gov.cn/MNFTQ/MRoadQuery.aspx?" + Math.random() * 1000);
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        httpGet.setHeader("Cache-Control", "no-cache");
        // httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Host", "query.customs.gov.cn");
        httpGet.setHeader("Pragma", "no-cache");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");

        HttpClientContext context = HttpClientContext.create();
        // CloseableHttpResponse response1 = httpclient.execute(httpGet, context);
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        // Header[] headers = response1.getHeaders(HttpHeaders.CONTENT_TYPE);
        // System.out.println("context cookies:" + context.getCookieStore().getCookies());
        // String setCookie = response1.getFirstHeader("Set-Cookie").getValue();
        // System.out.println("context cookies:" + setCookie);

        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            // do something useful with the response body and ensure it is fully consumed

            String result = IOUtils.toString(entity1.getContent(), "GBK");
            // System.out.println(result);

            Matcher m1 = Pattern.compile("<input type=\\\"hidden\\\" name=\\\"__VIEWSTATE\\\" id=\\\"__VIEWSTATE\\\" value=\\\"(.*)\\\" />").matcher(result);
            __VIEWSTATE = m1.find() ? m1.group(1) : "";
            Matcher m2 = Pattern.compile("<input type=\\\"hidden\\\" name=\\\"__EVENTVALIDATION\\\" id=\\\"__EVENTVALIDATION\\\" value=\\\"(.*)\\\" />").matcher(result);
            __EVENTVALIDATION = m2.find() ? m2.group(1) : "";

            System.out.println(__VIEWSTATE);
            System.out.println(__EVENTVALIDATION);

            /*
            File storeFile = new File("D:\\customs\\customs"+ i +".jpg");
            FileOutputStream output = new FileOutputStream(storeFile);
            IOUtils.copy(input, output);
            output.close();
            */
            EntityUtils.consume(entity1);
        } finally {
            response1.close();
        }

        HttpPost httpPost = new HttpPost("http://query.customs.gov.cn/MNFTQ/MRoadQuery.aspx?" + Math.random() * 1000);
        httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpPost.setHeader("Accept-Encoding", "gzip, deflate");
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        httpPost.setHeader("Cache-Control", "no-cache");
        // httpPost.setHeader("Connection", "keep-alive");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("Cookie", "ASP.NET_SessionId=t1td453hcuy4oqiplekkqe55");
        httpPost.setHeader("Host", "query.customs.gov.cn");
        httpPost.setHeader("Origin", "http://query.customs.gov.cn");
        httpPost.setHeader("Pragma", "no-cache");
        httpPost.setHeader("Referer", "http://query.customs.gov.cn/MNFTQ/MRoadQuery.aspx");
        httpPost.setHeader("Upgrade-Insecure-Requests", "1");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("__VIEWSTATE", __VIEWSTATE));
        nvps.add(new BasicNameValuePair("__EVENTVALIDATION", __EVENTVALIDATION));
        nvps.add(new BasicNameValuePair("ScrollTop", ""));
        nvps.add(new BasicNameValuePair("__essVariable", ""));
        nvps.add(new BasicNameValuePair("MRoadQueryCtrl1$txtManifestID", "5100312462240"));
        nvps.add(new BasicNameValuePair("MRoadQueryCtrl1$txtBillNo", "7PH650021105"));
        nvps.add(new BasicNameValuePair("MRoadQueryCtrl1$txtCode", "a778"));
        nvps.add(new BasicNameValuePair("MRoadQueryCtrl1$btQuery", " 查 询 "));
        nvps.add(new BasicNameValuePair("select", "中国政府网"));
        nvps.add(new BasicNameValuePair("select1", "国务院部门网站"));
        nvps.add(new BasicNameValuePair("select2", "地方政府网站"));
        nvps.add(new BasicNameValuePair("select3", "驻港澳机构网站"));
        nvps.add(new BasicNameValuePair("select4", "世界海关组织"));
        nvps.add(new BasicNameValuePair("select5", "在京直属事业单位"));
        nvps.add(new BasicNameValuePair("select6", "社会团体"));
        nvps.add(new BasicNameValuePair("select7", "资讯网"));
        nvps.add(new BasicNameValuePair("select8", "媒体"));


        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "GBK"));

        CloseableHttpResponse response2 = httpclient.execute(httpPost);

        try {
            System.out.println(response2.getStatusLine());
            HttpEntity entity2 = response2.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            // System.out.println(entity2.getContent());
            System.out.println(IOUtils.toString(response2.getEntity().getContent(), "GBK"));

            EntityUtils.consume(entity2);
        } finally {
            response2.close();
        }

    }
}
