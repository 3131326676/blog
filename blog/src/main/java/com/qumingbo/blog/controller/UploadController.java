package com.qumingbo.blog.controller;

import com.qumingbo.blog.utils.Result;
import com.qumingbo.blog.utils.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: 杨德石
 * @Date: 2020/2/10 21:42
 * @Version 1.0
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public Result<String> uploadImage(@RequestParam MultipartFile file) {
        String url = uploadService.uploadImage(file);
        return new Result<>("上传成功！", url);
    }

    /**
     * 删除文件
     * @param fileName 文件名
     * @return true或false
     */
    @RequestMapping(value = "/deleteImage", method = RequestMethod.POST)
    public Result<Object> deleteImage(@RequestParam String fileName) {
        String[] split = fileName.split("/");
        System.out.println(split);
        uploadService.deleteImage(fileName);
        return new Result<Object>(true);
    }

}
