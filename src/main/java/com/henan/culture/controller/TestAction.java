package com.henan.culture.controller;

import cn.hutool.json.JSONUtil;
import com.henan.culture.controller.base.BaseController;
import com.henan.culture.domain.dto.ResponseDTO;
import com.henan.culture.domain.entity.WxAccount;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.enums.RedisHashType;
import com.henan.culture.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-22 15:22
 **/
@RequestMapping("/test")
@RestController
public class TestAction extends BaseController {

    @Autowired
    private IAccountService accountService;

    @RequestMapping("/login")
    public ResponseDTO login(HttpServletRequest request) {
        String name = request.getParameter("name");
        String wxOpenId = request.getParameter("wxOpenId");
        String headIcon = request.getParameter("headIcon");
        WxAccount wxAccount = accountService.getWxAccount(wxOpenId, name);
        System.out.println("account:"+ JSONUtil.toJsonStr(wxAccount));
        Player player = accountService.accountLogin(wxAccount, headIcon);
        RedisHashType.LoginTimes.incrementKey(player.getId());
        return  ResponseDTO.Suc(player);
    }
}
