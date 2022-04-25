package com.henan.culture.controller.game;

import com.henan.culture.controller.base.BaseController;
import com.henan.culture.domain.dto.ResponseDTO;
import com.henan.culture.domain.entity.LeaderboardInfo;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.service.IRankService;
import com.henan.culture.enums.RankType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-24 17:52
 **/
@RestController
@RequestMapping("/rank")
public class RankController extends BaseController {

    @Autowired
    private IRankService rankService;

    @RequestMapping("/list")
    public ResponseDTO list(HttpServletRequest request) {
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        Player player = getLoginPlayer(request);
        if (player == null){
            return ResponseDTO.Fail("玩家不存在");
        }
        long playerRank = rankService.getPlayerRank(player, RankType.Score);
        List<LeaderboardInfo> groupRanks = rankService.getGroupRanks(RankType.Score, pageNo);
        return ResponseDTO.Suc()
                .addProperty("myRank", playerRank)
                .addProperty("rankInfos", groupRanks)
                ;
    }

}
