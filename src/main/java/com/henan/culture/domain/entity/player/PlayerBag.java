package com.henan.culture.domain.entity.player;

import com.google.common.collect.Maps;
import com.henan.culture.enums.ItemType;
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


    public void addItem(ItemType itemType, long add){
        itemMap.put(itemType.getType(), itemMap.getOrDefault(itemType.getType(),0l) + add);
    }

    public void sendItem(ItemType itemType, long reduce){
        itemMap.put(itemType.getType(), itemMap.getOrDefault(itemType.getType(),0l) - reduce);
    }


    public boolean checkEnough(ItemType itemType, long reduce){
        return itemMap.getOrDefault(itemType.getType(), 0L) >= reduce;
    }

    public boolean checkAndSpendItem(ItemType itemType, long reduce){
        if (checkEnough(itemType, reduce)){
            sendItem(itemType,reduce);
            return true;
        }
        return false;
    }
}
