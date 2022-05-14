package com.henan.culture.domain.entity.player;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * @description: 玩家积分
 * @author: chenwei
 * @create: 2022-04-19 18:48
 **/
@Data
public class PlayerScore {
    private Map<Integer,Integer> scoreMap = Maps.newHashMap();


    public boolean checkAndReset(int id, int score){
        Integer cScore = scoreMap.getOrDefault(id, 0);
        if(score > cScore){
            scoreMap.put(id, score);
            return true;
        }
        return false;
    }

    public long getMaxScore() {
        return  scoreMap.values().stream().mapToInt(Integer::intValue).sum();
    }

    public void clear(){
        this.scoreMap.clear();
    }


}
