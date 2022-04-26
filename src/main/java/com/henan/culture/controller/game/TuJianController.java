package com.henan.culture.controller.game;

import cn.hutool.core.collection.CollUtil;
import com.henan.culture.config.GameConfig;
import com.henan.culture.controller.base.BaseController;
import com.henan.culture.domain.dto.ResponseDTO;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.domain.entity.player.PlayerPoked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description: 图鉴
 * @author: chenwei
 * @create: 2022-04-24 20:09
 **/
@RestController
@RequestMapping("/tujian")
public class TuJianController extends BaseController {

    @Autowired
    private GameConfig gameConfig;

    @RequestMapping("/add")
    public ResponseDTO add(HttpServletRequest request){
        Player player = getLoginPlayer(request);
        if (player == null){
            return ResponseDTO.Fail("玩家不存在");
        }
        int type = Integer.parseInt(request.getParameter("type"));
        PlayerPoked playerPoked = player.getPlayerPoked();
        List<Integer> ids = gameConfig.getTuJianIdsByType(type);
        if (CollUtil.isNotEmpty(ids)){
            playerPoked.addIds(ids);
            player.saveDB();
        }
        return ResponseDTO.Suc();
    }

    @RequestMapping("/list")
    public ResponseDTO list(HttpServletRequest request){
        Player player = getLoginPlayer(request);
        if (player == null){
            return ResponseDTO.Fail("玩家不存在");
        }
        return ResponseDTO.Suc().addTuJian(player);
    }
}
