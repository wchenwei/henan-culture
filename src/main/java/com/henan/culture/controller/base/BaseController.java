package com.henan.culture.controller.base;

import com.henan.culture.cache.PlayerCacheManager;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.domain.entity.WxAccount;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-16 18:28
 **/
@Component
public class BaseController {
    
    public WxAccount getLoginAccount(){
        WxAccount principal = (WxAccount) SecurityUtils.getSubject().getPrincipal();
        return principal;
    }

    public Player getLoginPlayer(){
        WxAccount loginAccount = getLoginAccount();
        if(loginAccount != null){
            return PlayerCacheManager.getInstance().getPlayer(loginAccount);
        }
        return null;
    }
}
