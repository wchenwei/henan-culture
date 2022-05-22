package com.henan.culture.config;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.*;
import com.henan.culture.config.template.LittleGameTemplate;
import com.henan.culture.config.template.TuJianTemplate;
import com.henan.culture.config.template.ZhuanPanTemplate;
import com.henan.culture.config.template.impl.*;
import com.henan.culture.domain.entity.Items;
import com.henan.culture.utils.config.excel.ExcleConfig;
import com.henan.culture.utils.util.JSONUtil;
import com.henan.culture.utils.weight.RandomUtils;
import com.henan.culture.utils.weight.WeightMeta;
import lombok.Getter;
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
    private Map<Integer, PinTuTemplateImpl> pinTuMap = Maps.newHashMap();
    private List<RankPrizeTemplateImpl> rankList = Lists.newArrayList();
    private ListMultimap<Integer, Integer> tuJianListMap = ArrayListMultimap.create();
    private WeightMeta<Integer> weightMeta;
    private Map<Integer, LittleGameTemplateImpl> littleGameTemplateMap = Maps.newHashMap();
    private Map<Integer, ZhuanPanTemplateImpl> zhuanPanMap = Maps.newHashMap();

    @Getter
    private int endRank;

    @Override
    public void loadConfig() {
        loadDaTi();
        loadTuJian();
        loadRank();
        loadPinTu();
        loadZhuanPan();
        loadLittleGame();
    }

    private void loadLittleGame() {
        List<LittleGameTemplateImpl> list = JSONUtil.fromJson(getJson(LittleGameTemplateImpl.class), new TypeReference<List<LittleGameTemplateImpl>>() {
        });
        list.forEach(e -> {
            e.init();
        });
        this.littleGameTemplateMap = list.stream().collect(Collectors.toMap(LittleGameTemplate::getId, Function.identity()));
    }

    private void loadZhuanPan() {
        List<ZhuanPanTemplateImpl> list = JSONUtil.fromJson(getJson(ZhuanPanTemplateImpl.class), new TypeReference<List<ZhuanPanTemplateImpl>>() {
        });
        Map<Integer,Integer> reward = Maps.newHashMap();
        list.forEach(e -> {
            e.init();
            reward.put(e.getId(), e.getWeight());
        });
        weightMeta = RandomUtils.buildWeightMeta(reward);
        this.zhuanPanMap = list.stream().collect(Collectors.toMap(ZhuanPanTemplate::getId, Function.identity()));
    }

    private void loadPinTu() {
        List<PinTuTemplateImpl> templates = JSONUtil.fromJson(getJson(PinTuTemplateImpl.class), new TypeReference<List<PinTuTemplateImpl>>() {});
        templates.forEach(e -> e.init());
        Map<Integer, PinTuTemplateImpl> tempMap = templates.stream().collect(Collectors.toMap(PinTuTemplateImpl::getId, Function.identity()));
        pinTuMap = ImmutableMap.copyOf(tempMap);
        log.error("拼图加载完成");
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
        templates.forEach(e -> tuJianListMap.put(e.getType(), e.getId()));
        log.error("图鉴加载完成");
    }

    private void loadRank() {
        List<RankPrizeTemplateImpl> templates = JSONUtil.fromJson(getJson(RankPrizeTemplateImpl.class), new TypeReference<List<RankPrizeTemplateImpl>>() {});
        templates.forEach(e -> {
            e.init();
            this.endRank = Math.max(endRank, e.getEnd());
        });
        Map<Integer, RankPrizeTemplateImpl> tempMap = templates.stream().collect(Collectors.toMap(RankPrizeTemplateImpl::getId, Function.identity()));
        rankPrizeMap = ImmutableMap.copyOf(tempMap);
        rankList = ImmutableList.copyOf(templates);
        log.error("排行奖励加载完成");
    }

    public Integer randomZPReward(){
        return this.weightMeta.random();
    }

    public LittleGameTemplateImpl getLittleGameTemplate(int id){
        return littleGameTemplateMap.get(id);
    }

    public ZhuanPanTemplateImpl getZhuanPanTemplate(int id){
        return zhuanPanMap.get(id);
    }

    public DaTiTemplateImpl getDaTiTemplate(int id){
        return daTiMap.get(id);
    }

    public TuJianTemplate getTuJianTemplate(int id){
        return tuJianMap.get(id);
    }

    public PinTuTemplateImpl getPinTuTemplate(int id){
        return pinTuMap.get(id);
    }

    public RankPrizeTemplateImpl getRankPrizeTemplate(int rank){
        return rankList.stream().filter(e -> e.isFit(rank)).findFirst().orElse(null);
    }

    public List<RankPrizeTemplateImpl> getRankPrizeTemplateList(){
        return rankPrizeMap.values().stream().collect(Collectors.toList());
    }

    public List<Integer> getTuJianIdsByType(int type){
        return tuJianListMap.get(type);
    }
}
