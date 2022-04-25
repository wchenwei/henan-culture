package com.henan.culture.listener;

import com.henan.culture.cache.MailCacheManager;
import com.henan.culture.utils.config.excel.ConfigLoad;
import com.henan.culture.utils.springredis.factory.MapperFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            log.error("===============================服务器开始启动===============================");
            ConfigLoad.loadAllConfig();
            //注册redisDB
            MapperFactory.getFactory().registerDB();
            //加载邮件
            MailCacheManager.getInstance().init();
            log.error("加载邮件完成");

            log.info("服务器启动成功");
        } catch (Exception e) {
            log.error("服务器启动失败", e);
        }
    }

}