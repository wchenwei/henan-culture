package com.henan.culture.domain.entity.player;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-19 10:45
 **/
@Getter
public class PlayerBag {
    private Map<Integer, Long> itemMap = Maps.newConcurrentMap();


    public void addItem(int itemId, long add){
        itemMap.merge(itemId, add, Long::sum);
    }

    public void addItem(Map<Integer, Long> addItemMap){
        addItemMap.forEach((key, value)-> itemMap.merge(key ,value, Long::sum));
    }

    public void spendItem(int itemId, long reduce){
        itemMap.put(itemId, itemMap.getOrDefault(itemId,0l) - reduce);
    }

    public boolean checkEnough(int itemId, long reduce){
        return itemMap.getOrDefault(itemId, 0L) >= reduce;
    }

    public long getItemTotal(int itemId){
        return itemMap.getOrDefault(itemId, 0L);
    }

    public void removeItem(Integer itemId){
        this.itemMap.remove(itemId);
    }

    public boolean checkAndSpendItem(int itemId, long reduce){
        if (checkEnough(itemId, reduce)){
            spendItem(itemId, reduce);
            return true;
        }
        return false;
    }

    public void reset(int itemId, long value){
        this.itemMap.put(itemId, value);
    }
}
