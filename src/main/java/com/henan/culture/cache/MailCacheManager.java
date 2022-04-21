package com.henan.culture.cache;

import com.google.common.collect.Maps;
import com.henan.culture.domain.entity.mail.Mail;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.enums.MailSendType;

import java.util.List;
import java.util.Map;

/**
 * @description: 邮件
 * @author: chenwei
 * @create: 2022-04-20 11:25
 **/
public class MailCacheManager {

    private static final MailCacheManager instance = new MailCacheManager();
    public static MailCacheManager getInstance() {
        return instance;
    }

    private Map<String, Mail> mailMap = Maps.newConcurrentMap();



    public void addMail(Mail mail){
        mailMap.put(mail.getId(), mail);
    }

    public void init(){
//        List<Mail> mail = Mail.queryListAll(0, Mail.class);
//        mail.forEach(e -> addMail(e));
    }

    /**
     * 登陆时检查是否有系统邮件
     * @param player
     */
    public void loadPlayerSysMail(Player player) {
        // 判断是否有新的全服邮件
        for(Mail mail :mailMap.values()) {
            if(isAddPlayerMail(player, mail)) {
                player.getPlayerMail().addMail(mail);
            }
        }
    }

    /**
     * 获取邮件
     *
     * @author yanpeng
     * @param id
     * @return
     *
     */
    public Mail getMail(String id) {
        Mail mail = mailMap.get(id);
        if(mail == null) {
            mail = Mail.queryOne(0, mail.getId(), Mail.class);
        }
        return mail;
    }


    /**
     * 判断玩家是否有未发送的系统邮件
     *
     * @author yanpeng
     * @param player
     * @param mail
     * @return
     *
     */
    private boolean isAddPlayerMail(Player player, Mail mail) {
        if(mail.getSendType() == MailSendType.All.getType() || mail.getReceivers().contains(player.getId())) {
            if(!player.getPlayerMail().isHaveMail(mail)) {
                return true;
            }
        }
        return false;
    }
}
