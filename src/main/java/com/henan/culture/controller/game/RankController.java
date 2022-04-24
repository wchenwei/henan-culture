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
    public ResponseDTO rankList(HttpServletRequest request){
        Player player = getLoginPlayer(request);
        if (player == null){
            return ResponseDTO.Fail("玩家不存在");
        }
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        List<LeaderboardInfo> ranks = rankService.getGroupRanks(RankType.Score, pageNo);
        return ResponseDTO.Suc().addProperty("ranks", ranks);
    }

}
