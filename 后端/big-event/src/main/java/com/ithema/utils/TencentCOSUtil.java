package com.ithema.utils;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;

import java.io.File;

public class TencentCOSUtil {
    private static final String SECRET_ID = "xxx";
    private static final String SECRET_KEY = "xxx";
    private static final String BUCKET_NAME = "sss";
    private static final String REGION = "ap-guangzhou";
    private static volatile COSClient cosClient;

    private TencentCOSUtil() {}

    private static COSClient getCosClient() {
        if (cosClient == null) {
            synchronized (TencentCOSUtil.class) {
                if (cosClient == null) {
                    // 1. 初始化用户身份信息（secretId, secretKey）
                    COSCredentials cred = new BasicCOSCredentials(SECRET_ID, SECRET_KEY);
                    // 2. 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
                    Region region = new Region(REGION);
                    ClientConfig clientConfig = new ClientConfig(region);
                    // 3. 生成 cos 客户端
                    cosClient = new COSClient(cred, clientConfig);
                }
            }
        }
        return cosClient;
    }

    public static void uploadFile(String key, File localFile) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, key, localFile);
        getCosClient().putObject(putObjectRequest);
        System.out.println("文件上传成功");
    }

    public static void downloadFile(String key, File downFile) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(BUCKET_NAME, key);
        getCosClient().getObject(getObjectRequest, downFile);
        System.out.println("文件下载成功");
    }

    public static void deleteFile(String key) {
        getCosClient().deleteObject(BUCKET_NAME, key);
        System.out.println("文件删除成功");
    }

    public static void shutdown() {
        if (cosClient != null) {
            cosClient.shutdown();
            cosClient = null;
        }
    }

    public static void main(String[] args) {
        String key = "2.jpg";
        File localFile = new File("path/to/your/2.jpg");
        File downFile = new File("path/to/save/downloaded/2.jpg");

        // 上传文件
        uploadFile(key, localFile);

        // 下载文件
        downloadFile(key, downFile);

        // 删除文件
        deleteFile(key);

        // 关闭客户端
        shutdown();
    }
}