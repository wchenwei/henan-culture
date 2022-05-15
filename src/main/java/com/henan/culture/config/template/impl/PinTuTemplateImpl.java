package com.henan.culture.config.template.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.henan.culture.config.template.PinTuTemplate;
import com.henan.culture.domain.entity.Items;
import com.henan.culture.utils.config.excel.FileConfig;
import com.henan.culture.utils.util.ItemUtils;
import lombok.Getter;

import java.util.List;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-23 16:07
 **/
@FileConfig("pintu")
public class PinTuTemplateImpl extends PinTuTemplate {
    @Getter
    private List<Items> rewards = Lists.newArrayList();

    public void init(){
        if (StrUtil.isNotEmpty(getPrize())){
            rewards = ItemUtils.str2DefaultItemImmutableList(getPrize());
        }
    }
}
