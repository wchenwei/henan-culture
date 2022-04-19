package com.henan.culture.domain.service.impl;

import com.google.common.collect.Lists;
import com.henan.culture.domain.entity.LeaderboardInfo;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.domain.service.IRankService;
import com.henan.culture.enums.RankType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-19 11:05
 **/
@Service
public class RankService implements IRankService {
    protected static final int pageSize = 10;//每页的条数

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    @Override
    public void updatePlayerRankForAdd(Player player, RankType rankType, long add) {
        redisTemplate.opsForZSet().incrementScore(rankType.getRankType(), player.getId(), add);
    }

    @Override
    public void updatePlayerRankForAdd(Player player, String rankName, long add) {
        redisTemplate.opsForZSet().incrementScore(rankName, player.getId(), add);
    }

    @Override
    public void updatePlayerRank(Player player, String rankName, long value) {
        redisTemplate.opsForZSet().add(rankName, player.getId(), value);
    }

    @Override
    public void updatePlayerRank(Player player, RankType rankType, long value) {
        redisTemplate.opsForZSet().add(rankType.getRankType(), player.getId(), value);
    }


    @Override
    public long getPlayerRank(Player player, RankType rankType) {
        return redisTemplate.opsForZSet().reverseRank(rankType.getRankType(), player.getId());
    }

    @Override
    public List<LeaderboardInfo> getGroupRanks(RankType rankType, int pageNo) {
        int start = pageSize * (pageNo - 1);
        int end = pageSize * pageNo - 1;
        List<LeaderboardInfo> infos = Lists.newArrayList();
        Set<ZSetOperations.TypedTuple<Integer>> values = redisTemplate.opsForZSet().reverseRangeWithScores(rankType.getRankType(), start, end);
        for (ZSetOperations.TypedTuple<Integer> val : values) {
            infos.add(new LeaderboardInfo(val, start));
            start++;
        }
        return infos;
    }

    @Override
    public Long getPlayerScore(Player player, RankType rankType) {
        Double score  = redisTemplate.opsForZSet().score(rankType.getRankType(), player.getId());
        if (score == null){
            return 0L;
        }
        return score.longValue();
    }
}
