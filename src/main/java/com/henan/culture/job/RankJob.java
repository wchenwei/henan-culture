package com.henan.culture.job;

import cn.hutool.core.collection.CollUtil;
import com.henan.culture.config.GameConfig;
import com.henan.culture.config.template.impl.RankPrizeTemplateImpl;
import com.henan.culture.domain.entity.mail.Mail;
import com.henan.culture.enums.MailSendType;
import com.henan.culture.enums.RankType;
import com.henan.culture.service.IMailService;
import com.henan.culture.service.IRankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @description: 排行
 * @author: chenwei
 * @create: 2022-04-27 11:39
 **/
@Slf4j
@Component
public class RankJob {

    @Autowired
    private IRankService rankService;
    @Autowired
    private GameConfig gameConfig;
    @Autowired
    private IMailService mailService;

    @Scheduled(cron = "0 0 0 ? * MON")
    public void loadNewMail(){
        log.error("开始发放奖励");
        int endRank = gameConfig.getEndRank();
        List<RankPrizeTemplateImpl> list = gameConfig.getRankPrizeTemplateList();
        for (RankPrizeTemplateImpl template : list) {
            int start = template.getStart();
            Set<ZSetOperations.TypedTuple<String>> rankPlayers = rankService.getRankPlayers(RankType.Score, start, endRank);
            if (CollUtil.isEmpty(rankPlayers)){
                break;
            }
            StringBuilder receivers = new StringBuilder();
            for (ZSetOperations.TypedTuple<String> rankPlayer : rankPlayers) {
                receivers.append(rankPlayer.getValue()).append(",");
            }
            if (receivers.length() > 0){
                receivers.deleteCharAt(receivers.length() -1);
                String content = "恭喜您本周最获得排行奖励";
                mailService.addMail("排行奖励", content, MailSendType.One, receivers.toString(), template.getPrize1());
            }
        }
        rankService.renameRankType(RankType.Score);
        log.error("发放奖励完毕");
    }
}
