package com.henan.culture.service.impl;

import com.henan.culture.domain.entity.Items;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.service.IItemService;
import com.henan.culture.enums.ItemType;
import com.henan.culture.enums.LogType;
import com.henan.culture.utils.util.ItemUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-24 16:37
 **/
@Service
public class ItemService implements IItemService {

    /**
     * 添加道具
     * 没有通知玩家道具变化，没有保存数据
     */
    @Override
    public void addItem(Player player, Items item, LogType logType) {
        addItem(player,ItemType.getItemType(item.getType()),item.getId(),item.getCount(),logType);
    }

    @Override
    public void addItem(Player player, List<Items> itemList, LogType logType) {
        itemList.forEach(item -> addItem(player,item,logType));
    }

    /**
     * 添加道具
     * 没有通知玩家道具变化，没有保存数据
     * @param player
     * @param itemType
     * @param itemId
     * @param num
     * @param logType 使用说明
     */
    @Override
    public void addItem(Player player, ItemType itemType, int itemId, long count, LogType logType) {
        if(count == 0) {
            return;
        }
        if(itemType != null) {
            switch(itemType) {
                case Help:
                case Score:
                case Relive:
                    player.getPlayerBag().addItem(itemId, count);
                    return;
            }
        }
    }
    /**
     * 消耗道具
     * 没有通知玩家道具变化，没有保存数据
     */
    @Override
    public boolean reduceItem(Player player, Items item, LogType logType) {
        return reduceItem(player,ItemType.getItemType(item.getType()),item.getId(),item.getCount(),logType);
    }

    @Override
    public void reduceItem(Player player, List<Items> itemList, LogType logType) {
        itemList.forEach(item -> reduceItem(player, item, logType));
    }

    /**
     * 消耗道具
     *  没有通知玩家道具变化，没有保存数据
     * @param player
     * @param itemType
     * @param itemId
     * @param count
     * @return 使用说明
     */
    private boolean reduceItem(Player player, ItemType itemType, int itemId, long count, LogType logType) {
        if(itemType!=null) {
            switch(itemType) {
                case Help:
                case Score:
                case Relive:
                     player.getPlayerBag().spendItem(itemId, count);
                     return true;

            }
            return true;
        }
        return false;
    }

    @Override
    public long getItemTotal(Player player, int itemTypeInt, int itemId) {
        long total = 0;
        ItemType itemType = ItemType.getItemType(itemTypeInt);
        switch(itemType) {
            case Help:
            case Score:
            case Relive:
                return player.getPlayerBag().getItemTotal(itemId);
        }
        return total;
    }

    //检查并消耗道具
    @Override
    public boolean checkItemEnoughAndSpend(Player player, int itemId, long count, ItemType type, LogType logType) {
        Items item = new Items(itemId);
        item.setItemType(type);
        item.setCount(count);
        return checkItemEnoughAndSpend(player, item, logType);
    }

    @Override
    public boolean checkItemEnoughAndSpend(Player player, Items item, LogType logType) {
        if(checkItemEnough(player, item)) {
            return reduceItem(player, item, logType);
        }
        return false;
    }
    //检查并消耗道具

    @Override
    public boolean checkItemEnoughAndSpend(Player player, List<Items> itemList,LogType logType) {
        List<Items> newItemList = ItemUtils.mergeItemList(itemList);
        if(checkItemEnough(player, newItemList)) {
            reduceItem(player, newItemList, logType);
            return true;
        }
        return false;
    }
    /**
     * 检查道具是否足够
     */
    @Override
    public boolean checkItemEnough(Player player, Items item) {
        return checkItemEnough(player,ItemType.getItemType(item.getType()),item.getId(),item.getCount());
    }

    @Override
    public boolean checkItemEnough(Player player, List<Items> itemList) {
        return itemList.stream().allMatch(item -> null!=item&&checkItemEnough(player, item));
    }

    /**
     * 检查道具是否足够
     * @author zigm
     * @param player
     * @param itemType
     * @param itemId
     * @param num
     * @return 使用说明
     */
    @Override
    public boolean checkItemEnough(Player player, ItemType itemType, int itemId, long count) {
        if(itemType!=null) {
            switch(itemType) {
                case Help:
                case Score:
                case Relive:
                    return player.getPlayerBag().checkEnough(itemId,count);
            }
        }
        return false;
    }


    /**
     * 清空玩家的背包中的道具
     * @param player
     * @param itemIdList
     */
    @Override
    public void clearPlayerBag(Player player, Collection<Integer> itemIdList) {
        for (int itemId : itemIdList) {
            long count = player.getPlayerBag().getItemTotal(itemId);
            if(count > 0) {
                player.getPlayerBag().removeItem(itemId);
            }
        }
    }

}
