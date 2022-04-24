package com.henan.culture.infrastructure.util;

import com.google.common.collect.Maps;
import com.henan.culture.enums.ItemType;

import java.util.Iterator;
import java.util.Map;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-22 19:31
 **/
public class ItemUtil {

    public static Map<ItemType, Long> buildItemMap(String itemsStr){
        Map<ItemType, Long> itemMap = Maps.newHashMap();
        try {
            String[] items = itemsStr.split(",");
            for (String itemStr : items) {
                String[] item = itemStr.split(":");
                ItemType itemType = ItemType.getItemType(Integer.parseInt(item[0]));
                if (itemType != null){
                    itemMap.put(itemType, Long.parseLong(item[1]));
                }
            }
        } catch (NumberFormatException e) {
        }
        return itemMap;
    }

    public static Map<ItemType, Long> convertItemMap(Map<Integer, Long> itemMap){
        Map<ItemType, Long> itemTypeMap = Maps.newHashMap();
        Iterator<Map.Entry<Integer, Long>> iterator = itemMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer, Long> entry = iterator.next();
            ItemType itemType = ItemType.getItemType(entry.getKey());
            if (itemType != null){
                itemTypeMap.put(itemType, entry.getValue());
            }
        }
        return itemTypeMap;
    }
}
