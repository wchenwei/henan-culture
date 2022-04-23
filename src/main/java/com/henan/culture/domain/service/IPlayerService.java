package com.henan.culture.domain.service;

import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.domain.entity.WxAccount;

public interface IPlayerService {

    Player loadPlayer(WxAccount wxAccount);

    void checkDayReset(Player player);
}
