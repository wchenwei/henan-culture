package com.henan.culture.utils.weight;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.henan.culture.domain.entity.Items;
import com.henan.culture.utils.util.ItemUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class RandomUtils extends RandomUtil {
	/**
	 * 构造道具随机
	 * @param items
	 * @return
	 */
	public static WeightMeta<Items> buildItemWeightMeta(String items) {
		Map<Items, Integer> rewardMap = Maps.newHashMap();
		for (String item : items.split(",")) {
			WeightItem witem = ItemUtils.str2WeightItem(item, ":");
			rewardMap.put(witem.getItems(), witem.getWeight());
		}
        return RandomUtils.buildWeightMeta(rewardMap);
	}
	
    public static <T> WeightMeta<T> buildWeightMeta(final Map<T, Integer> weightMap) {
        final int size = weightMap.size();
        Object[] nodes = new Object[size];
        int[] weights = new int[size];
        int index = 0;
        int weightAdder = 0;
        for (Map.Entry<T, Integer> each : weightMap.entrySet()) {
        	nodes[index] = each.getKey();
    		weights[index++] = (weightAdder = weightAdder + each.getValue());
        }
        return new WeightMeta<T>((T[]) nodes, weights);
    }

    
    //概率满足
    public static boolean randomIsRate(double rate) {
    	return RandomUtil.randomDouble() <= rate;
    }
    
    public static <T> List<T> randomEleList(Collection<T> collection, int count) {
    	if(collection.size() <= count) {
    		return Lists.newArrayList(collection);
    	}
    	return Lists.newArrayList(RandomUtil.randomEleSet(collection, count));
    }
    
    public static <T> List<T> randomEleListForAverage(List<T> collection, int count) {
    	if(collection.size() <= count) {
    		return Lists.newArrayList(collection);
    	}
    	List<T> luckList = Lists.newArrayList();
    	int size = collection.size()/count;
    	if(size <= 1) {
    		return randomEleList(collection,count);
    	}
    	for (int i = 0; i < count; i++) {
			int start = i*size;
			int end = (i+1)*size;
			luckList.add(RandomUtil.randomEle(collection.subList(start, end)));
		}
    	return luckList;
    }
    
    public static <T> List<T> randomRepeatableEleList(List<T> collection, int count) {
    	List<T> list = Lists.newArrayList();
    	for (int i = 0; i < count; i++) {
    		list.add(RandomUtil.randomEle(collection));
		}
    	return list;
    }
    
    public static int randomIntForEnd(int min,int max) {
    	if(max+1 <= min) {
    		return max;
    	}
    	return randomInt(min, max+1);
    }
    
    public static void main(String[] args) {
		System.err.println(new DateTime(1563206400000L));
		System.err.println(new DateTime(1563379200000L));
	}
}

