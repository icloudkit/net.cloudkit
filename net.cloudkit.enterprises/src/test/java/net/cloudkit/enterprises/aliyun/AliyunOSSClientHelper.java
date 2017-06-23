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
import com.aliyun.oss.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016/1/14 9:52
 */
public class AliyunOSSClientHelper {

    private static final Logger logger = LoggerFactory.getLogger(AliyunOSSClientHelper.class);

    public static final String ALIYUN_OSS_ENDPOINT_KEY_NAME = "ALIYUN_OSS_ENDPOINT";
    public static final String ALIYUN_OSS_ACCESS_KEY_ID_KEY_NAME = "ALIYUN_OSS_ACCESS_KEY_ID";
    public static final String ALIYUN_OSS_ACCESS_KEY_SECRET_KEY_NAME = "ALIYUN_OSS_ACCESS_KEY_SECRET";
    public static final String ALIYUN_OSS_BUCKET_NAME_KEY_NAME = "ALIYUN_OSS_BUCKET_NAME";

    /*
    private static String endpoint = "oss-cn-shenzhen.aliyuncs.com";
    private static String accessKeyId = "xxxxxx";
    private static String accessKeySecret = "xxxxxx";
    */

    private static String bucketName = "hypermedia";

    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    private List<PartETag> partETags = Collections.synchronizedList(new ArrayList<PartETag>());

    public static OSSClient getOSSClient() {
        String endpoint = "oss-cn-shenzhen.aliyuncs.com";
        String accessKeyId = "se35rTiqeJ2Ny1oJ";
        String accessKeySecret = "rAL0Fbgbd6VUaa6Fz1DcrSr4qbLSqm";
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        return client;
    }

    /**
     * 新建bucket
     * Bucket是OSS全局命名空间，相当于数据的容器，可以存储若干Object。
     * 关于Bucket的命名规范，参见Bucket中的命名规范。
     *
     * @param bucketName
     */
    public static void createBucket(OSSClient client, String bucketName) {
        // 初始化OSSClient
        // OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // OSSClient client = getOSSClient();

        // 新建一个Bucket
        client.createBucket(bucketName);
    }

    /**
     * 列出用户所有的Bucket
     */
    public static List<Bucket> listAllBucket(OSSClient client) {
        // 初始化OSSClient
        // OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // OSSClient client = getOSSClient();

        // 获取用户的Bucket列表
        List<Bucket> buckets = client.listBuckets();

        /*
        // 遍历Bucket
        for (Bucket bucket : buckets) {
            logger.info(bucket.getName());
        }
        */
        return buckets;
    }

    /**
     * 判断Bucket是否存在
     *
     * @param bucketName
     */
    public static boolean isExistsBucket(OSSClient client, String bucketName) {
        // 初始化OSSClient
        // OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // OSSClient client = getOSSClient();

        // String bucketName = "your-bucket-name";
        // 获取Bucket的存在信息
        boolean exists = client.doesBucketExist(bucketName);

        /*
        // 输出结果
        if (exists) {
            System.out.println("Bucket exists");
        } else {
            System.out.println("Bucket not exists");
        }
        */
        return exists;
    }

    /**
     * 设置Bucket ACL
     * 私有 CannedAccessControlList.Private
     * 公共读 CannedAccessControlList.PublicRead
     * 公共读写 CannedAccessControlList.PublicReadWrite
     * 默认权限 CannedAccessControlList.Default
     *
     * @param bucketName
     */
    public static void setBucketAcl(OSSClient client, String bucketName, CannedAccessControlList acl) {
        // 初始化OSSClient
        // OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // OSSClient client = getOSSClient();
        // String bucketName = "your-bucket-name";
        // 以设置为私有权限举例
        client.setBucketAcl(bucketName, acl);
    }

