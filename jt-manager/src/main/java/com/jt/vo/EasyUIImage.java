package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 封装内容：{"error":0,"url":"图片的保存路径","width":图片的宽度,"height":图片的高度}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EasyUIImage {

    private Integer error;//0表示成功，1表示失败
    private String url;
    private Integer width;
    private Integer height;

    public static EasyUIImage fail(){
        return new EasyUIImage(1,null,null,null);
    }

    public static EasyUIImage success(String url,Integer width,Integer height){
        return new EasyUIImage(0,url,width,height);
    }
}
