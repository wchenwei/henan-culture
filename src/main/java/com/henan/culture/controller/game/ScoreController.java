package com.henan.culture.controller.game;

import com.henan.culture.controller.base.BaseController;
import com.henan.culture.domain.dto.ResponseDTO;
import com.henan.culture.domain.entity.LeaderboardInfo;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.domain.service.IRankService;
import com.henan.culture.enums.RankType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        int score = Integer.parseInt(request.getParameter("score"));
        Player player = getLoginPlayer(request);
        if (player.getPlayerScore().checkAndReset(score)){
            rankService.updatePlayerRank(player, RankType.Score, score);
            player.saveDB();
        }
        return ResponseDTO.Suc(player);
    }

    @RequestMapping("/rank")
    public ResponseDTO rank(HttpServletRequest request) {
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        Player player = getLoginPlayer(request);
        long playerRank = rankService.getPlayerRank(player, RankType.Score);
        List<LeaderboardInfo> groupRanks = rankService.getGroupRanks(RankType.Score, pageNo);
        return ResponseDTO.Suc(player)
                .addProperty("playerRank", playerRank)
                .addProperty("ranks", groupRanks)
                ;
    }

}
