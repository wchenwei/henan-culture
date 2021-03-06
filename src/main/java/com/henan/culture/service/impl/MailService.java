package com.henan.culture.service.impl;

import com.henan.culture.cache.MailCacheManager;
import com.henan.culture.domain.entity.mail.Mail;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.enums.RedisHashType;
import com.henan.culture.repository.MailRepository;
import com.henan.culture.service.IMailService;
import com.henan.culture.enums.MailSendType;
import com.henan.culture.utils.util.Constants;
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
        long now = System.currentTimeMillis();
        List<Integer> idList = player.getPlayerMail().getMailIdList();
        List<Mail> mailList = idList.stream().map(e -> MailCacheManager.getInstance().getMail(e))
                .filter(Objects::nonNull)
                .filter(e -> now - e.getSendDateTime() < 7 * Constants.Day)
                .sorted(Comparator.comparingLong(Mail::getSendDateTime).reversed())
                .collect(Collectors.toList());
        return mailList;
    }

    @Override
    public void addMail(String title, String content, MailSendType sendType, String receivers, String reward) {
        Mail mail = new Mail(title,content, reward, sendType);
        mailRepository.save(mail);
        if (MailSendType.All != sendType){
            RedisHashType.MailReceivers.put(mail.getId(), receivers);
        }
    }

    @Override
    public void addMail(String title, String content, MailSendType sendType, String receivers) {
        addMail(title, content, sendType, receivers, null);
    }

}
