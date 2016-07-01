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

import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Holder;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 绕过客户端激活
 */
//Service Implementation Bean
@WebService(endpointInterface = "net.cloudkit.enterprises.ws.SuperPass")
public class SuperPassImpl implements SuperPass {

    private static String random = "";
    private static String installId = "";

    @Override
    public byte[] service(String serviceName, byte[] requestContext, byte[] requestData, Holder<byte[]> responseData) {

        System.out.println("=========================================================================================");
        System.out.println("serviceName: \n" + serviceName + "\nrequestContext: \n" + new String(requestContext) + "\nrequestData: \n" + new String(requestData));
        try {
            if (serviceName.equals("eport.superpass.pas.Random")) {

                byte[] result = SuperPassImpl.invoke(serviceName, requestContext, requestData, responseData);

                // responseData.value = "k5uZppGjoaKlY5ei".getBytes();
                // return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ResponseContext><ResponseCode>0</ResponseCode><ResponseMessage>success</ResponseMessage><ServiceResponseCode>0</ServiceResponseCode><ServiceResponseMessage></ServiceResponseMessage><ExceptionDetail></ExceptionDetail><Group name=\"DataPresentation\"><Key name=\"CompressAlgorithm\"></Key><Key name=\"SignatureAlgorithm\"></Key><Key name=\"EncryptAlgorithm\"></Key></Group></ResponseContext>".getBytes();
                random = new String(responseData.value);
                System.out.println(random);
                return result;

            } else if (serviceName.equals("eport.superpass.pas.NewClientIdExist")) {
                installId = TalkClient.getInstallId(random);
                requestContext = ("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?><RequestContext><Group name=\"SystemInfo\"><Key name=\"IcCode\" /><Key name=\"OperatorName\" /><Key name=\"CertNo\" /></Group><Group name=\"Pas\"><Key name=\"InstallIdEncrypted\">1</Key></Group><Group name=\"DataPresentation\"><Key name=\"EncryptAlgorithm\" /><Key name=\"CompressAlgorithm\" /><Key name=\"SignatureAlgorithm\" /></Group></RequestContext>").getBytes();
                requestData = ("<?xml version=\"1.0\"?><NewClientIdExistRequest xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://www.chinaport.gov.cn/PAS\"><NewInstallId>" + installId + "</NewInstallId></NewClientIdExistRequest>").getBytes();
                byte[] result = SuperPassImpl.invoke(serviceName, requestContext, requestData, responseData);

                // responseData.value = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><NewClientIdExistResponse xmlns=\"http://www.chinaport.gov.cn/PAS\" xmlns:ns2=\"http://www.chinaport.gov.cn/pas\"><Seq>5300001129778</Seq><ClientType>17</ClientType><CustomsCode>5300</CustomsCode></NewClientIdExistResponse>".getBytes();
                // return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ResponseContext><ResponseCode>0</ResponseCode><ResponseMessage>success</ResponseMessage><ServiceResponseCode>0</ServiceResponseCode><ServiceResponseMessage>客户端信息已采集</ServiceResponseMessage><ExceptionDetail></ExceptionDetail><Group name=\"DataPresentation\"><Key name=\"CompressAlgorithm\"></Key><Key name=\"SignatureAlgorithm\"></Key><Key name=\"EncryptAlgorithm\"></Key></Group></ResponseContext>".getBytes();

                return result;

            } else if (serviceName.equals("eport.superpass.pas.FreeClientTypeCheck")) {

                byte[] result = SuperPassImpl.invoke(serviceName, requestContext, requestData, responseData);

                // responseData.value = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ReTipResponse xmlns=\"http://www.chinaport.gov.cn/PAS\" xmlns:ns2=\"http://www.chinaport.gov.cn/pas\"><ResultCode>-1</ResultCode><PromptInfo>非免费客户端类型。</PromptInfo></ReTipResponse>\n".getBytes();
                // return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ResponseContext><ResponseCode>0</ResponseCode><ResponseMessage>success</ResponseMessage><ServiceResponseCode>-1</ServiceResponseCode><ServiceResponseMessage>非免费客户端类型。</ServiceResponseMessage><ExceptionDetail></ExceptionDetail><Group name=\"DataPresentation\"><Key name=\"CompressAlgorithm\"></Key><Key name=\"SignatureAlgorithm\"></Key><Key name=\"EncryptAlgorithm\"></Key></Group></ResponseContext>".getBytes();

                return result;

            } else if (serviceName.equals("eport.superpass.pas.LicenseCheck")) {

                requestData = ("<LicenseCheckRequest xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://www.chinaport.gov.cn/pas\"><InstallId>" + installId + "</InstallId></LicenseCheckRequest>").getBytes();

                // 001743068 5300001129778
                // 5300001972822
                // responseData.value = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><LicenseCheckResponse xmlns=\"http://www.chinaport.gov.cn/pas\"><NeedCheckActivate>true</NeedCheckActivate><IsActivated>true</IsActivated><ProbationDaysRemainder>0</ProbationDaysRemainder><LicenseState>B</LicenseState><LicenseEndDate>-1</LicenseEndDate><LicenseID>001743068</LicenseID><ApplyNo>XKY843EGKYNEJMEEADCD</ApplyNo><ActivationID>5300001976914</ActivationID><ClientType>17</ClientType><ValidReminderDays>30</ValidReminderDays></LicenseCheckResponse>".getBytes();
                // return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ResponseContext><ResponseCode>0</ResponseCode><ResponseMessage>success</ResponseMessage><ServiceResponseCode>0</ServiceResponseCode><ServiceResponseMessage></ServiceResponseMessage><ExceptionDetail></ExceptionDetail><Group name=\"DataPresentation\"><Key name=\"CompressAlgorithm\"></Key><Key name=\"SignatureAlgorithm\"></Key><Key name=\"EncryptAlgorithm\"></Key></Group></ResponseContext>".getBytes();

                byte[] result = SuperPassImpl.invoke(serviceName, requestContext, requestData, responseData);
                // result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ResponseContext><ResponseCode>0</ResponseCode><ResponseMessage>success</ResponseMessage><ServiceResponseCode>0</ServiceResponseCode><ServiceResponseMessage></ServiceResponseMessage><ExceptionDetail></ExceptionDetail><Group name=\"DataPresentation\"><Key name=\"CompressAlgorithm\"></Key><Key name=\"SignatureAlgorithm\"></Key><Key name=\"EncryptAlgorithm\"></Key></Group></ResponseContext>".getBytes();
                // responseData.value = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><LicenseCheckResponse xmlns=\"http://www.chinaport.gov.cn/pas\"><NeedCheckActivate>false</NeedCheckActivate><IsActivated>true</IsActivated><ProbationDaysRemainder>0</ProbationDaysRemainder><LicenseEndDate>0</LicenseEndDate><ActivationID>5300001976914</ActivationID><ClientType>01</ClientType><ValidReminderDays>30</ValidReminderDays></LicenseCheckResponse>".getBytes();

                return result;

            } else if (serviceName.equals("eport.superpass.pas.ClientTypeAuthQuery")) {

                // responseData.value = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ClientTypeAuthQueryResponse xmlns=\"http://www.chinaport.gov.cn/PAS\" xmlns:ns2=\"http://www.chinaport.gov.cn/pas\"><CustomsCode>5300</CustomsCode><ClientType>17</ClientType><ClientId>5300001129778</ClientId></ClientTypeAuthQueryResponse>".getBytes();
                // return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ResponseContext><ResponseCode>0</ResponseCode><ResponseMessage>success</ResponseMessage><ServiceResponseCode>0</ServiceResponseCode><ServiceResponseMessage>客户端定制权限查询成功</ServiceResponseMessage><ExceptionDetail></ExceptionDetail><Group name=\"DataPresentation\"><Key name=\"CompressAlgorithm\"></Key><Key name=\"SignatureAlgorithm\"></Key><Key name=\"EncryptAlgorithm\"></Key></Group></ResponseContext>".getBytes();

                byte[] result = SuperPassImpl.invoke(serviceName, requestContext, requestData, responseData);
                // requestData = ("<ClientTypeAuthQueryRequest xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://www.chinaport.gov.cn/PAS\"><InstallId>" + installId + "</InstallId></ClientTypeAuthQueryRequest>").getBytes();

                return result;

            } else if (serviceName.equals("eport.superpass.pas.ActivationInfoFormat")) {

                byte[] result = SuperPassImpl.invoke(serviceName, requestContext, requestData, responseData);

                responseData.value = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><LicenseCheckResponse xmlns=\"http://www.chinaport.gov.cn/pas\"><NeedCheckActivate>true</NeedCheckActivate><IsActivated>true</IsActivated><ProbationDaysRemainder>0</ProbationDaysRemainder><LicenseState>B</LicenseState><LicenseEndDate>-1</LicenseEndDate><LicenseID>001743068</LicenseID><ApplyNo>XKY843EGKYNEJMEEADCD</ApplyNo><ActivationID>5300001976914</ActivationID><ClientType>17</ClientType><ValidReminderDays>30</ValidReminderDays></LicenseCheckResponse>".getBytes();
                return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ResponseContext><ResponseCode>0</ResponseCode><ResponseMessage>success</ResponseMessage><ServiceResponseCode>0</ServiceResponseCode><ServiceResponseMessage>激活码无效</ServiceResponseMessage><ExceptionDetail></ExceptionDetail><Group name=\"DataPresentation\"><Key name=\"CompressAlgorithm\"></Key><Key name=\"SignatureAlgorithm\"></Key><Key name=\"EncryptAlgorithm\"></Key></Group></ResponseContext>".getBytes();

            } else {

                byte[] result = SuperPassImpl.invoke(serviceName, requestContext, requestData, responseData);

                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "".getBytes();
    }

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
        URL url = new URL("http://220.181.191.7/SuperPass/SuperPass?wsdl");
        QName qname = new QName("http://www.cneport.com/webservices/superpass", "SuperPass");
        Service service = Service.create(url, qname);
        SuperPass superPass = service.getPort(SuperPass.class);
        // Holder<byte[]> responseData = new Holder<byte[]>();
        byte[] result = superPass.service(serviceName, requestContext, requestData, responseData);

        System.out.println("responseContext: \n" + new String(result) + "\nresponseData: \n" + new String(responseData.value));
        System.out.println();
        return result;
    }
}
