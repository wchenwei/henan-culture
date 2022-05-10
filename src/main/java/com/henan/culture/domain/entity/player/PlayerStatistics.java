package com.henan.culture.domain.entity.player;

import com.henan.culture.enums.StatisticsType;
import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author siyunlong
 * @version V1.0
 * @Description: 玩家统计数据
 * @date 2018年1月3日 下午2:32:29
 */
public class PlayerStatistics{
    //此统计不会清空,一直累加
    @Getter
    private ConcurrentHashMap<Integer, Long> lifeStatistics = new ConcurrentHashMap<>();
    //此统计会每日重置
    private ConcurrentHashMap<Integer, Long> todayStatistics = new ConcurrentHashMap<>();

    //添加累计事件统计
    public void addLifeStatistics(StatisticsType statisticsType, long num) {
        this.lifeStatistics.put(statisticsType.getType(), getLifeStatistics(statisticsType) + num);
    }

    public void addLifeStatistics(StatisticsType statisticsType) {
        addLifeStatistics(statisticsType, 1);
    }

    public long getLifeStatistics(StatisticsType statisticsType) {
        return this.lifeStatistics.getOrDefault(statisticsType.getType(), 0L);
    }

    //添加每日事件统计
    public synchronized void addTodayStatistics(StatisticsType statisticsType, long num) {
        this.todayStatistics.put(statisticsType.getType(), getTodayStatistics(statisticsType) + num);
    }

    public void addTodayStatistics(StatisticsType statisticsType) {
        addTodayStatistics(statisticsType, 1);
    }

    public long getTodayStatistics(StatisticsType statisticsType) {
        return this.todayStatistics.getOrDefault(statisticsType.getType(), 0L);
    }

    public void clearTodayStatistics(StatisticsType statisticsType) {
        if (this.todayStatistics.containsKey(statisticsType.getType())) {
            this.todayStatistics.remove(statisticsType.getType());
        }
    }


    //清空每日数据
    public void clearTodayData() {
        this.todayStatistics.clear();
    }

}

