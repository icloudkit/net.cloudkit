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

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;

/**
 * This sample demonstrates how to create an empty folder under
 * specfied bucket to Aliyun OSS using the OSS SDK for Java.
 */
public class CreateFolderSample {

    private static String endpoint = "oss-cn-shenzhen.aliyuncs.com";
    // TLCLhaRoudG3Ceq9
    private static String accessKeyId = "TLCLhaRoudG3Ceq9";
    private static String accessKeySecret = "TEXrcq473lUTEQY21WKvTSa8mC3q55";

    private static String bucketName = "hypermedia";

    public static void createFolder(String newFolder) throws IOException {

        /*
         * Constructs a client instance with your account for accessing OSS
         */
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        try {
            /*
             * Create an empty folder without request body, note that the key must be suffixed with a slash
             */
            final String keySuffixWithSlash = "MyObjectKey";
            client.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
            System.out.println("Creating an empty folder " + keySuffixWithSlash + "\n");

            /*
             * Verify whether the size of the empty folder is zero
             */
            OSSObject object = client.getObject(bucketName, keySuffixWithSlash);
            System.out.println("Size of the empty folder '" + object.getKey() + "' is " + object.getObjectMetadata().getContentLength());
            object.getObjectContent().close();

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, " + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code: " + oe.getErrorCode());
            System.out.println("Request ID: " + oe.getRequestId());
            System.out.println("Host ID: " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered a serious internal problem while trying to communicate with OSS, such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            client.shutdown();
        }
    }
}
