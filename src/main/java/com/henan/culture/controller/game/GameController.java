package com.henan.culture.controller.game;

import com.henan.culture.controller.base.BaseController;
import com.henan.culture.domain.dto.ResponseDTO;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.domain.service.IPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-23 14:45
 **/
@RequestMapping("/game")
@RestController
public class GameController extends BaseController {
    @Autowired
    private IPlayerService playerService;

    /**
     * 开始新一局
     * @param request
     * @return
     */
    @RequestMapping("/restart")
    public ResponseDTO restart(HttpServletRequest request){
        Player player = getLoginPlayer(request);
        if (playerService.checkDayReset(player)){
            player.saveDB();
        }
        return ResponseDTO.Suc(player.buildDTO());
    }
}
