package com.henan.culture.domain.entity.player;

/**
 * @description: 玩家积分
 * @author: chenwei
 * @create: 2022-04-19 18:48
 **/
public class PlayerScore {
    private long score;// 积分

    public void addScore(int add){
        this.score += add;
    }

    public long getScore() {
        return score;
    }
}
