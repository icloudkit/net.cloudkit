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

package net.cloudkit.enterprises.ws;

import org.apache.commons.compress.compressors.deflate.DeflateCompressorInputStream;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.ws.Holder;
import javax.xml.ws.Service;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * SuperPassQueryTest.
 */
public class SuperPassQueryTest {

    public static XMLInputFactory factory = XMLInputFactory.newInstance();
    public static SuperPass superPass = null;

    static {
        URL url = null;
        try {
            url = new URL("http://spdec.chinaport.gov.cn/SuperPassDec/SuperPass?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        QName qname = new QName("http://www.cneport.com/webservices/superpass", "SuperPass");
        Service service = Service.create(url, qname);
        superPass = service.getPort(SuperPass.class);
    }

    public static void main(String[] args) throws Exception {



        List<String> params = new ArrayList<>();
        // System.out.println(SuperPassQueryTest.class.getResource("/list.dat").toURI());
        Path path = Paths.get(SuperPassQueryTest.class.getResource("/list.dat").toURI());
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
            // System.out.println(reader.readLine().length());
            String line;
            while ((line = reader.readLine()) != null) {
                // System.out.println("TEXT LINE:" + line);
                params.add(line);
            }
        }

        Path succeededFile = Paths.get(SuperPassQueryTest.class.getResource("/succeeded.dat").toURI());
        BufferedWriter succeededWriter = Files.newBufferedWriter(succeededFile, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        Path failedFile = Paths.get(SuperPassQueryTest.class.getResource("/failed.dat").toURI());
        BufferedWriter failedWriter = Files.newBufferedWriter(failedFile, StandardCharsets.UTF_8, StandardOpenOption.APPEND);

        for (String param : params) {
            try {
                /*
                StringTokenizer stringTokenizer = new StringTokenizer(param, ",");
                while(stringTokenizer.hasMoreTokens()){
                    System.out.println("COUNT:" + stringTokenizer.countTokens());
                    System.out.println("VALUE:" + stringTokenizer.nextToken());
                    System.out.println("COUNT:" + stringTokenizer.countTokens());
                }
                */

                System.out.println("QUERY PARAMS:" + param);
                String[] paramArray = param.split(",");
                // System.out.println("VALUE:" + paramArray[0]);
                // System.out.println("VALUE:" + paramArray[1]);
                // System.out.println("VALUE:" + paramArray[2]);

                String value_1 = paramArray[0];
                String value_2 = paramArray[1];
                String value_3 = paramArray[2];

                String serviceName = "eport.superpass.spdec.DecQueryListService";
                byte[] requestContext = ("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n" +
                    "<RequestContext>\n" +
                    "    <Group name=\"SystemInfo\">\n" +
                    "        <Key name=\"NAME_FULL\">深圳市巨航国际物流有限公司</Key>\n" +
                    "        <Key name=\"ClientId\">5300001976914</Key>\n" +
                    "        <Key name=\"CertNo\">df630b</Key>\n" +
                    "        <Key name=\"SaicSysNo\">766350979</Key>\n" +
                    "        <Key name=\"DEP_IN_CODE\">5300</Key>\n" +
                    "        <Key name=\"REG_CO_CGAC\">4403180237</Key>\n" +
                    "        <Key name=\"ENT_SEQ_NO\">000000000000315537</Key>\n" +
                    "        <Key name=\"ENT_TYPE\">3</Key>\n" +
                    "        <Key name=\"IcCode\">8930000011040</Key>\n" +
                    "        <Key name=\"OperatorName\">杨倩</Key>\n" +
                    "        <Key name=\"DEP_CODE_CHG\">5305</Key>\n" +
                    "        <Key name=\"SessionId\">AE2533938D521A9972186B07BBBEB244</Key>\n" +
                    "    </Group>\n" +
                    "    <Group name=\"DataPresentation\">\n" +
                    "        <Key name=\"SignatureAlgorithm\"/>\n" +
                    "        <Key name=\"EncryptAlgorithm\"/>\n" +
                    "        <Key name=\"CompressAlgorithm\"/>\n" +
                    "    </Group>\n" +
                    "    <Group name=\"Default\">\n" +
                    "        <Key name=\"clientSystemId\">0400620001</Key>\n" +
                    "        <Key name=\"needWebInvoke\">True</Key>\n" +
                    "    </Group>\n" +
                    "</RequestContext>").getBytes();

                byte[] requestData = ("<?xml version=\"1.0\"?>\n" +
                    "<DecQueryListRequest xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
                    "  <OperType>0</OperType>\n" +
                    "  <DecType>\n" +
                    "    <TrnType>0</TrnType>\n" +
                    "    <IEFlag>" + value_3 + "</IEFlag>\n" +
                    "    <DecSubType />\n" +
                    "  </DecType>\n" +
                    "  <CopeCode>766350979</CopeCode>\n" +
                    "  <AgentCode>4403180237</AgentCode>\n" +
                    "  <SeqNo>" + value_1 + "</SeqNo>\n" +
                    "  <UserType>0</UserType>\n" +
                    "</DecQueryListRequest>").getBytes();

                Holder<byte[]> responseData = new Holder<>();

                // <?xml version="1.0" encoding="UTF-8" standalone="no"?><ResponseContext><ResponseCode>0</ResponseCode><ResponseMessage>success</ResponseMessage><ServiceResponseCode>0</ServiceResponseCode><ServiceResponseMessage>调用成功</ServiceResponseMessage><ExceptionDetail/><Group name="DataPresentation"><Key name="CompressAlgorithm"/><Key name="SignatureAlgorithm"/><Key name="EncryptAlgorithm"/></Group></ResponseContext>
                // <?xml version="1.0" encoding="UTF-8" standalone="yes"?><DecQueryListResponse><QueryResponseData><EntryId>531820161181010544</EntryId><SeqNo>000000001139524197</SeqNo><BillNo>2016051920160523</BillNo><IEDate>20160621</IEDate><TradeMode>0615</TradeMode><ItemsNum>19</ItemsNum><TrafName>集中申报</TrafName><Status>O</Status><AgentName>深圳市巨航国际物流有限公司</AgentName><IEFlag>I</IEFlag><CustomsCode>5318</CustomsCode><DeclTrnRel>0</DeclTrnRel><RetExplain>结关;报关单结关</RetExplain><NoticeDate>2016-06-29</NoticeDate><TradeName>富葵精密组件(深圳)有限公司</TradeName><ExtendField><DecDeclareSysType>2</DecDeclareSysType><TrnSysType>1</TrnSysType><AssureExamRet>0</AssureExamRet><RelatedDocumentType>  </RelatedDocumentType><DeclareSeqNo>                  </DeclareSeqNo><ExtendField53>P</ExtendField53><ExtendField>21                                                   P</ExtendField></ExtendField><EntryType>M</EntryType></QueryResponseData></DecQueryListResponse>
                // String responseContext = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><ResponseContext><ResponseCode>0</ResponseCode><ResponseMessage>success</ResponseMessage><ServiceResponseCode>0</ServiceResponseCode><ServiceResponseMessage>调用成功</ServiceResponseMessage><ExceptionDetail/><Group name=\"DataPresentation\"><Key name=\"CompressAlgorithm\"/><Key name=\"SignatureAlgorithm\"/><Key name=\"EncryptAlgorithm\"/></Group></ResponseContext>";
                // String queryListResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><DecQueryListResponse><QueryResponseData><EntryId>531820161181010544</EntryId><SeqNo>000000001139524197</SeqNo><BillNo>2016051920160523</BillNo><IEDate>20160621</IEDate><TradeMode>0615</TradeMode><ItemsNum>19</ItemsNum><TrafName>集中申报</TrafName><Status>O</Status><AgentName>深圳市巨航国际物流有限公司</AgentName><IEFlag>I</IEFlag><CustomsCode>5318</CustomsCode><DeclTrnRel>0</DeclTrnRel><RetExplain>结关;报关单结关</RetExplain><NoticeDate>2016-06-29</NoticeDate><TradeName>富葵精密组件(深圳)有限公司</TradeName><ExtendField><DecDeclareSysType>2</DecDeclareSysType><TrnSysType>1</TrnSysType><AssureExamRet>0</AssureExamRet><RelatedDocumentType>  </RelatedDocumentType><DeclareSeqNo>                  </DeclareSeqNo><ExtendField53>P</ExtendField53><ExtendField>21                                                   P</ExtendField></ExtendField><EntryType>M</EntryType></QueryResponseData></DecQueryListResponse>";

                String responseContext = new String(superPass.service(serviceName, requestContext, requestData, responseData));
                String queryListResponse = new String(responseData.value);
                System.out.println("RESPONSE_CONTEXT:" + responseContext);
                System.out.println("QUERY_LIST_RESPONSE:" + queryListResponse);

                String serviceResponseCode = parsingReceiptStatus(responseContext);
                System.out.println("SERVICE_RESPONSE_CODE:" + serviceResponseCode);
                if (serviceResponseCode.equals("0")) {
                    String data = parsingReceiptData(queryListResponse);
                    System.out.println("DATA:" + data);
                    succeededWriter.write(data);
                    succeededWriter.flush();
                } else {
                    failedWriter.write(param + "\n");
                    failedWriter.flush();
                }
                Thread.sleep(6 * 1000);
            } catch (Exception e) {
                failedWriter.write(param + "\n");
                failedWriter.flush();
            }
        }
        succeededWriter.close();
        failedWriter.close();
    }

    public static String parsingReceiptStatus(String responseContext) throws XMLStreamException {
        String serviceResponseCode = "-1";
        XMLStreamReader reader = factory.createXMLStreamReader(new StringReader(responseContext));
        try {
            int event = reader.getEventType();
            while (true) {
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        // System.out.println(reader.getName());
                        if (reader.getName().toString().equals("ServiceResponseCode")) {
                            // System.out.println(reader.getElementText());
                            serviceResponseCode = reader.getElementText();
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        // System.out.println("End Element:" + r.getName());
                        break;
                }
                if (!reader.hasNext())
                    break;
                event = reader.next();
            }
        } finally {
            reader.close();
        }
        return serviceResponseCode;
    }

    public static String parsingReceiptData(String responseData) throws XMLStreamException {
        StringBuilder dataBuilder = new StringBuilder("");
        XMLStreamReader reader = factory.createXMLStreamReader(new StringReader(responseData));
        try {
            int event = reader.getEventType();
            while (true) {
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:

                        if (reader.getName().toString().equals("TradeName")) {
                            // System.out.println(reader.getElementText());
                            dataBuilder.append(reader.getElementText());
                            dataBuilder.append("\t");
                        }
                        // System.out.println(reader.getName());
                        if (reader.getName().toString().equals("SeqNo")) {
                            // System.out.println(reader.getElementText());
                            dataBuilder.append(reader.getElementText());
                            dataBuilder.append("\t");
                        }
                        // BillNo
                        if (reader.getName().toString().equals("EntryId")) {
                            // System.out.println(reader.getElementText());
                            dataBuilder.append(reader.getElementText());
                            dataBuilder.append("\t");
                        }
                        if (reader.getName().toString().equals("IEFlag")) {
                            // System.out.println(reader.getElementText());
                            dataBuilder.append(reader.getElementText());
                            dataBuilder.append("\t");
                        }
                        // 是否轉現場
                        if (reader.getName().toString().equals("IEDate")) {
                            // System.out.println(reader.getElementText());
                            dataBuilder.append(reader.getElementText());
                            dataBuilder.append("\t");
                        }
                        if (reader.getName().toString().equals("TradeMode")) {
                            // System.out.println(reader.getElementText());
                            dataBuilder.append(reader.getElementText());
                            dataBuilder.append("\t");
                        }
                        if (reader.getName().toString().equals("Status")) {
                            // System.out.println(reader.getElementText());
                            dataBuilder.append(reader.getElementText());
                            dataBuilder.append("\t");
                        }
                        if (reader.getName().toString().equals("RetExplain")) {
                            // System.out.println(reader.getElementText());
                            dataBuilder.append(reader.getElementText());
                            dataBuilder.append("\t");
                        }
                        // NoticeDate
                        // CustomsCode
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        // System.out.println("End Element:" + r.getName());
                        break;
                }
                if (!reader.hasNext())
                    break;
                event = reader.next();
            }
        } finally {
            reader.close();
        }
        dataBuilder.append("\n");
        return dataBuilder.toString();
    }

    // eport.superpass.spdec.DecQueryDetailService
    public static void queryDetail(String seqNo) throws MalformedURLException {

        String serviceName = "eport.superpass.spdec.DecQueryListService";
        byte[] requestContext = ("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n" +
            "<RequestContext>\n" +
            "    <Group name=\"SystemInfo\">\n" +
            "        <Key name=\"NAME_FULL\">深圳市巨航国际物流有限公司</Key>\n" +
            "        <Key name=\"ClientId\">5300001976914</Key>\n" +
            "        <Key name=\"CertNo\">df630b</Key>\n" +
            "        <Key name=\"SaicSysNo\">766350979</Key>\n" +
            "        <Key name=\"DEP_IN_CODE\">5300</Key>\n" +
            "        <Key name=\"REG_CO_CGAC\">4403180237</Key>\n" +
            "        <Key name=\"ENT_SEQ_NO\">000000000000315537</Key>\n" +
            "        <Key name=\"ENT_TYPE\">3</Key>\n" +
            "        <Key name=\"IcCode\">8930000011040</Key>\n" +
            "        <Key name=\"OperatorName\">杨倩</Key>\n" +
            "        <Key name=\"DEP_CODE_CHG\">5305</Key>\n" +
            "        <Key name=\"SessionId\">3BCCE1ED647530A76964A7B2EE4D1CE2</Key>\n" +
            "    </Group>\n" +
            "    <Group name=\"DataPresentation\">\n" +
            "        <Key name=\"SignatureAlgorithm\"/>\n" +
            "        <Key name=\"EncryptAlgorithm\"/>\n" +
            "        <Key name=\"CompressAlgorithm\"/>\n" +
            "    </Group>\n" +
            "    <Group name=\"Default\">\n" +
            "        <Key name=\"clientSystemId\">0400620001</Key>\n" +
            "        <Key name=\"needWebInvoke\">True</Key>\n" +
            "    </Group>\n" +
            "</RequestContext>").getBytes();

        byte[] requestData = ("<?xml version=\"1.0\"?>\n" +
            "<DecQueryDetailRequest xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
            "  <SeqNo>" + seqNo + "</SeqNo>\n" +
            "</DecQueryDetailRequest>").getBytes();

        Holder<byte[]> responseData = new Holder<>();

        String responseContext = new String(superPass.service(serviceName, requestContext, requestData, responseData));
        String queryListResponse = new String(responseData.value);
        System.out.println("RESPONSE_CONTEXT:" + responseContext);
        System.out.println("QUERY_LIST_RESPONSE:" + queryListResponse);
    }
}
