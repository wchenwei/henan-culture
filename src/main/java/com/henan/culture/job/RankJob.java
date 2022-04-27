package com.henan.culture.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @description: 排行
 * @author: chenwei
 * @create: 2022-04-27 11:39
 **/
@Slf4j
@Component
public class RankJob {


    @Scheduled(cron="*/5 * * * * ?")
    public void loadNewMail(){

    }
}
