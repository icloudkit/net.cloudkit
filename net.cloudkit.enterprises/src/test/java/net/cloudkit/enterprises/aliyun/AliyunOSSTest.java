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

package net.cloudkit.enterprises.aliyun;//package net.cloudkit.workbench;
//
//import com.aliyun.oss.ClientConfiguration;
//import com.aliyun.oss.OSSClient;
//import com.aliyun.oss.model.*;
//
//import java.io.*;
//import java.util.List;
//
///**
// * AliyunOSSTest.java
// *
// * @author hongquanli <hongquanli@qq.com>
// * @version 1.0 2015/11/30 14:20
// */
//public class AliyunOSSTest {
//
//
//
//    private String accessKeyId = "<key>";
//    private String accessKeySecret = "<secret>";
//
//    // 以杭州为例,如果想使用HTTPS协议，endpoint以https://开头即可。
//    private String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
//
//    public static void main(String[] args) {
//
//    }
//
//    public OSSClient getOSSClient(){
//        // 让客户端使用代理访问OSS服务
//
//        // 创建ClientConfiguration实例
//        ClientConfiguration conf = new ClientConfiguration();
//        // 配置代理为本地8080端口
//        conf.setProxyHost("127.0.0.1");
//        conf.setProxyPort(8080);
//
//        //设置用户名和密码
//        conf.setProxyUsername("username");
//        conf.setProxyPassword("password");
//
//        // 设置网络参数
//        // 设置HTTP最大连接数为10
//        conf.setMaxConnections(10);
//        // 设置TCP连接超时为5000毫秒
//        conf.setConnectionTimeout(5000);
//        // 设置最大的重试次数为3
//        conf.setMaxErrorRetry(3);
//        // 设置Socket传输数据超时的时间为2000毫秒
//        conf.setSocketTimeout(2000);
//
//        // 通过ClientConfiguration可以设置的参数有
//        // UserAgent 用户代理，指HTTP的User-Agent头。默认为”aliyun-sdk-java”
//        // ProxyHost 代理服务器主机地址
//        // ProxyPort 代理服务器端口
//        // ProxyUsername 代理服务器验证的用户名
//        // ProxyPassword 代理服务器验证的密码
//        // ProxyDomain 访问NTLM验证的代理服务器的Windows域名
//        // ProxyWorkstation NTLM代理服务器的Windows工作站名称
//        // MaxConnections 允许打开的最大HTTP连接数。默认为1024
//        // SocketTimeout 打开连接传输数据的超时时间（单位：毫秒）。默 认为50000毫秒
//        // ConnectionTimeout 建立连接的超时时间（单位：毫秒）。默认为 50000毫秒
//        // MaxErrorRetry 可重试的请求失败后最大的重试次数。默认为3次
//
//        // 创建OSS客户端
//        OSSClient client = new OSSClient(endpoint, accessKeySecret, accessKeySecret, conf);
//        // 使用CNAME进行访问,比如你的域名"http://cname.com"CNAME指向你的bucket域名"mybucket.oss-cn-hangzhou.aliyuncs.com"
//        // 用户只需要在创建OSSClinet类实例时，将原本填入该bucket的endpoint更换成CNAME后的域名即可。同时需要注意的是，使用该OSSClinet实例的后续操作中，bucket的名称只能填成被指向的bucket名称。
//        // 如果是专有云用户，希望使用非aliyuncs.com结尾的域名访问OSS。可以通过ClientConfiguration中setCnameExcludeList来设置endpoint来避免使用cname方式访问OSS。
//        // OSSClient client = new OSSClient("http://cname.com/", /* accessKeyId */, /* accessKeySecret */);
//
//        return client;
//    }
//
//    /**
//     * 新建bucket
//     * Bucket是OSS全局命名空间，相当于数据的容器，可以存储若干Object。
//     * 关于Bucket的命名规范，参见Bucket中的命名规范。
//     *
//     * @param bucketName
//     */
//    public void createBucket(String bucketName) {
//        // 初始化OSSClient
//        OSSClient client = new OSSClient(endpoint,accessKeyId, accessKeySecret);
//        // 新建一个Bucket
//        client.createBucket(bucketName);
//    }
//
//    /**
//     * 列出用户所有的Bucket
//     */
//    public void listAllBucket() {
//        // 初始化OSSClient
//        OSSClient client = new OSSClient(endpoint,accessKeyId, accessKeySecret);
//        // 获取用户的Bucket列表
//        List<Bucket> buckets = client.listBuckets();
//        // 遍历Bucket
//        for (Bucket bucket : buckets) {
//            System.out.println(bucket.getName());
//        }
//    }
//
//    /**
//     * 判断Bucket是否存在
//     *
//     * @param bucketName
//     */
//    public void isExistsBucket(String bucketName) {
//        // 初始化OSSClient
//        OSSClient client = new OSSClient(endpoint,accessKeyId, accessKeySecret);
//        // String bucketName = "your-bucket-name";
//        // 获取Bucket的存在信息
//        boolean exists = client.doesBucketExist(bucketName);
//        // 输出结果
//        if (exists) {
//            System.out.println("Bucket exists");
//        } else {
//            System.out.println("Bucket not exists");
//        }
//    }
//
//    /**
//     * 设置Bucket ACL
//     *
//     * @param bucketName
//     */
//    public void setBucketAcl(String bucketName) {
//        // 初始化OSSClient
//        OSSClient client = new OSSClient(endpoint,accessKeyId, accessKeySecret);
//        // String bucketName = "your-bucket-name";
//        // 以设置为私有权限举例
//        client.setBucketAcl(bucketName,CannedAccessControlList.Private);
//    }
//
//    /**
//     * 获取Bucket ACL
//     *
//     * @param bucketName
//     */
//    public void getBucketAcl(String bucketName) {
//        // 初始化OSSClient
//        OSSClient client = new OSSClient(endpoint,accessKeyId, accessKeySecret);
//        // String bucketName = "your-bucket-name";
//        AccessControlList accessControlList = client.getBucketAcl(bucketName);
//        //可以打印出来看结果,也可以从控制台确认
//        System.out.println(accessControlList.toString());
//    }
//
//    /**
//     * 获取Bucket地址
//     *
//     * @param bucketName
//     */
//    public void getBucketLocation(String bucketName) {
//        // 初始化OSSClient
//        OSSClient client = new OSSClient(endpoint,accessKeyId, accessKeySecret);
//        // String bucketName = "your-bucket-name";
//        // 获取 bucket 地址
//        String location = client.getBucketLocation(bucketName);
//        System.out.println(location);
//    }
//
//    /**
//     * 删除Bucket
//     * 如果Bucket不为空（Bucket中有Object），则Bucket无法删除，必须删除Bucket中的所有Object后，Bucket才能成功删除。
//     *
//     * @param bucketName
//     */
//    public void deleteBucket(String bucketName) {
//        // 初始化OSSClient
//        OSSClient client = new OSSClient(endpoint,accessKeyId, accessKeySecret);
//        // String bucketName = "your-bucket-name";
//        // 删除Bucket
//        client.deleteBucket(bucketName);
//    }
//
//    /**
//     * 上传Object
//     *
//     * Object是OSS中最基本的数据单元，您可以把它简单地理解为文件，用下面代码可以实现一个Object的上传。
//     * Object通过InputStream的形式上传到OSS中。在上面的例子里我们可以看出，每个上传的Object，都需要指
//     * 定和此Object关联的ObjectMetadata。ObjectMetaData是用户对该object的描述，由一系列name-value对
//     * 组成；其中ContentLength是必须设置的，以便SDK可以正确识别上传Object的大小。为了保证上传文件服务
//     * 器端与本地一致，用户可以设置ContentMD5，OSS会计算上传数据的MD5值并与用户上传的MD5值比较
//     * ，如果不一致返回InvalidDigest错误码。关于Object的命名规范，参见Object中的命名规范 。关于上传
//     * Object更详细的信息，参见Object中的上传Object 。
//     *
//     * 在OSS中，用户操作的基本数据单元是Object。单个Object最大允许大小根据上传数据方式不同而不同,Put
//     * Object方式最大不能超过5GB, 使用multipart上传方式object大小不能超过48.8TB。Object包含key、meta和
//     * data。其中，key是Object的名字；meta是用户对该object的描述，由一系列name-value对组成；data是
//     * Object的数据。
//     *
//     * 命名规范
//     * Object的命名规范如下：
//     * 使用UTF-8编码
//     * 长度必须在1-1023字节之间
//     * 不能以“/”或者“\”字符开头
//     * 不能含有“\r”或者“\n”的换行符
//     *
//     * Object通过InputStream的形式上传到OSS中。在上面的例子里我们可以看出，每上传一个Object，都需要指
//     * 定和Object关联的ObjectMetadata。ObjectMetaData是用户对该object的描述，由一系列name-value对组
//     * 成；其中ContentLength是必须设置的，以便SDK可以正确识别上传Object的大小。 为了保证上传文件服务器
//     * 端与本地一致，用户可以设置ContentMD5，OSS会计算上传数据的MD5值并与用户上传的MD5值比较，如
//     * 果不一致返回InvalidDigest错误码。
//     *
//     * @param bucketName
//     * @param key
//     * @param filePath
//     * @throws FileNotFoundException
//     */
//    public void putObject(String bucketName, String key, String filePath) throws FileNotFoundException {
//        // 初始化OSSClient
//        OSSClient client = new OSSClient(endpoint,accessKeyId, accessKeySecret);
//        // 获取指定文件的输入流
//        File file = new File(filePath);
//        InputStream content = new FileInputStream(file);
//        // 创建上传Object的Metadata
//        ObjectMetadata meta = new ObjectMetadata();
//        // 用户自定义元信息,设置自定义元信息name的值为my-data
//        // 当用户下载此Object的时候，此元信息也可以一并得到。一个Object可以有多个类似的参数，但所有的user meta总大小不能超过2KB。
//        // NOTE：user meta的名称大小写不敏感，比如用户上传object时，定义名字为“Name”的meta,在表头中存储的参数为：“x-oss-meta-name”,所以读取时读取名字为“name”的参数即可。但如果存入参数为“name”,读取时使用“Name”读取不到对应信息，会返回“Null”
//        meta.addUserMetadata("name", "my-data");
//
//        // 必须设置ContentLength
//        meta.setContentLength(file.length());
//
//        // 当无法确认上传内容的长度时（比如SocketStream作为上传的数据源,边接受边上传，直至Socket关闭为止），需要采用chunked编码。
//        // 如果不设置content-length, 默认为chunked编码。
//
//        // 上传Object.
//        PutObjectResult result = client.putObject(bucketName, key, content, meta);
//        // 打印ETag
//        System.out.println(result.getETag());
//    }
//
//    // TODO 追加上传
//    // 用户使用Append方式上传，关键得对Position这个参数进行正确的设置。当用户创建一个Appendable
//    // Object时，追加位置设为0。当对Appendable Object进行内容追加时，追加位置设为Object当前长度。有两
//    // 种方式获取该Object长度：一种是通过上传追加后的返回内容获取，如上述代码。另一种是通过下面要讲到的
//    // getObjectMetadata获取该Object的当前长度。
//    //
//    // 对于Object meta的设置只在使用追加上传创建Object时设置。后续如果需要更改该Object meta，可以
//    // 通过下面要讲到的copy object接口实现（源和目的为同一个Object）。
//    //
//    // 创建OSSClient实例
//    //OSSClient client = new OSSClient(ENDPOINT, ACCESS_ID, ACCESS_KEY);
//    //// 发起首次追加Object请求，注意首次追加需要设置追加位置为0
//    //final String fileToAppend = "<file to append at first time>";
//    //AppendObjectRequest appendObjectRequest = new AppendObjectRequest(bucketName, key, new File(fileToAppend));
//    //// 设置content-type，注意设置object meta只能在使用Append创建Object设置
//    //ObjectMetadata meta = new ObjectMetadata();
//    //meta.setContentType("image/jpeg");
//    //appendObjectRequest.setMetadata(meta);
//    //// 设置追加位置为0,发送追加Object请求
//    //appendObjectRequest.setPosition(0L);
//    //AppendObjectResult appendObjectResult = client.appendObject(appendObjectRequest);
//    //// 发起第二次追加Object请求，追加位置为第一次追加后的Object长度
//    //final String fileToAppend2 = "<file to append at second time>";
//    //appendObjectRequest = new AppendObjectRequest(bucketName, key, new File(fileToAppend2));
//    //// 设置追加位置为前一次追加文件的大小,发送追加Object请求
//    //appendObjectRequest.setPosition(appendObjectResult.getNextPosition());
//    //appendObjectResult = client.appendObject(appendObjectRequest);
//    //OSSObject o = client.getObject(bucketName, key);
//    //// 当前该Object的大小为两次追加文件的大小总和
//    //System.out.println(o.getObjectMetadata().getContentLength());
//    //// 下一个追加位置为前两次追加文件的大小总和
//    //System.err.println(appendObjectResult.getNextPosition().longValue());
//
//    // TODO 分块上传
//
//
//    /**
//     * 创建模拟文件夹
//     *
//     * OSS服务是没有文件夹这个概念的，所有元素都是以Object来存储。
//     * 创建模拟文件夹本质上来说是创建了一个size为0的object。对于这个object照样可以上传下载,只是控制台会对
//     * 以"/"结尾的Object以文件夹的方式展示。所以用户可以使用上述方式来实现创建模拟文件夹。而对文件夹的访
//     * 问可以参看文件夹模拟功能
//     */
//    public void createSimulationFolder()throws IOException {
//        String bucketName = "your-bucket-name";
//        // 要创建的文件夹名称,在满足Object命名规则的情况下以"/"结尾
//        String objectName = "folder_name/";
//        // 初始化OSSClient
//        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//        ObjectMetadata objectMeta = new ObjectMetadata();
//        /*这里的size为0,注意OSS本身没有文件夹的概念,这里创建的文件夹本质上是一个size为0的Object,dataStream仍然可以有数据*/
//        byte[] buffer = new byte[0];
//        ByteArrayInputStream in = new ByteArrayInputStream(buffer);
//        objectMeta.setContentLength(0);
//        try {
//            client.putObject(bucketName, objectName, in, objectMeta);
//        } finally {
//            in.close();
//        }
//    }
//
//    // 设定Object的ACL
//    // Object的ACL
//    // | 权限值               | 中文名称                      | 权限对访问者的限制     |
//    // |---------------------|-----------------------------|---------------------|
//    // | public-read-write   | 公共读写                      | 该ACL表明某个Object是公共读写资源，即所有用户拥有对该Object的读写权限。 |
//    // | public-read         | 公共读，私有写                 | 该ACL表明某个Object是公共读资源，即非Object Owner只有该Object的读权限，而Object Owner拥有该Object的读写权限。 |
//    // | private             | 私有读写                      | 该ACL表明某个Object是私有资源，即只有该Object的Owner拥有该Object的读写权限，其他的用户没有权限操作该Object。 |
//    // | default             | 默认权限                      | 该ACL表明某个Object是遵循Bucket读写权限的资源，即Bucket是什么权限，Object就是什么权限 |
//    // 设置Object ACL注意事项
//    // 如果没有设置Object的权限，即Object的ACL为default，Object的权限和Bucket权限一致。
//    // 如果设置了Object的权限，Object的权限大于Bucket权限。举个例子，如果设置了Object的权限是
//    // public-read，无论Bucket是什么权限，该Object都可以被身份验证访问和匿名访问。
//    public void setObjectAcl(String bucketName) {
//        // 初始化OSSClient
//        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//        CannedAccessControlList[] ACLS = {
//                CannedAccessControlList.Private,
//                CannedAccessControlList.PublicRead,
//                CannedAccessControlList.PublicReadWrite,
//                CannedAccessControlList.Default
//        };
//        // 上传文件
//        final String key = "normal-set-object-acl";
//        // 128KB
//        final long inputStreamLength = 128 * 1024;
//        InputStream instream = genFixedLengthInputStream(inputStreamLength);
//        client.putObject(bucketName, key, instream, null);
//        //设置Object ACL
//        for (CannedAccessControlList acl : ACLS) {
//            client.setObjectAcl(bucketName, key, acl);
//        }
//    }
//
//    // 获取Object ACL
//    public void getObjectAcl(String bucketName, String key){
//        // 初始化OSSClient
//        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//        //读取Object ACL
//        ObjectAcl returnedAcl = client.getObjectAcl(bucketName, key);
//        System.out.println(returnedAcl.getPermission().toString());
//    }
//
//    // 设定Object的Http Header,为Object设置了过期时间,Java SDK支持的Http Header有四种，分别为：Cache-Control 、 Content-Disposition 、Content-Encoding 、 Expires 。它们的相关介绍见 RFC2616(http://www.ietf.org/rfc/rfc2616.txt) 。
//    //// 初始化OSSClient
//    //OSSClient client = ...;
//    //// 初始化上传输入流
//    //InputStream content = ...;
//    //// 创建上传Object的Metadata
//    //ObjectMetadata meta = new ObjectMetadata();
//    //// 设置ContentLength为1000
//    //meta.setContentLength(1000);
//    //// 设置1小时后过期
//    //Date expire = new Date(new Date().getTime() + 3600 * 1000);
//    //meta.setExpirationTime(expire);
//    //client.putObject(bucketName, key, content, meta);
//
//    /**
//     * 列出所有Object
//     * 当您完成一系列上传后，可能需要查看某个Bucket中有哪些Object。
//     * listObjects方法会返回ObjectListing对象，ObjectListing对象包含了此次listObject请求的返回结果。其中我
//     * 们可以通过ObjetListing中的getObjectSummaries方法获取所有Object的描述信息。
//     *
//     * @param bucketName
//     */
//    public void listObjects(String bucketName) {
//        // 初始化OSSClient
//        OSSClient client = new OSSClient(endpoint,accessKeyId, accessKeySecret);
//
//        // 构造ListObjectsRequest请求
//        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
//
//        /*
//        设置参数
//        Delimiter 用于对Object名字进行分组的字符。所有名字包含指定的前缀且第一次出现Delimiter字符之间的object作为一组元素: CommonPrefixes。
//        Marker 设定结果从Marker之后按字母排序的第一个开始返回。
//        MaxKeys 限定此次返回object的最大数，如果不设定，默认为100，MaxKeys取值不能大于1000。
//        Prefix 限定返回的object key必须以Prefix作为前缀。注意使用prefix查询时，返回的key中仍会包含Prefix。
//        文件夹功能模拟
//        我们可以通过 Delimiter 和 Prefix 参数的配合模拟出文件夹功能。Delimiter 和 Prefix 的组合效果是这样的：
//        如果把 Prefix 设为某个文件夹名，就可以罗列以此 Prefix 开头的文件，即该文件夹下递归的所有的文件和子文件夹。
//        如果再把 Delimiter 设置为 “/” 时，返回值就只罗列该文件夹下的文件，该文件夹下的子文件夹返回在 CommonPrefixes 部分，子文件夹下递归的文件和文件夹不被显示.
//        假设Bucket中有4个文件： oss.jpg， fun/test.jpg ， fun/movie/001.avi ， fun/movie/007.avi ，我们把 “/” 符号作为文件夹的分隔符。
//        */
//
//        // "/" 为文件夹的分隔符
//        listObjectsRequest.setDelimiter("/");
//        listObjectsRequest.setMarker("123");
//        // 递归列出fun目录下的所有文件
//        listObjectsRequest.setPrefix("fun/");
//
//        // 获取指定bucket下的所有Object信息
//        // ObjectListing listing = client.listObjects(bucketName);
//        ObjectListing listing = client.listObjects(listObjectsRequest);
//
//        // 遍历所有Object
//        // listObjects方法会返回 ObjectListing 对象，ObjectListing 对象包含了此次listObject请求的返回结果。
//        // 其中我们可以通过 ObjetListing 中的 getObjectSummaries 方法获取所有Object的描述信息（List<OSSObjectSummary>）。
//        // NOTE：默认情况下，如果Bucket中的Object数量大于100，则只会返回100个Object， 且返回结果中IsTruncated 为 true，并返回 NextMarker 作为下此读取的起点。
//        // 若想增大返回Object数目，可以修改MaxKeys 参数，或者使用 Marker 参数分次读取。
//        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
//            System.out.println(objectSummary.getKey());
//        }
//
//        // 遍历所有CommonPrefix
//        System.out.println("CommonPrefixs:");
//        for (String commonPrefix : listing.getCommonPrefixes()) {
//            System.out.println(commonPrefix);
//        }
//    }
//
//    /**
//     * 获取指定Object
//     * 当调用OSSClient的getObject方法时，会返回一个OSSObject的对象，此对象包含了Object的各种信息。通
//     * 过OSSObject的getObjectContent方法，可以获取返回的Object的输入流，通过读取此输入流获取此
//     * Object的内容，在用完之后关闭这个流。
//     *
//     * @param bucketName
//     * @param key
//     * @throws IOException
//     */
//    public void getObject(String bucketName, String key) throws IOException {
//        /*
//        OSSObject 包含了Object的各种信息，包含Object所在的Bucket、Object的名称、Metadata以及一个输入流。
//        我们可以通过操作输入流将Object的内容读取到文件或者内存中。而ObjectMetadata包含了Object上传时定义的，ETag，Http Header以及自定义的元信息。
//         */
//        // 初始化OSSClient
//        OSSClient client = new OSSClient(endpoint,accessKeyId, accessKeySecret);
//        // 获取Object，返回结果为OSSObject对象
//        OSSObject object = client.getObject(bucketName, key);
//        // 获取ObjectMeta
//        ObjectMetadata meta = object.getObjectMetadata();
//        // 获取Object的输入流
//        InputStream objectContent = object.getObjectContent();
//        // TODO 处理Object...
//        // 关闭流
//        objectContent.close();
//
//
//        // TODO 通过GetObjectRequest获取Object
//        /*
//        // GetObjectRequest可以设置以下参数
//        // Range 指定文件传输的范围。
//        // ModifiedSinceConstraint 如果指定的时间早于实际修改时间，则正常传送文件。否则抛出304 Not Modified异常。
//        // UnmodifiedSinceConstraint 如果传入参数中的时间等于或者晚于文件实际修改时间，则正常传输文件。否则抛出412 precondition failed异常
//        // MatchingETagConstraints 传入一组ETag，如果传入期望的ETag和object的ETag匹配，则正常传输文件。否则抛出412 precondition failed异常
//        // NonmatchingEtagConstraints 传入一组ETag，如果传入的ETag值和Object的ETag不匹配，则正常传输文件。否则抛出304 Not Modified异常。
//        // ResponseHeaderOverrides 自定义OSS返回请求中的一些Header。
//
//        // 修改 ResponseHeaderOverrides, 可以自定义OSS的返回Header
//        // ContentType OSS返回请求的content-type头
//        // ContentLanguage OSS返回请求的content-language头
//        // Expires OSS返回请求的expires头
//        // CacheControl OSS返回请求的cache-control头
//        // ContentDisposition OSS返回请求的content-disposition头
//        // ContentEncoding OSS返回请求的content-encoding头
//
//        // 新建GetObjectRequest
//        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
//        // 获取0~100字节范围内的数据
//        getObjectRequest.setRange(0, 100);
//        // 获取Object，返回结果为OSSObject对象
//        OSSObject object = client.getObject(getObjectRequest);
//        */
//
//        // TODO 直接下载Object到文件
//        // 新建GetObjectRequest
//        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
//        // 下载Object到文件
//        ObjectMetadata objectMetadata = client.getObject(getObjectRequest, new File("/path/to/file"));
//        // 只获取ObjectMetadata
//        // ObjectMetadata objectMetadata = client.getObjectMetadata(bucketName, key);
//
//
//    }
//
//    /**
//     * 删除Object
//     *
//     * @param bucketName
//     * @param key
//     */
//    public void deleteObject(String bucketName, String key) {
//        // 初始化OSSClient
//        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//        // 删除Object
//        client.deleteObject(bucketName, key);
//    }
//
//    /**
//     * 拷贝Object
//     * copyObject 方法返回一个 CopyObjectResult 对象，对象中包含了新Object的ETag和修改时间。使用该方法copy的object必须小于1G，否则会报错。若object大于1G，使用后文的Upload Part Copy
//     *
//     * @param srcBucketName
//     * @param srcKey
//     * @param destBucketName
//     * @param destKey
//     */
//    public void copyObject(String srcBucketName, String srcKey, String destBucketName, String destKey) {
//        // 初始化OSSClient
//        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//        // 拷贝Object
//        CopyObjectResult result = client.copyObject(srcBucketName, srcKey, destBucketName, destKey);
//        // 打印结果
//        System.out.println("ETag: " + result.getETag() + " LastModified: " + result.getLastModified());
//
//        // TODO 通过CopyObjectRequest拷贝Object
//        /*
//        // CopyObjectRequest 允许用户修改目的Object的ObjectMeta，同时也提供 ModifiedSinceConstraint ，UnmodifiedSinceConstraint ，MatchingETagConstraints ， NonmatchingEtagConstraints 四个参数的设定， 用法与 GetObjectRequest 的参数相似，参见 GetObjectRequest的可设置参数。
//        // NOTE：可以通过拷贝操作来实现修改已有 Object 的 meta 信息。如果拷贝操作的源Object地址和目标Object地址相同，则无论 x-oss -metadata -directive 为何值，都会直接替换源Object的meta信息
//
//        // 创建CopyObjectRequest对象
//        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(srcBucketName, srcKey, destBucketName, destKey);
//        // 设置新的Metadata
//        ObjectMetadata meta = new ObjectMetadata();
//        meta.setContentType("text/html");
//        copyObjectRequest.setNewObjectMetadata(meta);
//        // 复制Object
//        CopyObjectResult result = client.copyObject(copyObjectRequest);
//        System.out.println("ETag: " + result.getETag() + " LastModified: " + result.getLastModified());
//        */
//    }
//
//
//    /*
//    TODO POST 方式上传文件
//    对于后端服务关键是需要为前端提供policy以及Signature这两个表单域。
//    生成POST Policy
//    Post请求的policy表单域用于验证请求的合法性。 poicy为一段经过UTF-8和base64编码的JSON文本，声明了
//    Post请求必须满足的条件。虽然对于public-read-write的bucket上传时，post表单域为可选项，我们强烈建
//    议使用该域来限制Post请求。详细的policy josn字串生成规则查看API文档Post Object中Post Policy。
//    比如配置如下policy字串：
//    {
//        "expiration":"2015-02-25T14:25:46.000Z",
//        "conditions":[
//            {"bucket":"oss-test2"},
//            ["eq","$key","user/eric/${filename}"],
//            ["starts-with","$key","user/eric"],
//            ["starts-with","$x-oss-meta-tag","dummy_etag"],
//            ["content-length-range",1,1024]
//        ]
//    }
//    可以通过下面的代码生成上述json字串：
//    OSSClient client = new OSSClient(endpoint, accessId, accessKey);
//    Date expiration = DateUtil.parseIso8601Date("2015-02-25T14:25:46.000Z");
//    PolicyConditions policyConds = new PolicyConditions();
//    policyConds.addConditionItem("bucket", bucketName);
//    // 添加精确匹配条件项 “$”必须紧接大括号
//    policyConds.addConditionItem(MatchMode.Exact, PolicyConditions.COND_KEY, "user/eric/\\${filename}");
//    // 添加前缀匹配条件项
//    policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, "user/eric");
//    policyConds.addConditionItem(MatchMode.StartWith, "x-oss-meta-tag", "dummy_etag");
//    // 添加范围匹配条件项
//    policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 1, 1024);
//    // 生成Post Policy字符串
//    String postPolicy = client.generatePostPolicy(expiration, policyConds);
//    System.out.println(postPolicy);
//    // 计算policy Base64编码
//    byte[] binaryData = postPolicy.getBytes("utf-8");
//    String encodedPolicy = BinaryUtil.toBase64String(binaryData);
//    System.out.println(encodedPolicy);
//    在生成policy字串之后，注意的是POST表单中的policy需要进行Base64编码。
//    生成Post Signature
//    同时，为了完成POST上传，需要生成Post Signature来验证请求合法性。可以参考下面代码。
//    //传入Post Policy原json字串，生成postSignature
//    String postSignature = client.calculatePostSignature(postPolicy);
//    System.out.println(postSignature);
//    */
//
//    // TODO Multipart Upload
//    /*
//    可以在如下的应用场景内（但不仅限于此），使用Multipart Upload上传模式，如：
//    需要支持断点上传。
//    上传超过100MB大小的文件。
//    网络条件较差，和OSS的服务器之间的链接经常断开。
//    上传文件之前，无法确定上传文件的大小。
//
//    String bucketName = "your-bucket-name";
//    String key = "your-key";
//    // 初始化OSSClient
//    OSSClient client = ...;
//    // 开始Multipart Upload
//    InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(bucketName, key);
//    InitiateMultipartUploadResult initiateMultipartUploadResult = client.initiateMultipartUpload(initiateMultipartUploadRequest);
//    // 打印UploadId
//    System.out.println("UploadId: " + initiateMultipartUploadResult.getUploadId());
//    */
//
//    /*
//    我们用 InitiateMultipartUploadRequest 来指定上传Object的名字和所属Bucket。在
//    InitiateMultipartUploadRequest 中，您也可以设置 ObjectMetadata ，但是不必指定其中的
//    ContentLength 。 initiateMultipartUpload 的返回结果中含有 UploadId ，它是区分分块上传事件的唯一标
//    识，在后面的操作中，我们将用到它。
//    接下来我们可以有两种方式来传分块，一种是使用Upload Part从本地上传，另外一种是通过Upload Part copy从bucket中的object复制得到。
//    // TODO Upload Part本地上传
//    接着，我们把本地文件分块上传。 假设有一个文件，本地路径为 /path/to/file.zip 由于文件比较大，我们将其
//    分块传输到OSS中。
//    //String bucketName = "your-bucket-name";
//    //String key = "your-key";
//    //// 初始化OSSClient
//    //OSSClient client = ...;
//    //// 开始Multipart Upload
//    //InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(bucketName, key);
//    //InitiateMultipartUploadResult initiateMultipartUploadResult = client.initiateMultipartUpload(initiateMultipartUploadRequest);
//    //// 打印UploadId
//    //System.out.println("UploadId: " + initiateMultipartUploadResult.getUploadId());
//    //// 设置每块为 5M
//    //final int partSize = 1024 * 1024 * 5;
//    //File partFile = new File("/path/to/file.zip");
//    //// 计算分块数目
//    //int partCount = (int) (partFile.length() / partSize);
//    //if (partFile.length() % partSize != 0){
//    //    partCount++;
//    //}
//    //// 新建一个List保存每个分块上传后的ETag和PartNumber
//    //List<PartETag> partETags = new ArrayList<PartETag>();
//    //for(int i = 0; i < partCount; i++){
//    //    // 获取文件流
//    //    FileInputStream fis = new FileInputStream(partFile);
//    //    // 跳到每个分块的开头
//    //    long skipBytes = partSize * i;
//    //    fis.skip(skipBytes);
//    //    // 计算每个分块的大小
//    //    long size = partSize < partFile.length() - skipBytes ? partSize : partFile.length() - skipBytes;
//    //    // 创建UploadPartRequest，上传分块
//    //    UploadPartRequest uploadPartRequest = new UploadPartRequest();
//    //    uploadPartRequest.setBucketName(bucketName);
//    //    uploadPartRequest.setKey(key);
//    //    uploadPartRequest.setUploadId(initiateMultipartUploadResult.getUploadId());
//    //    uploadPartRequest.setInputStream(fis);
//    //    uploadPartRequest.setPartSize(size);
//    //    uploadPartRequest.setPartNumber(i + 1);
//    //    UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);
//    //    // 将返回的PartETag保存到List中。
//    //    partETags.add(uploadPartResult.getPartETag());
//    //    // 关闭文件
//    //    fis.close();
//    //}
//    上面程序的核心是调用 uploadPart 方法来上传每一个分块，但是要注意以下几点：
//    uploadPart 方法要求除最后一个Part以外，其他的Part大小都要大于100KB。但是Upload Part接口并不会立即校验上传Part的大小（因为不知道是否为最后一块）；只有当Complete Multipart Upload的时候才会校验。
//    OSS会将服务器端收到Part数据的MD5值放在ETag头内返回给用户。
//    为了保证数据在网络传输过程中不出现错误，用户上传是可以设置Content-MD5，OSS会计算上传数据的MD5值与用户上传的MD5值比较，如果不一致返回InvalidDigest错误码。
//    Part号码的范围是1~10000。如果超出这个范围，OSS将返回InvalidArgument的错误码。
//    每次上传part时都要把流定位到此次上传块开头所对应的位置。
//    每次上传part之后，OSS的返回结果会包含一个 PartETag 对象，他是上传块的ETag与块编号（PartNumber）的组合，在后续完成分块上传的步骤中会用到它，因此我们需要将其保存起来。一般来讲我们将这些 PartETag 对象保存到List中。
//    */
//
//    // TODO Upload Part本地chunked上传
//    //File file = new File(filePath);
//    //// 设置每块为 5M
//    //final int partSize = 5 * 1024 * 1024;
//    //int fileSize = (int) file.length();
//    //// 计算分块数目
//    //final int partCount = (file.length() % partSize != 0) ? (fileSize / partSize + 1) : (fileSize / partSize);
//    //List<PartETag> partETags = new ArrayList<PartETag>();
//    //for (int i = 0; i < partCount; i++) {
//    //    InputStream fin = new BufferedInputStream(new FileInputStream(file));
//    //    fin.skip(i * partSize);
//    //    int size = (i + 1 == partCount) ? (fileSize - i * partSize) : partSize;
//    //    UploadPartRequest req = new UploadPartRequest();
//    //    req.setBucketName(bucketName);
//    //    req.setKey(key);
//    //    req.setPartNumber(i + 1);
//    //    req.setPartSize(size);
//    //    req.setUploadId(uploadId);
//    //    req.setInputStream(fin);
//    //    // 在UploadPartRequest这个类的实例里面，通过设置setUseChunkEncoding(true)来使得上传为chunked编码。
//    //    req.setUseChunkEncoding(true); // 使用chunked编码
//    //    UploadPartResult result = client.uploadPart(req);
//    //    partETags.add(result.getPartETag());
//    //    fin.close();
//    //}
//
//
//    // TODO Upload Part Copy拷贝上传
//    // Upload Part Copy 通过从一个已经存在的object中拷贝数据来上传一个object。当拷贝一个大于500MB的文件，建议使用Upload Part Copy的方式来进行拷贝。
//    //ObjectMetadata objectMetadata = client.getObjectMetadata(sourceBucketName,sourceKey);
//    //long partSize = 1024 * 1024 * 100;
//    //// 得到被拷贝object大小
//    //long contentLength = objectMetadata.getContentLength();
//    //// 计算分块数目
//    //int partCount = (int) (contentLength / partSize);
//    //if (contentLength % partSize != 0) {
//    //    partCount++;
//    //}
//    //System.out.println("total part count:" + partCount);
//    //List<PartETag> partETags = new ArrayList<PartETag>();
//    //long startTime = System.currentTimeMillis();
//    //for (int i = 0; i < partCount; i++) {
//    //    System.out.println("now begin to copy part:" + (i+1));
//    //    long skipBytes = partSize * i;
//    //    // 计算每个分块的大小
//    //    long size = partSize < contentLength - skipBytes ? partSize : contentLength - skipBytes;
//    //    // 创建UploadPartCopyRequest，上传分块
//    //    UploadPartCopyRequest uploadPartCopyRequest = new UploadPartCopyRequest();
//    //    uploadPartCopyRequest.setSourceKey("/" + sourceBucketName + "/" + sourceKey);
//    //    uploadPartCopyRequest.setBucketName(targetBucketName);
//    //    uploadPartCopyRequest.setKey(targetKey);
//    //    uploadPartCopyRequest.setUploadId(uploadId);
//    //    uploadPartCopyRequest.setPartSize(size);
//    //    uploadPartCopyRequest.setBeginIndex(skipBytes);
//    //    uploadPartCopyRequest.setPartNumber(i + 1);
//    //    UploadPartCopyResult uploadPartCopyResult = client.uploadPartCopy(uploadPartCopyRequest);
//    //    // 将返回的PartETag保存到List中。
//    //    partETags.add(uploadPartCopyResult.getPartETag());
//    //    System.out.println("now end to copy part:" + (i+1));
//    //}
//
//    // 以上程序调用uploadPartCopy 方法来拷贝每一个分块。与UploadPart要求基本一致，需要通过setBeginIndex来定位到此次上传块开头所对应的位置，同时需要通过setSourceKey来指定copy的object
//
//    // 完成分块上传
//    //CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(bucketName,key, initiateMultipartUploadResult.getUploadId(), partETags);
//    //// 完成分块上传
//    //CompleteMultipartUploadResult completeMultipartUploadResult = client.completeMultipartUpload(completeMultipartUploadRequest);
//    //// 打印Object的ETag
//    //System.out.println(completeMultipartUploadResult.getETag());
//
//    /*
//    上面代码中的 partETags 就是进行分块上传中保存的partETag的列表，OSS收到用户提交的Part列表后，会逐
//    一验证每个数据Part的有效性。当所有的数据Part验证通过后，OSS会将这些part组合成一个完整的Object。
//    取消分块上传事件
//    我们可以用 abortMultipartUpload 方法取消分块上传。
//    AbortMultipartUploadRequest abortMultipartUploadRequest = new AbortMultipartUploadRequest(bucketName, key, uploadId);
//    // 取消分块上传
//    client.abortMultipartUpload(abortMultipartUploadRequest);
//    获取Bucket内所有分块上传事件
//    // 获取Bucket内所有上传事件
//    ListMultipartUploadsRequest listMultipartUploadsRequest=new ListMultipartUploadsRequest(bucketName);
//    MultipartUploadListing listing = client.listMultipartUploads(listMultipartUploadsRequest);
//    // 遍历所有上传事件
//    for (MultipartUpload multipartUpload : listing.getMultipartUploads()) {
//        System.out.println("Key: " + multipartUpload.getKey() + " UploadId: " + multipartUpload.getUploadId());
//    }
//    NOTE：默认情况下，如果Bucket中的分块上传事件的数量大于1000，则只会返回1000个Object， 且返回结
//    果中 IsTruncated 为 false，反回 NextKeyMarker 和 NextUploadMarker 作为下此读取的起点。若想增大返
//    回分块上传事件数目，可以修改 MaxUploads 参数，或者使用 KeyMarker 以及 UploadIdMarker 参数分次读
//    取。
//    获取所有已上传的块信息
//    我们可以用 listParts 方法获取某个上传事件所有已上传的块。
//    ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, key, uploadId);
//    // 获取上传的所有Part信息
//    PartListing partListing = client.listParts(listPartsRequest);
//    // 遍历所有Part
//    for (PartSummary part : partListing.getParts()) {
//        System.out.println("PartNumber: " + part.getPartNumber() + " ETag: " + part.getETag());
//    }
//    NOTE：默认情况下，如果Bucket中的分块上传事件的数量大于1000，则只会返回1000个Object， 且返回结
//    果中 IsTruncated 为 false，并返回 NextPartNumberMarker 作为下此读取的起点。若想增大返回分块上传
//    事件数目，可以修改 MaxParts 参数，或者使用 PartNumberMarker 参数分次读取。
//    */
//
//    /*
//    跨域资源共享
//    跨域资源共享（CORS）
//    CORS允许web端的应用程序访问不属于本域的资源。OSS提供接口方便开发者控制跨域访问的权限。
//    设定CORS规则
//    通过setBucketCORS 方法将指定的bucket上设定一个跨域资源共享CORS的规则，如果原规则存在则覆盖原规
//    则。具体的规则主要通过CORSRule类来进行参数设置。代码如下：
//    SetBucketCORSRequest request = new SetBucketCORSRequest();
//    request.setBucketName(bucketName);
//    ArrayList<CORSRule> putCorsRules = new ArrayList<CORSRule>();
//    //CORS规则的容器,每个bucket最多允许10条规则
//    CORSRule corRule = new CORSRule();
//    ArrayList<String> allowedOrigin = new ArrayList<String>();
//    //指定允许跨域请求的来源
//    allowedOrigin.add( "http://www.b.com");
//    ArrayList<String> allowedMethod = new ArrayList<String>();
//    //指定允许的跨域请求方法(GET/PUT/DELETE/POST/HEAD)
//    allowedMethod.add("GET");
//    ArrayList<String> allowedHeader = new ArrayList<String>();
//    //控制在OPTIONS预取指令中Access-Control-Request-Headers头中指定的header是否允许。
//    allowedHeader.add("x-oss-test");
//    ArrayList<String> exposedHeader = new ArrayList<String>();
//    //指定允许用户从应用程序中访问的响应头
//    exposedHeader.add("x-oss-test1");
//    corRule.setAllowedMethods(allowedMethod);
//    corRule.setAllowedOrigins(allowedOrigin);
//    corRule.setAllowedHeaders(allowedHeader);
//    corRule.setExposeHeaders(exposedHeader);
//    //指定浏览器对特定资源的预取(OPTIONS)请求返回结果的缓存时间,单位为秒。
//    corRule.setMaxAgeSeconds(10);
//    //最多允许10条规则
//    putCorsRules.add(corRule);
//    request.setCorsRules(putCorsRules);
//    oss.setBucketCORS(request);
//    其中需要特别注意的是：
//    每个bucket最多只能使用10条规则。
//    AllowedOrigins和AllowedMethods都能够最多支持一个”*”通配符。”*”表示对于所有的域来源
//    或者操作都满足。而AllowedHeaders和ExposeHeaders不支持通配符。
//    */
//
//    // 获取CORS规则
//    // 我们可以参考bucket的CORS规则，通过getBucketCORSRules方法。代码如下：
//    //ArrayList<CORSRule> corsRules;
//    ////获得CORS规则列表
//    //corsRules = (ArrayList<CORSRule>) oss.getBucketCORSRules(bucketName);
//    //for (CORSRule rule : corsRules) {
//    //    for (String allowedOrigin1 : rule.getAllowedOrigins()) {
//    //        //获得允许跨域请求源
//    //        System.out.println(allowedOrigin1);
//    //    }
//    //    for (String allowedMethod1 : rule.getAllowedMethods()) {
//    //        //获得允许跨域请求方法
//    //        System.out.println(allowedMethod1);
//    //    }
//    //    if (rule.getAllowedHeaders().size() > 0){
//    //        for (String allowedHeader1 : rule.getAllowedHeaders()){
//    //            //获得允许头部列表
//    //            System.out.println(allowedHeader1);
//    //        }
//    //    }
//    //    if (rule.getExposeHeaders().size() > 0){
//    //        for (String exposeHeader : rule.getExposeHeaders()){
//    //            //获得允许头部
//    //            System.out.println(exposeHeader);
//    //        }
//    //    }
//    //    if ( null != rule.getMaxAgeSeconds()){
//    //        System.out.println(rule.getMaxAgeSeconds());
//    //    }
//    //}
//
//    // 删除CORS规则
//    // 用于关闭指定Bucket对应的CORS并清空所有规则。
//    //// 清空bucket的CORS规则
//    //oss.deleteBucketCORSRules(bucketName);
//
//    // 同样的，只有bucket的拥有者才能删除规则。
//
//
//    // 防盗链
//    // 防盗链设置
//    // OSS是按使用收费的服务，为了防止用户在OSS上的数据被其他人盗链，OSS支持基于HTTP header中表头字段referer的防盗链方法。
//
//    /*
//    设置Referer白名单
//    通过下面代码设置Referer白名单：
//    OSSClient client = new OSSClient(endpoint, accessId, accessKey);
//    List<String> refererList = new ArrayList<String>();
//    // 添加referer项
//    refererList.add("http://www.aliyun.com");
//    refererList.add("http://www.*.com");
//    refererList.add("http://www.?.aliyuncs.com");
//    // 允许referer字段为空，并设置Bucket Referer列表
//    BucketReferer br = new BucketReferer(true, refererList);
//    client.setBucketReferer(bucketName, br);
//    Referer参数支持通配符“*”和“？”，更多详细的规则配置可以参考用户手册OSS防盗链
//    */
//
//    /*
//    //// 获取Referer白名单
//    //// 获取Bucket Referer列表
//    //br = client.getBucketReferer(bucketName);
//    //refererList = br.getRefererList();
//    //for (String referer : refererList) {
//    //    System.out.println(referer);
//    //}
//    输出结果示例：
//    http://www.aliyun.com
//    http://www.*.com"
//    http://www.?.aliyuncs.com
//    */
//
//    /*
//    清空Referer白名单
//    Referer白名单不能直接清空，只能通过重新设置来覆盖之前的规则。
//    OSSClient client = new OSSClient(endpoint, accessId, accessKey);
//    // 默认允许referer字段为空，且referer白名单为空。
//    BucketReferer br = new BucketReferer();
//    client.setBucketReferer(bucketName, br);
//    */
//
//    /*
//    生命周期管理
//    生命周期管理（Lifecycle）
//    OSS提供Object生命周期管理来为用户管理对象。用户可以为某个Bucket定义生命周期配置，来为该Bucket的
//    Object定义各种规则。目前，用户可以通过规则来删除相匹配的Object。每条规则都由如下几个部分组成：
//    Object名称前缀，只有匹配该前缀的Object才适用这个规则
//    操作，用户希望对匹配的Object所执行的操作。
//    日期或天数，用户期望在特定日期或者是在Object最后修改时间后多少天执行指定的操作。
//
//    设置Lifecycle
//    lifecycle的配置规则由一段xml表示。
//    <LifecycleConfiguration>
//        <Rule>
//            <ID>delete obsoleted files</ID>
//            <Prefix>obsoleted/</Prefix>
//            <Status>Enabled</Status>
//            <Expiration>
//            <Days>3</Days>
//            </Expiration>
//        </Rule>
//        <Rule>
//            <ID>delete temporary files</ID>
//            <Prefix>temporary/</Prefix>
//            <Status>Enabled</Status>
//            <Expiration>
//                <Date>2022-10-12T00:00:00.000Z</Date>
//            </Expiration>
//        </Rule>
//    </LifecycleConfiguration>
//
//    一个Lifecycle的Config里面可以包含多个Rule（最多1000个）。
//    各字段解释：
//    ID字段是用来唯一表示本条Rule（各个ID之间不能由包含关系，比如abc和abcd这样的）。
//    Prefix指定对bucket下的符合特定前缀的object使用规则。
//    Status指定本条规则的状态，只有Enabled和Disabled，分别表示启用规则和禁用规则。
//    Expiration节点里面的Days表示大于文件最后修改时间指定的天数就删除object，Date则表示到指定
//                                                  的绝对时间之后就删除object(绝对时间服从ISO8601的格式)。
//    可以通过下面的代码，设置上述lifecycle规则。
//    OSSClient client = new OSSClient(endpoint, accessId, accessKey);
//    SetBucketLifecycleRequest req = new SetBucketLifecycleRequest(bucketName);
//    // 添加Lifecycle规则
//    req.AddLifecycleRule(new LifecycleRule("delete obsoleted files", "obsoleted/", RuleStatus.Enabled, 3));
//    req.AddLifecycleRule(new LifecycleRule("delete temporary files", "temporary/", RuleStatus.Enabled,
//                                           DateUtil.parseIso8601Date("2022-10-12T00:00:00.000Z")));
//    // 设置Bucket Lifecycle
//    client.setBucketLifecycle(req);
//    可以通过下面的代码获取上述lifecycle规则。
//    OSSClient client = new OSSClient(endpoint, accessId, accessKey);
//    // 获取上述Bucket Lifecycle
//    List<LifecycleRule> rules = client.getBucketLifecycle(bucketName);
//    Assert.assertEquals(rules.size(), 2);
//    System.out.println("Rule1: ");
//    LifecycleRule r1 = rules.get(0);
//    System.out.println("ID: " + r1.getId());
//    System.out.println("Prefix: " + r1.getPrefix());
//    System.out.println("Status: " + r1.getStatus().toString());
//    System.out.println("ExpirationDays: " + r1.getExpriationDays());
//    System.out.println();
//    System.out.println("Rule2: ");
//    LifecycleRule r2 = rules.get(1);
//    System.out.println("ID: " + r2.getId());
//    System.out.println("Prefix: " + r2.getPrefix());
//    System.out.println("Status: " + r2.getStatus().toString());
//    System.out.println("ExpirationTime: " + DateUtil.formatIso8601Date(r2.getExpirationTime()));
//
//    可以通过下面的代码清空bucket中lifecycle规则。
//    OSSClient client = new OSSClient(endpoint, accessId, accessKey);
//    client.deleteBucketLifecycle(bucketName);
//    */
//
//    /*
//    授权访问
//    */
//
//    /*
//    --------------------------------------------------------------------------------------------------------------------
//    // 异常
//    OSS Java SDK 中有两种异常 ClientException 以及 OSSException ， 他们都继承自或者间接继承自RuntimeException。
//    ClientException
//    ClientException指SDK内部出现的异常，比如未设置BucketName，网络无法到达等等。
//    OSSException
//    OSSException指服务器端错误，它来自于对服务器错误信息的解析。OSSException一般有以下几个成员：
//    Code： OSS返回给用户的错误码。
//    Message： OSS给出的详细错误信息。
//    RequestId： 用于唯一标识该次请求的UUID；当您无法解决问题时，可以凭这个RequestId来请求
//    OSS开发工程师的帮助。
//    HostId： 用于标识访问的OSS集群（目前统一为oss.aliyuncs.com）
//    下面是OSS中常见的异常：
//    错误码描述
//    AccessDenied 拒绝访问
//    BucketAlreadyExists Bucket已经存在
//    BucketNotEmpty Bucket不为空
//    EntityTooLarge 实体过大
//    EntityTooSmall 实体过小
//    FileGroupTooLarge 文件组过大
//    FilePartNotExist 文件Part不存在
//    FilePartStale 文件Part过时
//    InvalidArgument 参数格式错误
//    InvalidAccessKeyId AccessKeyId不存在
//    InvalidBucketName 无效的Bucket名字
//    InvalidDigest 无效的摘要
//    InvalidObjectName 无效的Object名字
//    InvalidPart 无效的Part
//    InvalidPartOrder 无效的part顺序
//    InvalidTargetBucketForLogging Logging操作中有无效的目标bucket
//    InternalError OSS内部发生错误
//    MalformedXML XML格式非法
//    MethodNotAllowed 不支持的方法
//    MissingArgument 缺少参数
//    MissingContentLength 缺少内容长度
//    NoSuchBucket Bucket不存在
//    NoSuchKey 文件不存在
//    NoSuchUpload Multipart Upload ID不存在
//    NotImplemented 无法处理的方法
//    PreconditionFailed 预处理错误
//    RequestTimeTooSkewed 发起请求的时间和服务器时间超出15分钟
//    RequestTimeout 请求超时
//    SignatureDoesNotMatch 签名错误
//    TooManyBuckets 用户的Bucket数目超过限制
//    --------------------------------------------------------------------------------------------------------------------
//    */
//
//
//}
