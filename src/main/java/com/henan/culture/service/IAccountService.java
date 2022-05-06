package com.henan.culture.service;

import com.henan.culture.domain.entity.WxAccount;
import com.henan.culture.domain.entity.player.Player;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-20 10:01
 **/
public interface IAccountService {
    Player userLogin(String code, String name, String headIcon);

    WxAccount getWxAccount(String openId, String name);

    Player accountLogin(WxAccount wxAccount, String headIcon);
}
