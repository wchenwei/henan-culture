package com.henan.culture.controller.game;

import com.henan.culture.controller.base.BaseController;
import com.henan.culture.domain.dto.ResponseDTO;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.domain.service.IItemService;
import com.henan.culture.enums.ItemType;
import com.henan.culture.enums.LogType;
import com.henan.culture.enums.ShareType;
import com.henan.culture.infrastructure.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-23 14:16
 **/
@RestController
@RequestMapping("/share")
public class ShareController extends BaseController {

    @Autowired
    private IItemService itemService;

    @RequestMapping("/add")
    public ResponseDTO add(HttpServletRequest request) {
        String type = request.getParameter("type");// 分享类型
        Player player = getLoginPlayer(request);
        if (player == null){
            return ResponseDTO.Fail("玩家不存在");
        }
        ShareType shareType = ShareType.getShareType(Integer.valueOf(type));
        if (shareType == null) {
            return ResponseDTO.Fail("分享类型不存在");
        }
        if (player.getPlayerShare().getShareCount(shareType) < Constants.Max_Share_Count){
            // 分享得重生道具
            if (shareType == ShareType.Relive) {
                itemService.addItem(player, ItemType.Relive, Constants.Relive_Item_Id, 1, LogType.Share.value(shareType.getDesc()));
            }
            player.getPlayerShare().addShare(shareType);
            player.saveDB();
        }
        return ResponseDTO.Suc(player.buildDTO());
    }
}
