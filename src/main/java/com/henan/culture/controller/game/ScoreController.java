package com.henan.culture.controller.game;

import com.henan.culture.controller.base.BaseController;
import com.henan.culture.domain.dto.ResponseDTO;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.service.IRankService;
import com.henan.culture.enums.RankType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 积分
 * @author: chenwei
 * @create: 2022-04-19 15:39
 **/
@RestController
@RequestMapping("/score")
public class ScoreController extends BaseController {

    @Autowired
    private IRankService rankService;

    @RequestMapping("/update")
    public ResponseDTO update(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        int score = Integer.parseInt(request.getParameter("score"));
        Player player = getLoginPlayer(request);
        if (player == null){
            return ResponseDTO.Fail("玩家不存在");
        }
        if (player.getPlayerScore().checkAndReset(id, score)){
            rankService.updatePlayerRank(player, RankType.Score, player.getPlayerScore().getMaxScore());
            player.saveDB();
        }
        return ResponseDTO.Suc(player.buildDTO());
    }


}
