package com.sky.controller.admin;


import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;
    
    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<String>upload(MultipartFile file){

        try {
            //得到上传源文件的名称
            String originalFilename = file.getOriginalFilename();
            //得到文件后缀属性
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //生成唯一名称
            String fileName = UUID.randomUUID().toString() + extension;
            //文件请求路径
            String filePath = aliOssUtil.upload(file.getBytes(), fileName);

            return Result.success(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }

}

