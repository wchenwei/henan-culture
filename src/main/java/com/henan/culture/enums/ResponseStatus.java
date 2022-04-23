package com.henan.culture.enums;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-20 10:36
 **/
public enum ResponseStatus {
    OK(0,"成功"),
    ERROR(1,"失败"),
    PARAM_ERROR(2,"参数错误"),
    LOGIN_FAIL(2,"参数错误"),
    ;

    ResponseStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String desc;
}
