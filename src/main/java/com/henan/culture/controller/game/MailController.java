package com.henan.culture.controller.game;

import com.henan.culture.cache.MailCacheManager;
import com.henan.culture.controller.base.BaseController;
import com.henan.culture.domain.dto.ResponseDTO;
import com.henan.culture.domain.entity.Items;
import com.henan.culture.domain.entity.mail.Mail;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.enums.ItemType;
import com.henan.culture.enums.LogType;
import com.henan.culture.enums.MailSendType;
import com.henan.culture.enums.MailState;
import com.henan.culture.service.IItemService;
import com.henan.culture.service.IMailService;
import com.henan.culture.utils.util.ItemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-20 13:42
 **/
@RequestMapping("/mail")
@RestController
public class MailController extends BaseController {
    @Autowired
    private IMailService mailService;
    @Autowired
    private IItemService itemService;

    @RequestMapping("/list")
    public ResponseDTO list(HttpServletRequest request){
        Player player = getLoginPlayer(request);
        if (player == null){
            return ResponseDTO.Fail("玩家不存在");
        }
        return ResponseDTO.Suc().addMail(player);
    }

    @RequestMapping("/read")
    public ResponseDTO read(HttpServletRequest request){
        Player player = getLoginPlayer(request);
        if (player == null){
            return ResponseDTO.Fail("玩家不存在");
        }
        int id = Integer.parseInt(request.getParameter("id"));
        Mail mail = MailCacheManager.getInstance().getMail(id);
        if(mail == null){
            return ResponseDTO.Fail("邮件不存在");
        }
        int mailState = player.getPlayerMail().getMailState(id);
        if(mailState == MailState.Read.getType()
                || mailState == MailState.ReadNoGet.getType()
                || mailState == MailState.Get.getType()
                || mailState == MailState.Del.getType()
        ) {
            return ResponseDTO.Suc();
        }
        player.getPlayerMail().readMail(mail);
        player.saveDB();
        return ResponseDTO.Suc().addProperty("state", player.getPlayerMail().getMailState(id));
    }

    @RequestMapping("/getReward")
    public ResponseDTO getReward(HttpServletRequest request){
        Player player = getLoginPlayer(request);
        if (player == null){
            return ResponseDTO.Fail("玩家不存在");
        }
        int id = Integer.parseInt(request.getParameter("id"));
        Mail mail = MailCacheManager.getInstance().getMail(id);
        if(mail == null){
            return ResponseDTO.Fail();
        }
        //是否有附件
        if(!player.getPlayerMail().canGetReward(mail)){
            return ResponseDTO.Fail();
        }
        //是否已领
        if(player.getPlayerMail().getMailState(mail.getId()) == MailState.Get.getType()){
            return ResponseDTO.Fail();
        }
        List<Items> items = mail.getRewardItems();
        player.getPlayerMail().getReward(id);
        itemService.addItem(player, items, LogType.Mail);
        if (ItemUtils.isContains(items, ItemType.RealGoods)){
            // TODO 包含实物奖励重新发一个通知奖励 ? 奖励内容
            mailService.addMail("实物奖励","实物奖励领取方式：请联系XX,联系方式1xxx", MailSendType.One, player.getId().toString());
        }
        player.getPlayerMail().getReward(id);
        player.saveDB();

        return ResponseDTO.Suc(player.buildDTO());
    }

}
