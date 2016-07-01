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

package net.cloudkit.enterprises.aliyun;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * This sample demonstrates how to upload/download an object to/from
 * Aliyun OSS using the OSS SDK for Java.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:application-context*.xml"})
@ActiveProfiles("test")
public class AliyunOSSHelperTest {

    public static final String ALIYUN_OSS_ENDPOINT_KEY_NAME ="ALIYUN_OSS_ENDPOINT";
    public static final String ALIYUN_OSS_ACCESS_KEY_ID_KEY_NAME ="ALIYUN_OSS_ACCESS_KEY_ID";
    public static final String ALIYUN_OSS_ACCESS_KEY_SECRET_KEY_NAME ="ALIYUN_OSS_ACCESS_KEY_SECRET";
    public static final String ALIYUN_OSS_BUCKET_NAME_KEY_NAME ="ALIYUN_OSS_BUCKET_NAME";
    public static final String ALIYUN_OSS_SYSTEM_PATH_KEY_NAME ="ALIYUN_OSS_SYSTEM_PATH";

    /*
    private static String endpoint = "oss-cn-shenzhen.aliyuncs.com";
    private static String accessKeyId = "Gi0Yziw4U8wzEL4I";
    private static String accessKeySecret = "A5g3t8l6bOL2ZYk4V0GPlQuTCxolus";

    private static String bucketName = "repositories";
    // private static String key = "client-v1.0.0.exe";
    private static String key = "system/test.txt";
    */

    @Autowired
    @Qualifier("configProperties")
    private Properties properties;

    @Test
    public void test() {

        String endpoint = properties.get(ALIYUN_OSS_ENDPOINT_KEY_NAME).toString();
        String accessKeyId = properties.get(ALIYUN_OSS_ACCESS_KEY_ID_KEY_NAME).toString();
        String accessKeySecret = properties.get(ALIYUN_OSS_ACCESS_KEY_SECRET_KEY_NAME).toString();
        String bucketName = properties.get(ALIYUN_OSS_BUCKET_NAME_KEY_NAME).toString();

        // private static String key = "client-v1.0.0.exe";
        String key = properties.get(ALIYUN_OSS_SYSTEM_PATH_KEY_NAME).toString() + "test.txt";

        /*
         * Constructs a client instance with your account for accessing OSS
         */
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        try {
            /*
             * Download an object from your bucket
             */
            System.out.println("Downloading an object");

            OSSObject object = client.getObject(new GetObjectRequest(bucketName, key));
            System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
            displayTextInputStream(object.getObjectContent());
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code: " + oe.getErrorCode());
            System.out.println("Request ID: " + oe.getRequestId());
            System.out.println("Host ID: " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered a serious internal problem while trying to communicate with OSS, such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            client.shutdown();
        }
    }

    private void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("\t" + line);
        }
        System.out.println();

        reader.close();
    }
}
