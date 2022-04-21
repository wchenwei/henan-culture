package com.henan.culture.domain.entity.player;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: 玩家积分
 * @author: chenwei
 * @create: 2022-04-19 18:48
 **/
@Data
public class PlayerScore {
    private long maxScore;//最高分

    public boolean checkAndReset(int score){
        if (score > maxScore){
            this.maxScore = score;
            return true;
        }
        return false;
    }
}
