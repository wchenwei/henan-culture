package com.henan.culture.config.template.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.henan.culture.config.template.RankPrizeTemplate;
import com.henan.culture.enums.ItemType;
import com.henan.culture.infrastructure.config.excel.FileConfig;
import com.henan.culture.infrastructure.util.ItemUtil;
import lombok.Getter;

import java.util.Map;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-23 16:12
 **/
@FileConfig("rankprize")
public class RankPrizeTemplateImpl extends RankPrizeTemplate {

    @Getter
    private Map<ItemType, Long> rewards = Maps.newHashMap();

    public void init(){
        if (StrUtil.isNotEmpty(getPrize1())){
            rewards = ItemUtil.buildItemMap(getPrize1());
        }
    }

}
