package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SysResult implements Serializable {
    private static final long serialVersionUID = 8256609573007416976L;
    private Integer status;
    private String msg;
    private Object data;

    public static SysResult success(){
        return new SysResult(200,"操作成功",null);
    }
    public static SysResult success(Object data){
        return new SysResult(200,"操作成功",data);
    }
    public static SysResult success(String msg,Object data){
        return new SysResult(200,msg,data);
    }

    public static SysResult fail(){
        return new SysResult(201,"操作失败",null);
    }


}
