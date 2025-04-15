package com.ithema.bigevent;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TencentCOSImageUploadTest {

    private static COSClient cosClient;
    private static final String SECRET_ID = "sss";
    private static final String SECRET_KEY = "sss";
    private static final String BUCKET_NAME = "sss";
    private static final String REGION = "ap-guangzhou";
    private static final String IMAGE_KEY = "2.jpg";
    private static final String LOCAL_IMAGE_PATH = "F:/desk/其他的/2.jpg";

    @BeforeAll
    public static void setUp() {
        // 1. 初始化用户身份信息（secretId, secretKey）
        COSCredentials cred = new BasicCOSCredentials(SECRET_ID, SECRET_KEY);
        // 2. 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        Region region = new Region(REGION);
        ClientConfig clientConfig = new ClientConfig(region);
        // 3. 生成 cos 客户端
        cosClient = new COSClient(cred, clientConfig);
    }

    @Test
    public void testUploadImage() {
        // 要上传的本地图片文件路径
        File localImageFile = new File(LOCAL_IMAGE_PATH);
        // 存储在 COS 上的对象键
        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, IMAGE_KEY, localImageFile);
        PutObjectResult result = cosClient.putObject(putObjectRequest);
        assertTrue(result.getETag() != null);
        System.out.println("图片上传成功");
    }

    @Test
    public void testDownloadImage() {
        // 存储在 COS 上的对象键
        GetObjectRequest getObjectRequest = new GetObjectRequest(BUCKET_NAME, IMAGE_KEY);
        // 下载到本地的文件路径
        File downFile = new File("path/to/save/downloaded/2.jpg");
        ObjectMetadata objectMetadata = cosClient.getObject(getObjectRequest, downFile);
        assertTrue(downFile.exists());
        System.out.println("图片下载成功");
    }

    @Test
    public void testDeleteImage() {
        // 存储在 COS 上的对象键
        cosClient.deleteObject(BUCKET_NAME, IMAGE_KEY);
        // 验证对象是否已被删除
        try {
            cosClient.getObjectMetadata(BUCKET_NAME, IMAGE_KEY);
        } catch (Exception e) {
            assertTrue(e instanceof CosServiceException);
            System.out.println("图片删除成功");
        }
    }

    @AfterAll
    public static void tearDown() {
        // 关闭客户端
        cosClient.shutdown();
    }
}    