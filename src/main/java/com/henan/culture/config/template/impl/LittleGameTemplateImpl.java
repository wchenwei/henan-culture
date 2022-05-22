package com.henan.culture.config.template.impl;

import com.henan.culture.config.template.LittleGameTemplate;
import com.henan.culture.domain.entity.Items;
import com.henan.culture.utils.config.excel.FileConfig;
import com.henan.culture.utils.weight.RandomUtils;
import com.henan.culture.utils.weight.WeightMeta;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-05-22 11:32
 **/
@FileConfig("littlegame")
public class LittleGameTemplateImpl extends LittleGameTemplate {
    private WeightMeta<Items> weightMeta;

    public void init(){
        this.weightMeta = RandomUtils.buildItemWeightMeta(getPrize());
    }

    public Items random(){
        return weightMeta.random();
    }
}
