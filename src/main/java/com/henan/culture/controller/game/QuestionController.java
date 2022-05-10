package com.henan.culture.controller.game;

import com.henan.culture.config.GameConfig;
import com.henan.culture.config.template.impl.DaTiTemplateImpl;
import com.henan.culture.controller.base.BaseController;
import com.henan.culture.domain.dto.ResponseDTO;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.enums.RedisHashType;
import com.henan.culture.enums.StatisticsType;
import com.henan.culture.service.IItemService;
import com.henan.culture.enums.LogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 答题
 * @author: chenwei
 * @create: 2022-04-24 20:32
 **/
@RestController
@RequestMapping("/question")
public class QuestionController extends BaseController {

    @Autowired
    private GameConfig gameConfig;
    @Autowired
    private IItemService itemService;

    @RequestMapping("/answer")
    public ResponseDTO answer(HttpServletRequest request){
        Player player = getLoginPlayer(request);
        int id = Integer.parseInt(request.getParameter("id"));
        int options = Integer.parseInt(request.getParameter("options"));
        if (player == null){
            return ResponseDTO.Fail("玩家不存在");
        }
        DaTiTemplateImpl template = gameConfig.getDaTiTemplate(id);
        if (template == null){
            return ResponseDTO.Fail("参数错误");
        }
        if (!template.isRight(options)){
            player.getPlayerStatistics().addLifeStatistics(StatisticsType.QuestionWrong);
            player.saveDB();
            return ResponseDTO.Suc();
        }
        player.getPlayerStatistics().addLifeStatistics(StatisticsType.QuestionRight);
        itemService.addItem(player, template.getRewards(), LogType.Question);
        player.saveDB();
        return ResponseDTO.Suc(player.buildDTO());
    }
}
