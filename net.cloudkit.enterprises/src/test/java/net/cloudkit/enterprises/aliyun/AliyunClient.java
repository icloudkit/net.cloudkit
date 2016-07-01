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

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;

/**
 * AliyunClient.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016/1/15 15:14
 */
public class AliyunClient {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;

    private static OSSClient ossClient;

    private AliyunClient() {
        super();
    }

    private static class LazyHolder {
        private static final AliyunClient INSTANCE = new AliyunClient();
    }

    public void initOSSClient(String endpoint, String accessKeyId, String accessKeySecret) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        // this.bucketName = bucketName;

        if(ossClient == null) {
            ClientConfiguration conf = new ClientConfiguration();
            conf.setConnectionTimeout(5000);
            conf.setMaxErrorRetry(10);
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);
        }
    }

    public static AliyunClient getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static OSSClient getOSSClient() {
        return ossClient;
    }

}
