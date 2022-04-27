package com.henan.culture.config.template.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.henan.culture.config.template.RankPrizeTemplate;
import com.henan.culture.domain.entity.Items;
import com.henan.culture.utils.config.excel.FileConfig;
import com.henan.culture.utils.util.ItemUtils;
import com.henan.culture.utils.util.StringUtil;
import lombok.Getter;

import java.util.List;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-23 16:12
 **/
@FileConfig("rankprize")
@Getter
public class RankPrizeTemplateImpl extends RankPrizeTemplate {
    private int start;
    private int end;
    private List<Items> rewards = Lists.newArrayList();

    public void init(){
        if (StrUtil.isNotEmpty(getPrize())){
            rewards = ItemUtils.str2DefaultItemImmutableList(getPrize());
        }
        int[] ranks = StringUtil.splitStr2IntArray(getRanking(), ",");
        start = ranks[0];
        if (ranks.length==1){
            end = start;
        }else {
            end = ranks[1];
        }
    }

    public boolean isFit(int rank){
        return rank>=start && rank<= end;
    }

}