    /**
     * 获取Bucket地址
     *
     * @param bucketName
     */
    public static String getBucketLocation(OSSClient client, String bucketName) {
        // 初始化OSSClient
        // OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // OSSClient client = getOSSClient();

        // String bucketName = "your-bucket-name";
        // 获取 bucket 地址
        String location = client.getBucketLocation(bucketName);
        // System.out.println(location);
        return location;
    }

    /**
     * 删除Bucket
     * 如果Bucket不为空（Bucket中有Object），则Bucket无法删除，必须删除Bucket中的所有Object后，Bucket才能成功删除。
     *
     * @param bucketName
     */
    public static void deleteBucket(OSSClient client, String bucketName) {
        // 初始化OSSClient
        // OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // OSSClient client = getOSSClient();

        // String bucketName = "your-bucket-name";
        // 删除Bucket
        client.deleteBucket(bucketName);
    }

    /*
     * 设定Object的ACL
     * Object的ACL
     * public-read-write | 公共读写 | 该ACL表明某个Object是公共读写资源，即所有用户拥有对该Object的读写权限。 |
     * public-read | 公共读，私有写 | 该ACL表明某个Object是公共读资源，即非Object Owner只有该Object的读权限，而Object Owner拥有该Object的读写权限。 |
     * private | 私有读写 | 该ACL表明某个Object是私有资源，即只有该Object的Owner拥有该Object的读写权限，其他的用户没有权限操作该Object。 |
     * default | 默认权限 | 该ACL表明某个Object是遵循Bucket读写权限的资源，即Bucket是什么权限，Object就是什么权限 |
     * 设置Object ACL注意事项
     * 如果没有设置Object的权限，即Object的ACL为default，Object的权限和Bucket权限一致。
     * 如果设置了Object的权限，Object的权限大于Bucket权限。举个例子，如果设置了Object的权限是
     * public-read，无论Bucket是什么权限，该Object都可以被身份验证访问和匿名访问。
    */
    public static void setObjectAcl(OSSClient client, String bucketName, String key, CannedAccessControlList[] acls) {
        // 初始化OSSClient
        // OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // OSSClient client = getOSSClient();

        /*
        CannedAccessControlList[] ACLS = {
            CannedAccessControlList.Private,
            CannedAccessControlList.PublicRead,
            CannedAccessControlList.PublicReadWrite,
            CannedAccessControlList.Default
        };
        */
        //设置Object ACL
        for (CannedAccessControlList acl : acls) {
            client.setObjectAcl(bucketName, key, acl);
        }
    }

    /**
     * 设定Object的ACL
     *
     * @param bucketName
     * @param key
     * @param acl
     */
    public static void setObjectAcl(OSSClient client, String bucketName, String key, CannedAccessControlList acl) {
        // 初始化OSSClient
        // OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // OSSClient client = getOSSClient();

        /*
        CannedAccessControlList[] ACLS = {
            CannedAccessControlList.Private,
            CannedAccessControlList.PublicRead,
            CannedAccessControlList.PublicReadWrite,
            CannedAccessControlList.Default
        };
        */

        //设置Object ACL
        client.setObjectAcl(bucketName, key, acl);
    }

    // 获取Object ACL
    public static ObjectAcl getObjectAcl(OSSClient client, String bucketName, String key) {
        // 初始化OSSClient
        // OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // OSSClient client = getOSSClient();

        //读取Object ACL
        ObjectAcl returnedAcl = client.getObjectAcl(bucketName, key);
        logger.info(returnedAcl.getPermission().toString());

        return returnedAcl;
    }

    /**
     * 列出所有Object
     * 当您完成一系列上传后，可能需要查看某个Bucket中有哪些Object。
     * listObjects方法会返回ObjectListing对象，ObjectListing对象包含了此次listObject请求的返回结果。其中我
     * 们可以通过ObjetListing中的getObjectSummaries方法获取所有Object的描述信息。
     *
     * @param bucketName
     */
    public static ObjectListing listObjects(OSSClient client, String bucketName, String path) {
        // 初始化OSSClient
        // OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // OSSClient client = getOSSClient();

        // 构造ListObjectsRequest请求
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);

