package com.henan.culture.controller.game;

import com.henan.culture.config.GameConfig;
import com.henan.culture.config.template.impl.DaTiTemplateImpl;
import com.henan.culture.config.template.impl.PinTuTemplateImpl;
import com.henan.culture.controller.base.BaseController;
import com.henan.culture.domain.dto.ResponseDTO;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.enums.LogType;
import com.henan.culture.enums.RedisHashType;
import com.henan.culture.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 拼图
 * @author: chenwei
 * @create: 2022-04-24 20:32
 **/
@RestController
@RequestMapping("/pinTu")
public class PinTuController extends BaseController {

    @Autowired
    private GameConfig gameConfig;
    @Autowired
    private IItemService itemService;

    @RequestMapping("/reward")
    public ResponseDTO reward(HttpServletRequest request){
        Player player = getLoginPlayer(request);
        int id = Integer.parseInt(request.getParameter("id"));
        int result = Integer.parseInt(request.getParameter("result"));// 0失败 1成功
        if (player == null){
            return ResponseDTO.Fail("玩家不存在");
        }
        PinTuTemplateImpl template = gameConfig.getPinTuTemplate(id);
        if (template == null){
            return ResponseDTO.Fail("参数错误");
        }
        if (result <= 0){
            RedisHashType.PinTuFail.incrementKey(player.getId());
            return ResponseDTO.Suc();
        }
        RedisHashType.PinTuSuc.incrementKey(player.getId());
        itemService.addItem(player, template.getRewards(), LogType.Question);
        player.saveDB();
        return ResponseDTO.Suc(player.buildDTO());
    }
}
