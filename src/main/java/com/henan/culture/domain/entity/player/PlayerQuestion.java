package com.henan.culture.domain.entity.player;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-25 11:41
 **/
public class PlayerQuestion {
    public Set<Integer> ques = Sets.newHashSet();

    public void add(int id){
        ques.add(id);
    }

    public boolean isAnswer(int id){
        return ques.contains(id);
    }

    public void clear(){
        this.ques.clear();
    }
}
