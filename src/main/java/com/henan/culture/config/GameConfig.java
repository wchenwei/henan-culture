package com.henan.culture.config;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.henan.culture.config.template.TuJianTemplate;
import com.henan.culture.config.template.impl.DaTiTemplateImpl;
import com.henan.culture.config.template.impl.RankPrizeTemplateImpl;
import com.henan.culture.infrastructure.config.excel.ExcleConfig;
import com.henan.culture.infrastructure.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-23 15:48
 **/
@Slf4j
@Service
public class GameConfig extends ExcleConfig {
    private Map<Integer, DaTiTemplateImpl> daTiMap = Maps.newHashMap();
    private Map<Integer, TuJianTemplate> tuJianMap = Maps.newHashMap();
    private Map<Integer, RankPrizeTemplateImpl> rankPrizeMap = Maps.newHashMap();

    @Override
    public void loadConfig() {
        loadDaTi();
        loadTuJian();
        loadRank();
    }

    private void loadDaTi() {
        List<DaTiTemplateImpl> templates = JSONUtil.fromJson(getJson(DaTiTemplateImpl.class), new TypeReference<List<DaTiTemplateImpl>>() {});
        templates.forEach(e -> e.init());
        Map<Integer, DaTiTemplateImpl> tempMap = templates.stream().collect(Collectors.toMap(DaTiTemplateImpl::getId, Function.identity()));
        daTiMap = ImmutableMap.copyOf(tempMap);
        log.error("答题加载完成");
    }

    private void loadTuJian() {
        List<TuJianTemplate> templates = JSONUtil.fromJson(getJson(TuJianTemplate.class), new TypeReference<List<TuJianTemplate>>() {});
        Map<Integer, TuJianTemplate> tempMap = templates.stream().collect(Collectors.toMap(TuJianTemplate::getId, Function.identity()));
        tuJianMap = ImmutableMap.copyOf(tempMap);
        log.error("图鉴加载完成");
    }

    private void loadRank() {
        List<RankPrizeTemplateImpl> templates = JSONUtil.fromJson(getJson(RankPrizeTemplateImpl.class), new TypeReference<List<RankPrizeTemplateImpl>>() {});
        templates.forEach(e -> e.init());
        Map<Integer, RankPrizeTemplateImpl> tempMap = templates.stream().collect(Collectors.toMap(RankPrizeTemplateImpl::getId, Function.identity()));
        rankPrizeMap = ImmutableMap.copyOf(tempMap);
        log.error("排行奖励加载完成");
    }
}
