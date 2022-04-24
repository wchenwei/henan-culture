package com.henan.culture.enums;


/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-24 16:35
 **/
public class LogType {

    public static final LogType Mail = new LogType("邮件", 1);
    public static final LogType Share = new LogType("分享", 2);

    ;

    private int code;
    private String desc;
    private String extra;

    public LogType(String desc, int code) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getExtra() {
        return extra;
    }

    public LogType value(String extra) {
        LogType clone = new LogType(this.desc, this.code);
        clone.extra = extra;
        return clone;
    }
    public LogType value(long extra) {
        return value(extra+"");
    }
}