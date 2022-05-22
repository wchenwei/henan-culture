package com.henan.culture.config.template.impl;

import com.google.common.collect.Lists;
import com.henan.culture.config.template.ZhuanPanTemplate;
import com.henan.culture.domain.entity.Items;
import com.henan.culture.utils.config.excel.FileConfig;
import com.henan.culture.utils.util.ItemUtils;

import java.util.List;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-05-22 11:32
 **/
@FileConfig("zhuanpan")
public class ZhuanPanTemplateImpl extends ZhuanPanTemplate {
    private List<Items> items = Lists.newArrayList();

    public void init(){
        items = ItemUtils.str2DefaultItemImmutableList(getPrize());
    }

    public List<Items> getItems() {
        return items;
    }
}
