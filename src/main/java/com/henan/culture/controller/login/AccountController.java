package com.henan.culture.controller.login;

import com.henan.culture.domain.dto.CodeDTO;
import com.henan.culture.domain.dto.PlayerDTO;
import com.henan.culture.domain.dto.ResponseDTO;
import com.henan.culture.domain.service.IAccountService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-20 09:58
 **/
@RestController
@RequestMapping("/account")
public class AccountController {
    @Resource
    private IAccountService accountService;

    /**
     * 微信小程序端用户登陆api
     * 返回给小程序端 自定义登陆态 token
     */
    @PostMapping("/login")
    public ResponseDTO wxAppletLoginApi(@RequestBody @Validated CodeDTO codeDTO) {
        try {
            PlayerDTO playerDTO = accountService.userLogin(codeDTO);
            return ResponseDTO.Suc()
                    .addProperty("player", playerDTO);
        } catch (Exception e) {
            return ResponseDTO.Fail();
        }

    }

}
