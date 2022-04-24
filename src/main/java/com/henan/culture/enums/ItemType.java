package com.henan.culture.enums;

import java.util.Arrays;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-19 10:46
 **/
public enum ItemType {
    Help(1,"辅助线道具"),
    Score(2,"得分加倍道具"),
    Relive(3,"复活道具"),
    RealGoods(4,"实物"),
    ;

    ItemType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static ItemType getItemType(int type){
        return Arrays.stream(ItemType.values()).filter(e -> e.getType() == type).findFirst().orElse(null);
    }

    public Integer getType() {
        return type;
    }

    private Integer type;
    private String desc;
}
