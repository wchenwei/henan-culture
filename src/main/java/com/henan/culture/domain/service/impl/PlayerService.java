package com.henan.culture.domain.service.impl;

import cn.hutool.core.date.DateUtil;
import com.henan.culture.cache.MailCacheManager;
import com.henan.culture.cache.PlayerCacheManager;
import com.henan.culture.domain.db.PlayerUtil;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.domain.entity.WxAccount;
import com.henan.culture.domain.service.IPlayerService;
import com.henan.culture.infrastructure.util.Constants;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-19 10:26
 **/
@Service
public class PlayerService implements IPlayerService {


    @Override
    public Player loadPlayer(WxAccount wxAccount) {
        Player player = PlayerUtil.getPlayer(wxAccount.getId());
        if (player == null){
            player = new Player();
            player.setId(wxAccount.getId());
            player.setWxOpenId(wxAccount.getWxOpenid());
            PlayerCacheManager.getInstance().addPlayerToCache(player);
        }
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
    }

}
