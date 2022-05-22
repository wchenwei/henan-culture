package com.henan.culture.controller.game;

import com.henan.culture.config.GameConfig;
import com.henan.culture.config.template.impl.LittleGameTemplateImpl;
import com.henan.culture.config.template.impl.ZhuanPanTemplateImpl;
import com.henan.culture.controller.base.BaseController;
import com.henan.culture.domain.dto.ResponseDTO;
import com.henan.culture.domain.entity.Items;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.enums.ItemType;
import com.henan.culture.enums.LogType;
import com.henan.culture.service.IItemService;
import com.henan.culture.service.IPlayerService;
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
    @Autowired
    private IItemService iItemService;
    @Autowired
    private GameConfig gameConfig;

    /**
     * 开始新一局
     *
     * @param request
     * @return
     */
    @RequestMapping("/restart")
    public ResponseDTO restart(HttpServletRequest request) {
        Player player = getLoginPlayer(request);
        if (player == null) {
            return ResponseDTO.Fail("玩家不存在");
        }
        playerService.checkDayReset(player);
        player.saveDB();
        return ResponseDTO.Suc(player);
    }

    /**
     * 更新最大章节
     * @param request
     * @return
     */
    @RequestMapping("/chapter")
    public ResponseDTO chapter(HttpServletRequest request) {
        Player player = getLoginPlayer(request);
        int chapter = Integer.parseInt(request.getParameter("id"));
        if (player == null) {
            return ResponseDTO.Fail("玩家不存在");
        }
        if (player.getPlayerChapter().checkAndResetChapter(chapter)) {
            player.saveDB();
        }
        return ResponseDTO.Suc();
    }

    /**
     * 消耗道具
     * @param request
     * @return
     */
    @RequestMapping("/costItem")
    public ResponseDTO costItem(HttpServletRequest request) {
        int itemId = Integer.parseInt(request.getParameter("itemId"));
        int itemType = Integer.parseInt(request.getParameter("itemType"));

        Player player = getLoginPlayer(request);
        if (player == null) {
            return ResponseDTO.Fail("玩家不存在");
        }
        ItemType type = ItemType.getItemType(itemType);
        if (type == null) {
            return ResponseDTO.Fail("道具不存在");
        }
        if (!iItemService.checkItemEnoughAndSpend(player, new Items(itemId, 1L, type), LogType.ItemCost)) {
            return ResponseDTO.Fail("道具不足");
        }
        player.saveDB();
        return ResponseDTO.Suc(player.buildDTO());
    }

    @RequestMapping("/zhuanpan")
    public ResponseDTO zhuanpan(HttpServletRequest request) {

        Player player = getLoginPlayer(request);
        if (player == null) {
            return ResponseDTO.Fail("玩家不存在");
        }
        int id = gameConfig.randomZPReward();
        ZhuanPanTemplateImpl template = gameConfig.getZhuanPanTemplate(id);
        iItemService.addItem(player, template.getItems(), LogType.ZhuanPan);
        player.saveDB();
        return ResponseDTO.Suc(player.buildDTO())
                .addProperty("id", id)
                .addProperty("rewards", template.getItems());
    }

    @RequestMapping("/littleGame")
    public ResponseDTO little(HttpServletRequest request) {

        Player player = getLoginPlayer(request);
        if (player == null) {
            return ResponseDTO.Fail("玩家不存在");
        }

        int id = Integer.parseInt(request.getParameter("id"));
        LittleGameTemplateImpl template = gameConfig.getLittleGameTemplate(id);
        Items items = template.random();
        iItemService.addItem(player, items, LogType.ZhuanPan);
        player.saveDB();
        return ResponseDTO.Suc(player.buildDTO())
                .addProperty("rewards", items);
    }


}
