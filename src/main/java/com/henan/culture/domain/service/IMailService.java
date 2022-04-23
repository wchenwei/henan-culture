package com.henan.culture.domain.service;

import com.henan.culture.domain.entity.mail.Mail;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.enums.MailSendType;

import java.util.List;

/**
 * @description: 邮件
 * @author: chenwei
 * @create: 2022-04-22 18:14
 **/
public interface IMailService {
    List<Mail> getPlayerMail(Player player);

    void addMail(String title, String content, MailSendType sendType, String receivers, String reward);
}
