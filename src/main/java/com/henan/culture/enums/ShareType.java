package com.henan.culture.enums;

import java.util.Arrays;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-23 14:18
 **/
public enum ShareType {
    Normal(1,"游戏主页面"),
    Top(2,"顶部"),
    Relive(3,"重生"),
    ;

    ShareType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private int type;
    private String desc;

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static ShareType getShareType(int type){
        return Arrays.stream(ShareType.values()).filter(e -> e.getType() == type).findFirst().orElse(null);
    }

    public RedisHashType getRedisHashType(){
        switch (this){
            case Normal:
                return RedisHashType.ShareNormal;
            case Relive:
                return RedisHashType.ShareRelive;
            case Top:
                return RedisHashType.ShareTop;
        }
        return null;
    }
}
