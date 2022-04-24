package com.henan.culture.controller.game;

import com.henan.culture.controller.base.BaseController;
import com.henan.culture.domain.entity.player.Player;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-23 14:16
 **/
@RestController
@RequestMapping("/share")
public class ShareController extends BaseController {

    @RequestMapping("/add")
    public void add(HttpServletRequest request){
        String type = request.getParameter("type");// 分享类型
        Player player = getLoginPlayer(request);
    }
}
