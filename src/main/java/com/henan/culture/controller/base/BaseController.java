package com.henan.culture.controller.base;

import com.henan.culture.cache.AccountCacheManager;
import com.henan.culture.cache.PlayerCacheManager;
import com.henan.culture.domain.entity.WxAccount;
import com.henan.culture.domain.entity.player.Player;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-16 18:28
 **/
@Component
public class BaseController {


    public WxAccount getLoginAccount(HttpServletRequest request){
        String openId = request.getParameter("wxOpenId");
        return AccountCacheManager.getInstance().getAccount(openId);
    }

    public Player getLoginPlayer(HttpServletRequest request){
        WxAccount loginAccount = getLoginAccount(request);
        if(loginAccount != null){
            return PlayerCacheManager.getInstance().getPlayer(loginAccount);
        }
        return null;
    }
}
