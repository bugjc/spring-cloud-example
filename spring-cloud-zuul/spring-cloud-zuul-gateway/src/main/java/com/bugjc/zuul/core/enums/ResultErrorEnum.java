package com.bugjc.zuul.core.enums;

public enum ResultErrorEnum {
    Success(200,"成功"),
    VerifySignError(201,"签名验证失败"),
    ParamError(202,"参数错误"),
    IOError(203,"IO流读取失败")
    ;
    public int code;
    public String msg;
    ResultErrorEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
