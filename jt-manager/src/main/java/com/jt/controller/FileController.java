package com.jt.controller;

import com.jt.service.FileService;
import com.jt.vo.EasyUIImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     * url:http://localhost:8091/pic/upload?dir=image
     * 参数："uploadFile"
     * 返回值： {"error":0,"url":"图片的保存路径","width":图片的宽度,"height":图片的高度}
     */
    @RequestMapping("/pic/upload")
    public EasyUIImage uploadFile(MultipartFile uploadFile){
        return fileService.uploadFile(uploadFile);
    }

}
