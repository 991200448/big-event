package com.ithema.controller;

import com.ithema.pojo.Result;
import com.ithema.utils.TencentCOSUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@RestController
public class FileUploadController {
    private static final String BUCKET_NAME = "daisypig-1259801538";

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
//        file.transferTo(new File("D:\\"+fileName));
        // 将 MultipartFile 转换为 File
        File localFile = convert(file);

        try {
            // 调用 TencentCOSUtil 工具类的上传方法
            TencentCOSUtil.uploadFile(fileName, localFile);
        } finally {
            // 删除临时文件
            localFile.delete();
        }
        String url = "https://"+BUCKET_NAME+".cos.ap-guangzhou.myqcloud.com/" + fileName;
        return Result.success(url);
    }


    // 将 MultipartFile 转换为 File 的方法
    public static File convert(MultipartFile multipartFile) throws IOException {
        File tempFile = File.createTempFile("temp", null);
        try (InputStream inputStream = multipartFile.getInputStream();
             OutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return tempFile;
    }
}