package com.henan.culture.controller.game;

import com.henan.culture.controller.base.BaseController;
import com.henan.culture.domain.dto.PlayerDTO;
import com.henan.culture.domain.entity.player.Player;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-20 13:42
 **/
@RequestMapping("/mail")
@RestController
public class MailController extends BaseController {

    @RequestMapping("/list")
    public PlayerDTO list(HttpServletRequest request){
        Player player = getLoginPlayer(request);

        return null;
    }

}
