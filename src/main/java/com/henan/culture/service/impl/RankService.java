package com.henan.culture.service.impl;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import com.henan.culture.domain.entity.LeaderboardInfo;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.service.IRankService;
import com.henan.culture.enums.RankType;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
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
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void updatePlayerRankForAdd(Player player, RankType rankType, long add) {
        stringRedisTemplate.opsForZSet().incrementScore(rankType.getRankType(), String.valueOf(player.getId()), add);
    }

    @Override
    public void updatePlayerRankForAdd(Player player, String rankName, long add) {
        stringRedisTemplate.opsForZSet().incrementScore(rankName, String.valueOf(player.getId()), add);
    }

    @Override
    public void updatePlayerRank(Player player, String rankName, long value) {
        stringRedisTemplate.opsForZSet().add(rankName, String.valueOf(player.getId()), value);
    }

    @Override
    public void updatePlayerRank(Player player, RankType rankType, long value) {
        stringRedisTemplate.opsForZSet().add(rankType.getRankType(), String.valueOf(player.getId()), value);
    }


    @Override
    public long getPlayerRank(Player player, RankType rankType) {
        Long rank = stringRedisTemplate.opsForZSet().reverseRank(rankType.getRankType(), String.valueOf(player.getId()));
        if (rank == null){
            return -1;
        }
        return rank+1;
    }

    @Override
    public List<LeaderboardInfo> getGroupRanks(RankType rankType, int pageNo) {
        int start = pageSize * (pageNo - 1);
        int end = pageSize * pageNo - 1;
        List<LeaderboardInfo> infos = Lists.newArrayList();
        Set<ZSetOperations.TypedTuple<String>> values = stringRedisTemplate.opsForZSet().reverseRangeWithScores(rankType.getRankType(), start, end);
        for (ZSetOperations.TypedTuple<String> val : values) {
            start++;
            infos.add(new LeaderboardInfo(val, start));
        }
        return infos;
    }

    @Override
    public Set<ZSetOperations.TypedTuple<String>> getRankPlayers(RankType rankType, int start, int end){
        return stringRedisTemplate.opsForZSet().reverseRangeWithScores(rankType.getRankType(), start-1, end-1);
    }

    @Override
    public void renameRankType(RankType rankType){
        stringRedisTemplate.renameIfAbsent(rankType.getRankType(), rankType.getRankType()+":"+ DateUtil.format(new Date(),"yyyyMMdd"));
    }

    @Override
    public boolean isHaveRank(RankType rankType) {
        return stringRedisTemplate.hasKey(rankType.getRankType());
    }

    @Override
    public Long getPlayerScore(Player player, RankType rankType) {
        Double score  = stringRedisTemplate.opsForZSet().score(rankType.getRankType(), String.valueOf(player.getId()));
        if (score == null){
            return 0L;
        }
        return score.longValue();
    }
}
