package com.henan.culture.domain.dto;

import com.google.common.collect.Lists;
import com.henan.culture.domain.entity.mail.Mail;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.domain.service.IMailService;
import com.henan.culture.infrastructure.util.SpringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-22 18:10
 **/
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class MailDTO {
    private List<MailVO> mailList = Lists.newArrayList();

    public MailDTO(Player player) {
        loadMailVOList(player);
    }

    public void loadMailVOList(Player player){
        IMailService mailService = SpringUtil.getBean(IMailService.class);
        for(Mail mail :mailService.getPlayerMail(player)) {
            mailList.add(new MailVO(player, mail));
        }
    }
}
