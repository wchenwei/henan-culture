package com.henan.culture.domain.entity;

import org.springframework.data.redis.core.ZSetOperations;

/**
 * @author 作者 xjt:
 * @version 创建时间：2017年5月10日 上午10:34:43
 * 类说明
 */
public class LeaderboardInfo {
    private int id;//玩家id
    private int rank;//排名
    private double score;//分数

    public LeaderboardInfo(int id, int rank, double score) {
        this.id = id;
        this.rank = rank;
        this.score = score;
    }

    public LeaderboardInfo(ZSetOperations.TypedTuple<Integer> data, int rank) {
        try {
            this.score = data.getScore();
            this.id = data.getValue();
            this.rank = rank;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int getId() {
        return id;
    }

    public int getRank() {
        return rank;
    }

    public double getScore() {
        return score;
    }

    //分数取整
    public void scoreToLong() {
        this.score = (long) score;
    }

}
