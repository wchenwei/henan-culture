package com.henan.culture.controller.game;

import com.henan.culture.controller.base.BaseController;
import com.henan.culture.domain.dto.ResponseDTO;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.domain.entity.player.PlayerPoked;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 图鉴
 * @author: chenwei
 * @create: 2022-04-24 20:09
 **/
@RestController
@RequestMapping("/tujian")
public class TuJianController extends BaseController {

    @RequestMapping("/add")
    public ResponseDTO add(HttpServletRequest request){
        Player player = getLoginPlayer(request);
        if (player == null){
            return ResponseDTO.Fail("玩家不存在");
        }
        int id = Integer.parseInt(request.getParameter("id"));
        PlayerPoked playerPoked = player.getPlayerPoked();
        if (!playerPoked.isContain(id)){
            playerPoked.addPoked(id);
            player.saveDB();
        }
        return ResponseDTO.Suc().addProperty("tuJian", playerPoked.getList());
    }

    @RequestMapping("/list")
    public ResponseDTO list(HttpServletRequest request){
        Player player = getLoginPlayer(request);
        if (player == null){
            return ResponseDTO.Fail("玩家不存在");
        }
        PlayerPoked playerPoked = player.getPlayerPoked();
        return ResponseDTO.Suc().addProperty("tuJian", playerPoked.getList());
    }
}
