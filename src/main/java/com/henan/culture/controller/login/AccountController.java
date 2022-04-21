package com.henan.culture.controller.login;

import cn.hutool.core.util.StrUtil;
import com.henan.culture.domain.dto.CodeDTO;
import com.henan.culture.domain.dto.PlayerDTO;
import com.henan.culture.domain.dto.ResponseDTO;
import com.henan.culture.domain.entity.WxAccount;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.domain.service.IAccountService;
import com.henan.culture.domain.service.WxAppletService;
import com.henan.culture.domain.service.impl.AccountService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-20 09:58
 **/
@RestController
@RequestMapping("/account")
public class AccountController {
    @Resource
    private AccountService accountService;

    /**
     * 微信小程序端用户登陆api
     * 返回给小程序端 自定义登陆态 token
     */
    @RequestMapping("/login")
    public ResponseDTO wxAppletLoginApi(@RequestBody @Validated CodeDTO codeDTO) {
        try {
            Player player = accountService.userLogin(codeDTO);
            return ResponseDTO.Suc(player);
        } catch (Exception e) {
            return ResponseDTO.Fail();
        }
    }

    @RequestMapping("/player/add")
    public ResponseDTO addPlayer(HttpServletRequest request){
        String name = request.getParameter("name");
        String wxOpenId = request.getParameter("wxOpenId");
        if (StrUtil.isEmpty(name) || StrUtil.isEmpty(wxOpenId)){
            return ResponseDTO.Fail();
        }
        Player player = accountService.getPlayer(name, wxOpenId,UUID.randomUUID().toString());
        return ResponseDTO.Suc(player);
    }

    @RequestMapping("/test")
    public ResponseDTO wxAppletLoginApi() {

        return ResponseDTO.Suc();
    }


}
