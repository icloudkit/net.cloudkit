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

import javax.xml.namespace.QName;
import javax.xml.ws.Holder;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * QP 参数查询
 */
public class CdlDownloadTest {

    /**
     * 调用 WebService
     *
     * @param serviceName
     * @param requestContext
     * @param requestData
     * @param responseData
     * @return
     * @throws MalformedURLException
     */
    public static byte[] invoke(String serviceName, byte[] requestContext, byte[] requestData, Holder<byte[]> responseData) throws MalformedURLException {

        // http://220.181.191.7/SuperPass/SuperPass?wsdl
        // 调用外部接口
        URL url = new URL("http://sptrade.chinaport.gov.cn/SuperPassCdl/SuperPass?wsdl");
        QName qname = new QName("http://www.cneport.com/webservices/superpass", "SuperPass");
        Service service = Service.create(url, qname);
        SuperPass superPass = service.getPort(SuperPass.class);
        // Holder<byte[]> responseData = new Holder<byte[]>();
        byte[] result = superPass.service(serviceName, requestContext, requestData, responseData);

        System.out.println("responseContext: \n" + new String(result) + "\nresponseData: \n" + new String(responseData.value));
        System.out.println();
        return result;
    }

    public static void main(String[] args) throws Exception {
        String serviceName = "eport.superpass.cdl.QueryEnableCdl2EntryService";
        byte[] requestContext = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?><RequestContext><Group name=\"SystemInfo\"><Key name=\"DEP_CODE_CHG\">5300</Key><Key name=\"OperatorName\">吕亚玲</Key><Key name=\"ENT_TYPE\">3</Key><Key name=\"SaicSysNo\">766350979</Key><Key name=\"SessionId\">C27462BEEA063EA14AC7E4FE8230F651</Key><Key name=\"CertNo\">df5eed</Key><Key name=\"ClientId\">5300002150968</Key><Key name=\"REG_CO_CGAC\">4403180237</Key><Key name=\"DEP_IN_CODE\">5300</Key><Key name=\"NAME_FULL\">深圳市巨航国际物流有限公司</Key><Key name=\"IcCode\">8320000014608</Key><Key name=\"ENT_SEQ_NO\">000000000000315537</Key></Group><Group name=\"DataPresentation\"><Key name=\"EncryptAlgorithm\" /><Key name=\"CompressAlgorithm\" /><Key name=\"SignatureAlgorithm\" /></Group></RequestContext>".getBytes();
        byte[] requestData = ("<?xml version=\"1.0\"?>\n" +
            "<QueryEnableCdl2EntryRequest xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
            "  <DeclDateBegin>20160501</DeclDateBegin>\n" +
            "  <DeclDateEnd>20160531</DeclDateEnd>\n" +
            "  <TradeBondedFlag>01</TradeBondedFlag>\n" +
            "  <AgentCode>4403180237</AgentCode>\n" +
            "  <TypistUnitCode>766350979$4403180237</TypistUnitCode>\n" +
            "  <ClientDate>20160603</ClientDate>\n" +
            "  <IEPort />\n" +
            "  <TradeMode />\n" +
            "  <TrafMode />\n" +
            "  <IEFlag>I</IEFlag>\n" +
            "  <OwnerCode />\n" +
            "  <TradeCode>4403944494</TradeCode>\n" +
            "</QueryEnableCdl2EntryRequest>").getBytes();
        Holder<byte[]> responseData = new Holder<>();

        byte[] result = invoke(serviceName, requestContext, requestData, responseData);
        System.out.println(new String(result));
    }
}
