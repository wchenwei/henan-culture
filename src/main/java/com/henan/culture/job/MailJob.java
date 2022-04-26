package com.henan.culture.job;

import com.henan.culture.cache.MailCacheManager;
import com.henan.culture.domain.entity.mail.Mail;
import com.henan.culture.repository.MailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-23 10:53
 **/
@Slf4j
@Component
public class MailJob {
    @Autowired
    private MailRepository mailRepository;

    @Scheduled(cron="*/5 * * * * ?")
    public void loadNewMail(){
        List<Mail> mailList = mailRepository.findByOldMailFalse();
        mailList.forEach(e ->{
            e.setOldMail(true);
            e.init();
            mailRepository.save(e);
            MailCacheManager.getInstance().addMail(e);
            log.error("加载邮件："+ mailList);
        });
    }
}
