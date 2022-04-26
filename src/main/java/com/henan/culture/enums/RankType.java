package com.henan.culture.enums;

import com.henan.culture.utils.util.Constants;

/**
 * @description: 排行榜类型
 * @author: chenwei
 * @create: 2022-04-19 10:57
 **/
public enum RankType {
    Score("score","积分排行");

    private String rankType;
    private String desc;

    RankType(String rankType, String desc) {
        this.rankType = rankType;
        this.desc = desc;
    }

    public String getRankType() {
        return Constants.gamePrefix+rankType;
    }


}
