package com.henan.culture.config.template.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.henan.culture.config.template.DaTiTemplate;
import com.henan.culture.enums.ItemType;
import com.henan.culture.infrastructure.config.excel.FileConfig;
import com.henan.culture.infrastructure.util.ItemUtil;
import lombok.Getter;

import java.util.Map;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-23 16:07
 **/
@FileConfig("dati")
public class DaTiTemplateImpl extends DaTiTemplate {
    @Getter
    private Map<ItemType, Long> rewards = Maps.newHashMap();

    public void init(){
        if (StrUtil.isNotEmpty(getPrize())){
            rewards = ItemUtil.buildItemMap(getPrize());
        }
    }

    public boolean isRight(int answer){
        return answer == getAnswer();
    }

}
