package com.henan.culture.domain.entity;

import com.henan.culture.cache.PlayerCacheManager;
import com.henan.culture.domain.entity.player.Player;
import org.springframework.data.redis.core.ZSetOperations;

/**
 * @author 作者 xjt:
 * @version 创建时间：2017年5月10日 上午10:34:43
 * 类说明
 */
public class LeaderboardInfo {
    private int id;//玩家id
    private String name;// 玩家名称
    private int rank;//排名
    private double score;//分数

    public LeaderboardInfo(int id, int rank, double score) {
        this.id = id;
        this.rank = rank;
        this.score = score;
    }

    public LeaderboardInfo(ZSetOperations.TypedTuple<String> data, int rank) {
        try {
            Player player = PlayerCacheManager.getInstance().getPlayerOrNull(id);
            if (player != null){
                this.score = data.getScore();
                this.id = Integer.parseInt(data.getValue());
                this.rank = rank;
                this.name = player.getName();
            }
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
