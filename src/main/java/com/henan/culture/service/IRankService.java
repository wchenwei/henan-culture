package com.henan.culture.service;

import com.henan.culture.domain.entity.LeaderboardInfo;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.enums.RankType;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Set;

public interface IRankService {

    public void updatePlayerRankForAdd(Player player, RankType rankType, long add);

    public void updatePlayerRankForAdd(Player player, String rankName, long add);

    public void updatePlayerRank(Player player, String rankName, long value);

    public void updatePlayerRank(Player player,RankType rankType, long value);

    public long getPlayerRank(Player player, RankType rankType) ;

    public List<LeaderboardInfo> getGroupRanks(RankType rankType, int pageNo);

    Set<ZSetOperations.TypedTuple<String>> getRankPlayers(RankType rankType, int start, int end);

    public Long getPlayerScore(Player player, RankType rankType);

    void renameRankType(RankType rankType);

    boolean isHaveRank(RankType rankType);
}
