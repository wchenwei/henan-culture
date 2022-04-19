package com.henan.culture.domain.entity.player;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @description: 图鉴
 * @author: chenwei
 * @create: 2022-04-19 15:32
 **/
public class PlayerPoked {
    private List<Integer> list = Lists.newArrayList();

    public void addPoked(int id){
        list.add(id);
    }
}
