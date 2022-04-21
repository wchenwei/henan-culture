package com.henan.culture.domain.entity.player;

import lombok.Getter;

/**
 * @description: 玩家积分
 * @author: chenwei
 * @create: 2022-04-19 18:48
 **/
@Getter
public class PlayerScore {
    private long maxScore;//最高分
    private long score;// 积分

    public void addScore(int add){
        this.score += add;
    }

    public boolean isHigher(){
        return score > maxScore;
    }

    public void clear(){
        this.score = 0;
    }

}
