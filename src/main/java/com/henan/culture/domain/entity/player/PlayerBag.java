package com.henan.culture.domain.entity.player;

import com.google.common.collect.Maps;
import com.henan.culture.enums.ItemType;

import java.util.Map;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-19 10:45
 **/
public class PlayerBag {
    private Map<Integer, Integer> itemMap = Maps.newConcurrentMap();


    public void addItem(ItemType itemType, int add){
        itemMap.put(itemType.getType(), itemMap.getOrDefault(itemType.getType(),0) + add);
    }

    public void sendItem(ItemType itemType, int reduce){
        itemMap.put(itemType.getType(), itemMap.getOrDefault(itemType.getType(),0) - reduce);
    }


    public boolean checkEnough(ItemType itemType, int reduce){
        return itemMap.getOrDefault(itemType.getType(), 0) >= reduce;
    }

    public boolean checkAndSpendItem(ItemType itemType, int reduce){
        if (checkEnough(itemType, reduce)){
            sendItem(itemType,reduce);
            return true;
        }
        return false;
    }
}
