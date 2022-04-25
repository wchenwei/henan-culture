package com.henan.culture.service;

import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.domain.entity.WxAccount;

public interface IPlayerService {

    Player loadPlayer(WxAccount wxAccount);

    boolean checkDayReset(Player player);
}
