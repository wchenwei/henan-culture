package com.henan.culture.domain.entity;

import com.henan.culture.cache.PlayerCacheManager;
import com.henan.culture.domain.entity.player.Player;
import lombok.Data;
import org.springframework.data.redis.core.ZSetOperations;

/**
 * @author 作者 xjt:
 * @version 创建时间：2017年5月10日 上午10:34:43
 * 类说明
 */
@Data
public class LeaderboardInfo {
    private int id;//玩家id
    private String name;// 玩家名称
    private int rank;//排名
    private long score;//分数

    public LeaderboardInfo(ZSetOperations.TypedTuple<String> data, int rank) {
        try {
            this.id = Integer.parseInt(data.getValue());
            Player player = PlayerCacheManager.getInstance().getPlayer(id);
            if (player != null){
                this.score = data.getScore().longValue();
                this.rank = rank;
                this.name = player.getName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
