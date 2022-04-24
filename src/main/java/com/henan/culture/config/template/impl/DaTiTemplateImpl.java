package com.henan.culture.config.template.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.henan.culture.config.template.DaTiTemplate;
import com.henan.culture.domain.entity.Items;
import com.henan.culture.infrastructure.config.excel.FileConfig;
import com.henan.culture.infrastructure.util.ItemUtils;
import lombok.Getter;

import java.util.List;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-23 16:07
 **/
@FileConfig("dati")
public class DaTiTemplateImpl extends DaTiTemplate {
    @Getter
    private List<Items> rewards = Lists.newArrayList();

    public void init(){
        if (StrUtil.isNotEmpty(getPrize())){
            rewards = ItemUtils.str2DefaultItemImmutableList(getPrize());
        }
    }

    public boolean isRight(int answer){
        return answer == getAnswer();
    }

}
