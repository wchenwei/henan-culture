package com.henan.culture.domain.dto;

import com.google.common.collect.Lists;
import com.henan.culture.domain.entity.mail.Mail;
import com.henan.culture.domain.entity.player.Player;
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
public class MailDTO extends BasePlayerDTO {
    private List<Mail> mailList = Lists.newArrayList();

    public MailDTO(Player player) {
        super(player);
    }
}
