package com.henan.culture.controller.login;

import com.henan.culture.controller.base.BaseController;
import com.henan.culture.domain.dto.CodeDTO;
import com.henan.culture.domain.service.WxAppletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 小程序后台 某 API
 */
@RestController
public class WxAppletController extends BaseController {

    @Resource
    private WxAppletService wxAppletService;

    /**
     * 微信小程序端用户登陆api
     * 返回给小程序端 自定义登陆态 token
     */
    @PostMapping("/api/wx/user/login")
    public ResponseEntity wxAppletLoginApi(@RequestBody @Validated CodeDTO request) {

        return new ResponseEntity<>(wxAppletService.wxUserLogin(request.getCode()), HttpStatus.OK);

    }


}