        /*
        设置参数
        Delimiter 用于对Object名字进行分组的字符。所有名字包含指定的前缀且第一次出现Delimiter字符之间的object作为一组元素: CommonPrefixes。
        Marker 设定结果从Marker之后按字母排序的第一个开始返回。
        MaxKeys 限定此次返回object的最大数，如果不设定，默认为100，MaxKeys取值不能大于1000。
        Prefix 限定返回的object key必须以Prefix作为前缀。注意使用prefix查询时，返回的key中仍会包含Prefix。
        文件夹功能模拟
        我们可以通过 Delimiter 和 Prefix 参数的配合模拟出文件夹功能。Delimiter 和 Prefix 的组合效果是这样的：
        如果把 Prefix 设为某个文件夹名，就可以罗列以此 Prefix 开头的文件，即该文件夹下递归的所有的文件和子文件夹。
        如果再把 Delimiter 设置为 “/” 时，返回值就只罗列该文件夹下的文件，该文件夹下的子文件夹返回在 CommonPrefixes 部分，子文件夹下递归的文件和文件夹不被显示.
        假设Bucket中有4个文件： oss.jpg， fun/test.jpg ， fun/movie/001.avi ， fun/movie/007.avi ，我们把 “/” 符号作为文件夹的分隔符。
        */
        // "/" 为文件夹的分隔符
        listObjectsRequest.setDelimiter("/");
        // listObjectsRequest.setMarker("123");
        // 递归列出fun目录下的所有文件
        listObjectsRequest.setPrefix(path + "/");

        // 获取指定bucket下的所有Object信息
        // ObjectListing listing = client.listObjects(bucketName);
        ObjectListing listing = client.listObjects(listObjectsRequest);

