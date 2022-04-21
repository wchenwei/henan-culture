package com.henan.culture.domain.service.impl;

import cn.hutool.core.date.DateUtil;
import com.henan.culture.cache.MailCacheManager;
import com.henan.culture.cache.PlayerCacheManager;
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
    public Player checkAccountPlayer(WxAccount wxAccount) {
        Player player = PlayerCacheManager.getInstance().getPlayer(wxAccount);
        if (player == null){
            player = new Player();
            player.setId(wxAccount.getId());
            player.setWxOpenId(wxAccount.getWxOpenid());
            PlayerCacheManager.getInstance().addPlayerToCache(player);
        }
        // 每次登录重新赋值
        player.setName(wxAccount.getName());
        // 加载邮件
        MailCacheManager.getInstance().loadPlayerSysMail(player);
        // 每日重置
        checkDayReset(player);
        player.saveDB();
        return player;
    }

    public void checkDayReset(Player player) {
        String dayMark = DateUtil.format(new Date(), "yyyyMMdd");
        if (!dayMark.equals(player.getDayMark())){
            doDayReset(player);
            player.setDayMark(dayMark);
        }
    }

    /**
     * 每日重置
     * @param player
     */
    private void doDayReset(Player player) {
        player.setLifeCount(Constants.lifeCount);
        player.getPlayerScore().clear();
    }

}
