package com.henan.culture.controller.game;

import cn.hutool.core.collection.CollUtil;
import com.henan.culture.cache.PlayerCacheManager;
import com.henan.culture.controller.base.BaseController;
import com.henan.culture.domain.dto.ResponseDTO;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.domain.vo.PlayerData;
import com.henan.culture.enums.RankType;
import com.henan.culture.service.IRankService;
import com.henan.culture.utils.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-26 13:42
 **/

@RestController
@RequestMapping("/gm")
public class GmController extends BaseController {
    @Autowired
    private IRankService rankService;

    @RequestMapping("/players")
    public ResponseDTO getPlayersData(@RequestBody List<Integer> playerIds){
        List<PlayerData> players = PlayerCacheManager.getInstance().getPlayerByIds(playerIds).stream()
                .map(e -> PlayerData.buildData(e)).collect(Collectors.toList());
        return ResponseDTO.Suc().addProperty("players", JSONUtil.toJson(players));
    }

    @RequestMapping("/player")
    public ResponseDTO getPlayerData(@RequestBody Integer id){
        Player player = PlayerCacheManager.getInstance().getPlayer(id);
        if (player != null){
            return ResponseDTO.Suc().addProperty("player", JSONUtil.toJson(PlayerData.buildData(player)));
        }
        return ResponseDTO.Fail();
    }

    @RequestMapping("/update")
    public ResponseDTO update(@RequestBody PlayerData playerData){
        Player player = PlayerCacheManager.getInstance().getPlayer(playerData.getId());
        if (player != null){
            // 积分
//            player.getPlayerScore().setMaxScore(playerData.getMaxScore());
//            rankService.updatePlayerRank(player, RankType.Score, playerData.getMaxScore());
            // 道具
            Map<Integer, Long> items = playerData.getItems();
            if (CollUtil.isNotEmpty(items)){
                items.entrySet().forEach(e -> player.getPlayerBag().reset(e.getKey(), e.getValue()));
            }
            return ResponseDTO.Suc();
        }
        return ResponseDTO.Fail();
    }

}