        /*
        // 遍历所有Object
        // listObjects方法会返回 ObjectListing 对象，ObjectListing 对象包含了此次listObject请求的返回结果。
        // 其中我们可以通过 ObjetListing 中的 getObjectSummaries 方法获取所有Object的描述信息（List<OSSObjectSummary>）。
        // NOTE：默认情况下，如果Bucket中的Object数量大于100，则只会返回100个Object， 且返回结果中IsTruncated 为 true，并返回 NextMarker 作为下此读取的起点。
        // 若想增大返回Object数目，可以修改MaxKeys 参数，或者使用 Marker 参数分次读取。
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            logger.info(objectSummary.getKey());
        }

        // 遍历所有CommonPrefix
        System.out.println("CommonPrefixs:");
        for (String commonPrefix : listing.getCommonPrefixes()) {
            logger.info(commonPrefix);
        }
        */
        return listing;
    }

    /**
     * 创建文件夹 Create folder
     *
     * @param newFolder
     * @throws IOException
     */
    public static void createFolder(OSSClient client, String bucketName, String newFolder) throws IOException {
        // Constructs a client instance with your account for accessing OSS
        // OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // OSSClient client = getOSSClient();

        // Create an empty folder without request body, note that the key must be suffixed with a slash
        // final String keySuffixWithSlash = "MyObjectKey";
        client.putObject(bucketName, newFolder + "/", new ByteArrayInputStream(new byte[0]));
        logger.info("Creating an empty folder " + newFolder + "\n");

        // Verify whether the size of the empty folder is zero
        // OSSObject object = client.getObject(bucketName, newFolder + "/");
        // System.out.println("Size of the empty folder '" + object.getKey() + "' is " + object.getObjectMetadata().getContentLength());
        // object.getObjectContent().close();
    }

    /**
     * 上传文件
     *
     * @param bucketName
     * @param key
     * @param filePath
     */
    public static void putObject(OSSClient client, String bucketName, String key, String filePath) {
        // Constructs a client instance with your account for accessing OSS
        // OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // OSSClient client = getOSSClient();

        // 获取指定文件的输入流
        File file = new File(filePath);
        // InputStream content = new FileInputStream(file);

        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // 用户自定义元信息,设置自定义元信息name的值为my-data
        // 当用户下载此Object的时候，此元信息也可以一并得到。一个Object可以有多个类似的参数，但所有的user meta总大小不能超过2KB。
        // NOTE：user meta的名称大小写不敏感，比如用户上传object时，定义名字为“Name”的meta,在表头中存储的参数为：“x-oss-meta-name”,所以读取时读取名字为“name”的参数即可。但如果存入参数为“name”,读取时使用“Name”读取不到对应信息，会返回“Null”
        // meta.addUserMetadata("name", "my-data");
        // 必须设置ContentLength
        meta.setContentLength(file.length());
        // 当无法确认上传内容的长度时（比如SocketStream作为上传的数据源,边接受边上传，直至Socket关闭为止），需要采用chunked编码。
        // 如果不设置content-length, 默认为chunked编码。
        // // 设置1小时后过期
        // Date expire = new Date(new Date().getTime() + 3600 * 1000);
        // meta.setExpirationTime(expire);

        // Note that there are two ways of uploading an object to your bucket, the one by specifying an input stream as content source, the other by specifying a file.
        // Upload an object to your bucket from an input stream
        logger.info("Uploading a new object to OSS from an input stream\n");
        // 上传Object.
        PutObjectResult result = client.putObject(bucketName, key, file, meta);
        // PutObjectResult result = client.putObject(bucketName, key, content, meta);
        // String content = "Thank you for using Aliyun Object Storage Service";
        // client.putObject(bucketName, key, new ByteArrayInputStream(content.getBytes()));

        // 打印ETag
        // System.out.println(result.getETag());

        // Upload an object to your bucket from a file
        // System.out.println("Uploading a new object to OSS from a file\n");
        // client.putObject(new PutObjectRequest(bucketName, key, file));
    }

    /**
     * 上传文件
     *
     * @param bucketName
     * @param key
     * @param inputStream
     */
    public static void putObject(OSSClient client, String bucketName, String key, InputStream inputStream) {

        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // 用户自定义元信息,设置自定义元信息name的值为my-data
        // 当用户下载此Object的时候，此元信息也可以一并得到。一个Object可以有多个类似的参数，但所有的user meta总大小不能超过2KB。
        // NOTE：user meta的名称大小写不敏感，比如用户上传object时，定义名字为“Name”的meta,在表头中存储的参数为：“x-oss-meta-name”,所以读取时读取名字为“name”的参数即可。但如果存入参数为“name”,读取时使用“Name”读取不到对应信息，会返回“Null”
        // meta.addUserMetadata("name", "my-data");
        // 必须设置ContentLength
        // meta.setContentLength(file.length());
        // 当无法确认上传内容的长度时（比如SocketStream作为上传的数据源,边接受边上传，直至Socket关闭为止），需要采用chunked编码。
        // 如果不设置content-length, 默认为chunked编码。
        // // 设置1小时后过期
        // Date expire = new Date(new Date().getTime() + 3600 * 1000);
        // meta.setExpirationTime(expire);

        // Note that there are two ways of uploading an object to your bucket, the one by specifying an input stream as content source, the other by specifying a file.
        // Upload an object to your bucket from an input stream
        logger.info("Uploading a new object to OSS from an input stream\n");
        // 上传Object.
        PutObjectResult result = client.putObject(bucketName, key, inputStream, meta);
        // PutObjectResult result = client.putObject(bucketName, key, content, meta);
        // String content = "Thank you for using Aliyun Object Storage Service";
        // client.putObject(bucketName, key, new ByteArrayInputStream(content.getBytes()));

        // 打印ETag
        // System.out.println(result.getETag());

        // Upload an object to your bucket from a file
        // System.out.println("Uploading a new object to OSS from a file\n");
        // client.putObject(new PutObjectRequest(bucketName, key, file));
    }

    /**
     * Download file
     *
     * @param key
     * @param path
     */
    /*
    public static void getObject(OSSClient client, String key, String disPath) {
        // Constructs a client instance with your account for accessing OSS
        // OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // OSSClient client = getOSSClient();

        // Download an object from your bucket
        logger.info("Downloading an object");

        // 获取Object，返回结果为OSSObject对象
        // OSSObject object = client.getObject(bucketName, key);
        ObjectMetadata objectMetadata = client.getObject(new GetObjectRequest(bucketName, key), new File(disPath));

        // 只获取ObjectMetadata
        // ObjectMetadata objectMetadata = client.getObjectMetadata(bucketName, key);
        // 获取ObjectMeta
        // System.out.println("Content-Type: "  + objectMetadata.getContentType());

        // 获取Object的输入流
        // objectContent = objectMetadata.getObjectContent();
        // displayTextInputStream(objectContent);
        // System.out.println("Done!");
    }
    */


    /**
     * Download file
     *
     * @param key
     */
    public static InputStream getObject(OSSClient client, String bucketName, String key) {

        // Constructs a client instance with your account for accessing OSS
        // OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // OSSClient client = getOSSClient();

        InputStream inputStream = null;

        // Download an object from your bucket
        logger.info("Downloading an object");
        OSSObject object = client.getObject(new GetObjectRequest(bucketName, key));
        // System.out.println("Content-Type: " + object.getObjectMetadata().getContentType());
        inputStream = object.getObjectContent();

        return inputStream;
    }

    /**
     * 删除Object
     *
     * @param bucketName
     * @param key
     */
    public static void deleteObject(OSSClient client, String bucketName, String key) {
        // 初始化OSSClient
        // OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // OSSClient client = getOSSClient();

        // 删除Object
        client.deleteObject(bucketName, key);
    }

    /**
     * 拷贝Object
     * copyObject 方法返回一个 CopyObjectResult 对象，对象中包含了新Object的ETag和修改时间。使用该方法copy的object必须小于1G，否则会报错。若object大于1G，使用后文的Upload Part Copy
     *
     * @param srcBucketName
     * @param srcKey
     * @param destBucketName
     * @param destKey
     */
    public static void copyObject(OSSClient client, String srcBucketName, String srcKey, String destBucketName, String destKey) {
        // 初始化OSSClient
        // OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // OSSClient client = getOSSClient();

        // 拷贝Object
        client.copyObject(srcBucketName, srcKey, destBucketName, destKey);
        // CopyObjectResult result = client.copyObject(srcBucketName, srcKey, destBucketName, destKey);
        // 打印结果
        // System.out.println("ETag: " + result.getETag() + " LastModified: " + result.getLastModified());

        // TODO 通过CopyObjectRequest拷贝Object
        /*
        // CopyObjectRequest 允许用户修改目的Object的ObjectMeta，同时也提供 ModifiedSinceConstraint ，UnmodifiedSinceConstraint ，MatchingETagConstraints ， NonmatchingEtagConstraints 四个参数的设定， 用法与 GetObjectRequest 的参数相似，参见 GetObjectRequest的可设置参数。
        // NOTE：可以通过拷贝操作来实现修改已有 Object 的 meta 信息。如果拷贝操作的源Object地址和目标Object地址相同，则无论 x-oss -metadata -directive 为何值，都会直接替换源Object的meta信息
        // 创建CopyObjectRequest对象
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(srcBucketName, srcKey, destBucketName, destKey);
        // 设置新的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType("text/html");
        copyObjectRequest.setNewObjectMetadata(meta);
        // 复制Object
        CopyObjectResult result = client.copyObject(copyObjectRequest);
        System.out.println("ETag: " + result.getETag() + " LastModified: " + result.getLastModified());
        */
    }

    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            // System.out.println("\t" + line);
        }
        // System.out.println();

        reader.close();
    }

    private static File createSampleFile() throws IOException {
        File file = File.createTempFile("oss-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("0123456789011234567890\n");
        writer.close();

        return file;
    }

    public void multipartUpload(OSSClient client, String bucketName, String key, File localFilePath) throws IOException {

        // Claim a upload id firstly
        String uploadId = claimUploadId(client, bucketName, key);
        logger.debug("Claiming a new upload id " + uploadId + "\n");

        // Calculate how many parts to be divided 5MB
        final long partSize = 5 * 1024 * 1024L;
        long fileLength = localFilePath.length();
        int partCount = (int) (fileLength / partSize);
        if (fileLength % partSize != 0) {
            partCount++;
        }
        if (partCount > 10000) {
            throw new RuntimeException("Total parts count should not exceed 10000");
        } else {
            logger.debug("Total parts count " + partCount + "\n");
        }

        // Upload multiparts to your bucket
        logger.debug("Begin to upload multiparts to OSS from a file\n");
        for (int i = 0; i < partCount; i++) {
            long startPos = i * partSize;
            long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
            executorService.execute(new PartUploader(client, bucketName, key, localFilePath, startPos, curPartSize, i + 1, uploadId));
        }

        // Waiting for all parts finished
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                executorService.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Verify whether all parts are finished
        if (partETags.size() != partCount) {
            throw new IllegalStateException("Upload multiparts fail due to some parts are not finished yet");
        } else {
            logger.debug("Succeed to complete multiparts into an object named " + key + "\n");
        }

        // View all parts uploaded recently
        // listAllParts(client, bucketName, key, uploadId);

        // Complete to upload multiparts
        completeMultipartUpload(client, bucketName, key, uploadId);
    }

    private static String claimUploadId(OSSClient client, String bucketName, String key) {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
        InitiateMultipartUploadResult result = client.initiateMultipartUpload(request);
        return result.getUploadId();
    }

    private void completeMultipartUpload(OSSClient client, String bucketName, String key, String uploadId) {
        // Make part numbers in ascending order
        Collections.sort(partETags, new Comparator<PartETag>() {

            @Override
            public int compare(PartETag p1, PartETag p2) {
                return p1.getPartNumber() - p2.getPartNumber();
            }
        });

        logger.debug("Completing to upload multiparts\n");
        CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(bucketName, key, uploadId, partETags);
        client.completeMultipartUpload(completeMultipartUploadRequest);
    }

    private void listAllParts(OSSClient client, String bucketName, String key, String uploadId) {
        logger.debug("Listing all parts......");
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, key, uploadId);
        PartListing partListing = client.listParts(listPartsRequest);

        int partCount = partListing.getParts().size();
        for (int i = 0; i < partCount; i++) {
            PartSummary partSummary = partListing.getParts().get(i);
            logger.debug("\tPart#" + partSummary.getPartNumber() + ", ETag=" + partSummary.getETag());
        }
    }

    private class PartUploader implements Runnable {

        private OSSClient client;
        private String bucketName;
        private String key;

        private File localFile;
        private long startPos;

        private long partSize;
        private int partNumber;
        private String uploadId;

        public PartUploader(OSSClient client, String bucketName, String key, File localFile, long startPos, long partSize, int partNumber, String uploadId) {
            this.localFile = localFile;
            this.startPos = startPos;
            this.partSize = partSize;
            this.partNumber = partNumber;
            this.uploadId = uploadId;

            this.client = client;
            this.bucketName = bucketName;
            this.key = key;
        }

        @Override
        public void run() {
            InputStream instream = null;
            try {
                instream = new FileInputStream(this.localFile);
                instream.skip(this.startPos);

                UploadPartRequest uploadPartRequest = new UploadPartRequest();
                uploadPartRequest.setBucketName(bucketName);
                uploadPartRequest.setKey(key);
                uploadPartRequest.setUploadId(this.uploadId);
                uploadPartRequest.setInputStream(instream);
                uploadPartRequest.setPartSize(this.partSize);
                uploadPartRequest.setPartNumber(this.partNumber);

                UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);
                System.out.println("Part#" + this.partNumber + " done\n");
                synchronized (partETags) {
                    partETags.add(uploadPartResult.getPartETag());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (instream != null) {
                    try {
                        instream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public static void main(String[] agrs) {

        /*--------------------------------------------------------------------------------------------------------------
        异常
        OSS Java SDK 中有两种异常 ClientException 以及 OSSException ， 他们都继承自或者间接继承自RuntimeException。
        ClientException
        ClientException指SDK内部出现的异常，比如未设置BucketName，网络无法到达等等。

        OSSException
        OSSException指服务器端错误，它来自于对服务器错误信息的解析。OSSException一般有以下几个成员：
        Code： OSS返回给用户的错误码。
        Message： OSS给出的详细错误信息。
        RequestId： 用于唯一标识该次请求的UUID；当您无法解决问题时，可以凭这个RequestId来请求OSS开发工程师的帮助。
        HostId： 用于标识访问的OSS集群（目前统一为oss.aliyuncs.com）

        下面是OSS中常见的异常：
        错误码/描述
        AccessDenied 拒绝访问
        BucketAlreadyExists Bucket已经存在
        BucketNotEmpty Bucket不为空
        EntityTooLarge 实体过大
        EntityTooSmall 实体过小
        FileGroupTooLarge 文件组过大
        FilePartNotExist 文件Part不存在
        FilePartStale 文件Part过时
        InvalidArgument 参数格式错误
        InvalidAccessKeyId AccessKeyId不存在
        InvalidBucketName 无效的Bucket名字
        InvalidDigest 无效的摘要
        InvalidObjectName 无效的Object名字
        InvalidPart 无效的Part
        InvalidPartOrder 无效的part顺序
        InvalidTargetBucketForLogging Logging操作中有无效的目标bucket
        InternalError OSS内部发生错误
        MalformedXML XML格式非法
        MethodNotAllowed 不支持的方法
        MissingArgument 缺少参数
        MissingContentLength 缺少内容长度
        NoSuchBucket Bucket不存在
        NoSuchKey 文件不存在
        NoSuchUpload Multipart Upload ID不存在
        NotImplemented 无法处理的方法
        PreconditionFailed 预处理错误
        RequestTimeTooSkewed 发起请求的时间和服务器时间超出15分钟
        RequestTimeout 请求超时
        SignatureDoesNotMatch 签名错误
        TooManyBuckets 用户的Bucket数目超过限制
        --------------------------------------------------------------------------------------------------------------*/

        OSSClient client = null;
        try {
            client = getOSSClient();

            // System.out.println(AliyunOSSFileStorageHelper.getBucketLocation(client, bucketName));

            // AliyunOSSFileStorageHelper.createBucket(client, "resources");

            // AliyunOSSFileStorageHelper.createFolder(client, bucketName, "test");

            AliyunOSSClientHelper.putObject(client, bucketName, "identify.config", "D:\\identify.config");

            // AliyunOSSFileStorageHelper.deleteObject(client, bucketName, "test/identify.config");

            // AliyunOSSFileStorageHelper.copyObject(client, bucketName, "test/identify.config", bucketName, "test/identify.config.bak");

            // AliyunOSSFileStorageHelper.getObject(client, bucketName, "test/identify.config");

            // AliyunOSSFileStorageHelper.displayTextInputStream(AliyunOSSFileStorageHelper.getObject(client, bucketName, "test/identify.config"));

            /*
            ObjectListing listing = AliyunOSSFileStorageHelper.listObjects(client, bucketName, "test");
            for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
                System.out.println(objectSummary.getKey());
            }
            for (String commonPrefix : listing.getCommonPrefixes()) {
                System.out.println(commonPrefix);
            }
            */


        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code: " + oe.getErrorCode());
            System.out.println("Request ID: " + oe.getRequestId());
            System.out.println("Host ID: " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered a serious internal problem while trying to communicate with OSS, such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } catch (Exception e) {
            System.out.println("Error Message: " + e.getMessage());
        } finally {
            if (client != null) {
                // Do not forget to shut down the client finally to release all allocated resources.
                client.shutdown();
            }
        }
    }
}
