package com.jt.service;

import com.jt.vo.EasyUIImage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService {

    @Value("${image.filePath}")
    private String filePath;//="F:/JT_Image/";
    @Value(("${image.domainName}"))
    private String domainName;//="http://image.jt.com/";

    @Override
    public EasyUIImage uploadFile(MultipartFile uploadFile) {
        //验证是否为图片格式
        String originalFilename = uploadFile.getOriginalFilename().toLowerCase();
        if(!originalFilename.matches("^.+\\.(jpg|png|gif|bmp)$")){
            return EasyUIImage.fail();
        }
        //验证是否为恶意程序
        try {
            BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            if(width==0 || height==0){
                //如果没有宽高肯定不是图片
                return EasyUIImage.fail();
            }
            //根据日期分路径存储
            String data = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
            String fileDir = filePath+data;
            File file = new File(fileDir);
            if(!file.exists()){
                file.mkdirs();
            }
            //通过UUID随机数重命名文件，防止文件重名
            String fileName = UUID.randomUUID().toString().replace("-","");
            String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
            String realFileName = fileName+fileType;
            String realFilePath = fileDir+realFileName;
            uploadFile.transferTo(new File(realFilePath));
            String url = domainName+data+realFileName;
            return EasyUIImage.success(url,width,height);
        } catch (Exception e) {
            e.printStackTrace();
            return EasyUIImage.fail();
        }

    }
}
