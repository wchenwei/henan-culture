package com.henan.culture.controller.base;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.henan.culture.cache.AccountCacheManager;
import com.henan.culture.cache.PlayerCacheManager;
import com.henan.culture.domain.entity.WxAccount;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.infrastructure.gson.GSONUtils;
import com.henan.culture.infrastructure.util.Md5Utils;
import com.henan.culture.infrastructure.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-16 18:28
 **/
@Component
public class BaseController {
    private static final String signName = "sign";

    public WxAccount getLoginAccount(HttpServletRequest request){
        String openId = request.getParameter("wxOpenId");
        return AccountCacheManager.getInstance().getAccount(openId);
    }

    public Player getLoginPlayer(HttpServletRequest request){
//        if (!validate(request)){
//            return null;
//        }
        WxAccount loginAccount = getLoginAccount(request);
        if(loginAccount != null){
            return PlayerCacheManager.getInstance().getPlayer(loginAccount);
        }
        return null;
    }

    public static boolean validate(HttpServletRequest request) {
        TreeMap<String, Object> paramMap = Maps.newTreeMap();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String param = parameterNames.nextElement();
            if (StringUtils.equals(signName, param)){
                continue;
            }
            paramMap.put(param, request.getParameter(param));
        }
        String sign = request.getParameter(signName);
        String signExtra = Md5Utils.encrypt(paramMap);
        return StringUtils.equals(sign, signExtra);

    }
}
