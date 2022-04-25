package com.henan.culture.service;

import com.henan.culture.domain.entity.LeaderboardInfo;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.enums.RankType;

import java.util.List;

public interface IRankService {

    public void updatePlayerRankForAdd(Player player, RankType rankType, long add);

    public void updatePlayerRankForAdd(Player player, String rankName, long add);

    public void updatePlayerRank(Player player, String rankName, long value);

    public void updatePlayerRank(Player player,RankType rankType, long value);

    public long getPlayerRank(Player player, RankType rankType) ;

    public List<LeaderboardInfo> getGroupRanks(RankType rankType, int pageNo);

    public Long getPlayerScore(Player player, RankType rankType);
}
