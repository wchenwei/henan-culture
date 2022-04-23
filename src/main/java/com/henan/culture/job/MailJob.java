package com.henan.culture.job;

import com.henan.culture.cache.MailCacheManager;
import com.henan.culture.domain.entity.mail.Mail;
import com.henan.culture.domain.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-23 10:53
 **/
@Component
public class MailJob {
    @Autowired
    private MailRepository mailRepository;

    @Scheduled(cron="*/3 * * * * ?")
    public void loadNewMail(){
        List<Mail> mailList = mailRepository.findByOldMailFalse();
        mailList.forEach(e ->{
            e.setOldMail(true);
            e.init();
            mailRepository.save(e);
            MailCacheManager.getInstance().addMail(e);
        });
        System.err.println("加载邮件："+ mailList);
    }
}
