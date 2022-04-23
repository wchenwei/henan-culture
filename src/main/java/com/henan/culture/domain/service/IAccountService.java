package com.henan.culture.domain.service;

import com.henan.culture.domain.dto.CodeDTO;
import com.henan.culture.domain.dto.PlayerDTO;
import com.henan.culture.domain.entity.WxAccount;
import com.henan.culture.domain.entity.player.Player;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-20 10:01
 **/
public interface IAccountService {
    Player userLogin(CodeDTO dto);

    WxAccount getWxAccount(String openId, String name);

    Player accountLogin(WxAccount wxAccount);
}
