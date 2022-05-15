package com.henan.culture.service.impl;

import cn.hutool.core.date.DateUtil;
import com.henan.culture.cache.PlayerCacheManager;
import com.henan.culture.domain.db.PlayerUtil;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.domain.entity.WxAccount;
import com.henan.culture.enums.RankType;
import com.henan.culture.service.IPlayerService;
import com.henan.culture.service.IRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-19 10:26
 **/
@Service
public class PlayerService implements IPlayerService {

    @Autowired
    private IRankService rankService;


    @Override
    public Player loadPlayer(WxAccount wxAccount) {
        Player player = PlayerUtil.getPlayer(wxAccount.getId());
        if (player == null){
            player = new Player();
            player.setId(wxAccount.getId());
            player.setWxOpenId(wxAccount.getWxOpenid());
            player.setRegisterTime(wxAccount.getLastTime().getTime());
        }
        PlayerCacheManager.getInstance().addPlayerToCache(player);
        return player;
    }

    @Override
    public boolean checkDayReset(Player player) {
        String dayMark = DateUtil.format(new Date(), "yyyyMMdd");
        if (!dayMark.equals(player.getDayMark())){
            doDayReset(player);
            player.setDayMark(dayMark);
            return true;
        }
        return false;
    }

    /**
     * 每日重置
     * @param player
     */
    private void doDayReset(Player player) {
        player.getPlayerShare().clear();
        // 重置分数
        long score = rankService.getPlayerScore(player, RankType.Score);
        if (score <= 0){
            player.getPlayerScore().setMaxScore(0);
        }
    }

}
