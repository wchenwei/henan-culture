package com.henan.culture.domain.entity.player;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @description: 图鉴
 * @author: chenwei
 * @create: 2022-04-19 15:32
 **/
@Data
public class PlayerPoked {
    private Set<Integer> list = Sets.newHashSet();

    public void addPoked(int id){
        list.add(id);
    }

    public boolean isContain(int id){
        return list.contains(id);
    }

}
