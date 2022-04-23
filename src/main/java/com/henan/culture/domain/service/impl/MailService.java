package com.henan.culture.domain.service.impl;

import com.henan.culture.cache.MailCacheManager;
import com.henan.culture.domain.entity.mail.Mail;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.domain.repository.MailRepository;
import com.henan.culture.domain.service.IMailService;
import com.henan.culture.enums.MailSendType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-22 18:15
 **/
@Service
public class MailService implements IMailService {
    @Autowired
    private MailRepository mailRepository;

    @Override
    public List<Mail> getPlayerMail(Player player) {
        MailCacheManager.getInstance().loadPlayerSysMail(player);
        List<Integer> idList = player.getPlayerMail().getMailIdList();
        List<Mail> mailList = idList.stream().map(e -> MailCacheManager.getInstance().getMail(e))
                .filter(Objects::nonNull).sorted(Comparator.comparingLong(Mail::getSendDateTime).reversed())
                .collect(Collectors.toList());
        return mailList;
    }

    @Override
    public void addMail(String title, String content, MailSendType sendType, String receivers, String reward) {
        Mail mail = new Mail(title,content, reward, sendType, receivers);
        mailRepository.save(mail);
    }

}
