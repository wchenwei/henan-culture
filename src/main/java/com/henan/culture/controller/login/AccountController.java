package com.henan.culture.controller.login;

import com.henan.culture.domain.dto.CodeDTO;
import com.henan.culture.domain.dto.ResponseDTO;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.domain.service.impl.AccountService;
import com.henan.culture.enums.ResponseStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseDTO wxAppletLoginApi(@RequestBody @Validated CodeDTO codeDTO) {
        try {
            Player player = accountService.userLogin(codeDTO);
            if (player != null){
                return ResponseDTO.Suc(player);
            }
        } catch (Exception e) {
            return ResponseDTO.Fail();
        }
        return ResponseDTO.buildDTO(ResponseStatus.LOGIN_FAIL);
    }


    @RequestMapping("/test")
    public ResponseDTO wxAppletLoginApi(HttpServletRequest request) {

        return ResponseDTO.Suc();
    }


}
