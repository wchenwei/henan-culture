package com.henan.culture.config.template.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.henan.culture.config.template.RankPrizeTemplate;
import com.henan.culture.domain.entity.Items;
import com.henan.culture.infrastructure.config.excel.FileConfig;
import com.henan.culture.infrastructure.util.ItemUtils;
import lombok.Getter;

import java.util.List;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-23 16:12
 **/
@FileConfig("rankprize")
public class RankPrizeTemplateImpl extends RankPrizeTemplate {

    @Getter
    private List<Items> rewards = Lists.newArrayList();

    public void init(){
        if (StrUtil.isNotEmpty(getPrize())){
            rewards = ItemUtils.str2DefaultItemImmutableList(getPrize());
        }
    }

}
