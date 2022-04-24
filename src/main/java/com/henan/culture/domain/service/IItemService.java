package com.henan.culture.domain.service;

import com.henan.culture.domain.entity.Items;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.enums.ItemType;
import com.henan.culture.enums.LogType;

import java.util.Collection;
import java.util.List;

/**
 * @description: 道具
 * @author: chenwei
 * @create: 2022-04-24 16:36
 **/
public interface IItemService {

    void addItem(Player player, Items item, LogType logType);

    void addItem(Player player, List<Items> itemList, LogType logType);

    void addItem(Player player, ItemType itemType, int itemId, long count, LogType logType);

    boolean reduceItem(Player player, Items item, LogType logType);

    void reduceItem(Player player, List<Items> itemList, LogType logType);

    long getItemTotal(Player player, int itemTypeInt, int itemId);

    boolean checkItemEnoughAndSpend(Player player, int itemId, long count, ItemType type, LogType logType);

    boolean checkItemEnoughAndSpend(Player player, Items item, LogType logType);

    boolean checkItemEnoughAndSpend(Player player, List<Items> itemList,LogType logType);

    boolean checkItemEnough(Player player, Items item);

    boolean checkItemEnough(Player player, List<Items> itemList);

    boolean checkItemEnough(Player player, ItemType itemType, int itemId, long count);

    void clearPlayerBag(Player player, Collection<Integer> itemIdList);
}
