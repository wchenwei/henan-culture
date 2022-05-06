package com.henan.culture.controller.game;

import com.henan.culture.domain.dto.ResponseDTO;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.enums.RedisHashType;
import com.henan.culture.service.impl.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-20 09:58
 **/
@RestController
@RequestMapping("/account")
public class AccountController{
    @Resource
    private AccountService accountService;

    /**
     * 微信小程序端用户登陆api
     * 返回给小程序端 自定义登陆态 token
     */
    @RequestMapping("/login")
    public ResponseDTO wxAppletLoginApi(HttpServletRequest request) {
        try {
            String code = request.getParameter("code");
            String name = request.getParameter("name");
            String headIcon = request.getParameter("headIcon");
            if (StringUtils.isAnyEmpty(code, name, headIcon)){
                return ResponseDTO.Fail("参数错误");
            }
            Player player = accountService.userLogin(code, name, headIcon);
            if (player != null){
                RedisHashType.LoginTimes.incrementKey(player.getId());
                return ResponseDTO.Suc(player);
            }
        } catch (Exception e) {
            return ResponseDTO.Fail();
        }
        return ResponseDTO.Fail();
    }


}
