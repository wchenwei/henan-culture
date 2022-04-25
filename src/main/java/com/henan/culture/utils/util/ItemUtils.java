package com.henan.culture.utils.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.henan.culture.domain.entity.Items;
import com.henan.culture.enums.ItemType;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ItemUtils {

    public static List<Items> str2DefaultItemImmutableList(String source) {
        return str2ItemImmutableList(source, ";", ":");
    }

    public static List<Items> str2ItemImmutableList(String source, String sp1, String sp2) {
        return ImmutableList.copyOf(str2ItemList(source, sp1, sp2));
    }

	public static List<Items> str2DefaultItemList(String source) {
		return str2ItemList(source, ";", ":");
	}
	
	public static List<Items> str2ItemList(String source,String sp1,String sp2) {
		List<Items> items = Lists.newArrayList();
		try {
			for (String str : source.split(sp1)) {
				int type = Integer.parseInt(str.split(sp2)[0]);
				int id = Integer.parseInt(str.split(sp2)[1]);
				long count = Long.parseLong(str.split(sp2)[2]);
				items.add(new Items(id,count,type));
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return items;
	}


	public static Items str2Item(String source,String sp1) {
		if(StringUtils.isBlank(source) || source.split(sp1).length!=3){
			return null;
		}
		int type = Integer.parseInt(source.split(sp1)[0]);
		int id = Integer.parseInt(source.split(sp1)[1]);
		long count = Long.parseLong(source.split(sp1)[2]);
		return new Items(id,count,type);
	}

	
	public static List<Items> createCloneItems(List<Items> itemList) {
		List<Items> cloneList = Lists.newArrayList();
		for (Items items : itemList) {
			cloneList.add(items.clone());
		}
		return cloneList;
	}
	
	public static List<Items> jsonToItems(JSONArray jsonArray) {
		List<Items> rewards = Lists.newArrayList();
		if(jsonArray!=null){
			for(int i=0;i<jsonArray.size();i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
	    		if(obj!=null){
	    			int id = obj.getInt("id");
	    			int type = obj.getInt("itemType");
	    			int count = obj.getInt("count");
	    			Items item = new Items(id, count, type);
	    			rewards.add(item);
	    		}
			}
		}
		return rewards;
	}

	public static List<Items> calItemExtraAdd(List<Items> itemList, ItemType itemType, double extraAdd) {
		if(extraAdd <= 0) {
			return itemList;
		}
		List<Items> newList = Lists.newArrayList();
		for (Items item : itemList) {
			if(ItemType.getItemType(item.getType()) == itemType) {
				Items newItem = item.clone();
				newItem.setCount((long)(newItem.getCount()*(1+extraAdd)));
				newList.add(newItem);
			}else{
				newList.add(item);
			}
		}
		return newList;
	} 
	
	public static List<Items> calItemExtraAdd(List<Items> itemList,double extraAdd) {
		if(extraAdd <= 0) {
			return itemList;
		}
		List<Items> newList = Lists.newArrayList();
		for (Items item : itemList) {
			Items newItem = item.clone();
			newItem.addCountRate(extraAdd);
			newList.add(newItem);
		}
		return newList;
	} 
	
	public static String itemListToString(List<Items> itemList) {
		String str = "";
		for(Items items:itemList){
			str += items.getType() + ":" + items.getId() + ":" + items.getCount() + ";";
		}
		str = StringUtils.stripEnd(str, ";");
		return str;
	}

	//list是否包含needItem
	public static boolean isContains(List<Items> items, Items needItem) {
		items.stream().anyMatch(item -> {
			return item.getType() == needItem.getType() && item.getId() == needItem.getId();
	    });
		return false;
	}
	

	 //检查合并itemList
	public static List<Items> mergeItemList(List<Items> itemList) {
        if (itemList.isEmpty()) {
            return itemList;
        }
		List<Items> resultList = Lists.newArrayList();
		Table<Integer, Integer, Items> tables = HashBasedTable.create();
		for (Items items : itemList) {
			Items temp = tables.get(items.getType(), items.getId());
			if(temp == null) {
				temp = items.clone();
				tables.put(items.getType(), items.getId(), temp);
			}else{
				temp.addCount(items.getCount());
			}
		}
		resultList.addAll(tables.values());
		return resultList;
	}
	
	/**
	 * 获取奖励的比例奖励
	 * @param itemList
	 * @param rate
	 * @return
	 */
	public static List<Items> calItemRateReward(List<Items> itemList,double rate) {
		List<Items> tempList = Lists.newArrayList();
		for (Items items : itemList) {
			Items clone = items.clone();
			clone.setCount((long)(clone.getCount()*rate));
			if(clone.getCount() > 0) {
				tempList.add(clone);
			}
		}
		return tempList;
	}

	/**
	 * 获取奖励的比例奖励 向上取整
	 * @param itemList
	 * @param rate
	 * @return
	 */
	public static List<Items> calCeilItemRateReward(List<Items> itemList,double rate) {
		List<Items> tempList = Lists.newArrayList();
		for (Items items : itemList) {
			Items clone = items.clone();
			clone.setCount((long) Math.ceil(clone.getCount()*rate));
			if(clone.getCount() > 0) {
				tempList.add(clone);
			}
		}
		return tempList;
	}
	
	/**
	 * 获取奖励的比例奖励
	 * @param itemList
	 * @param rate
	 * @return
	 */
	public static Items calItemRateReward(Items item,double rate) {
		Items clone = item.clone();
		clone.setCount((long)(clone.getCount()*rate));
		return clone;
	}
	/**
	 * 在itemList中去除对应itemType,id的item
	 * @param itemList
	 * @param itemType
	 * @param itemId
	 * @return
	 */
	public static List<Items> filterItemList(List<Items> itemList,ItemType itemType,int itemId){
		return itemList.stream().filter(t -> !(t.getType() == itemType.getType() && t.getId() == itemId)).collect(Collectors.toList());
	}

    public static String delItemList(List<Items> itemList, ItemType itemType, int itemId) {
        return itemListToString(filterItemList(itemList, itemType, itemId));
    }

	/**
	 * 在itemList中获取对应itemType,id的item
	 * @param itemList
	 * @param itemType
	 * @param itemId
	 * @return
	 */
	public static List<Items> getItemList(List<Items> itemList,ItemType itemType,int itemId){
		return itemList.stream().filter(t -> (t.getType() == itemType.getType() && t.getId() == itemId)).collect(Collectors.toList());
	}

    /**
	 * @Title: getItemNum 
	 * @Description: 获取items中的某个道具的数量
	 * @param listItems
	 * @return
	 * @return long    返回类型 
	 * @throws
	 */
    public static boolean checkHaveItems(List<Items> listItems, ItemType itemType, int itemId) {
        if (CollUtil.isEmpty(listItems)) {
            return false;
    	}
        return listItems.stream().anyMatch(e -> {
            return e.getType() == itemType.getType() && e.getId() == itemId;
        });
    }

	/**
	 * 检查道具是否足够
	 *
	 * @param listItems
	 * @param checkItems
	 * @return
	 */
	public static boolean checkItemEnough(List<Items> listItems, Items checkItems) {
		if (CollUtil.isEmpty(listItems)) {
			return false;
		}
		return listItems.stream().filter(e -> e.getType() == checkItems.getType() && e.getId() == e.getId())
				.mapToLong(e -> e.getCount()).sum() >= checkItems.getCount();
	}

	/**
	 * 扣除道具
	 *
	 * @param listItems
	 * @param checkItems
	 */
	public static void reduceItems(List<Items> listItems, Items checkItems) {
		long count = checkItems.getCount();
		for (int i = listItems.size() - 1; i >= 0; i--) {
			Items item = listItems.get(i);
			if (item.getCount() > count) {
				item.setCount(item.getCount() - count);
				return;
			} else {
				listItems.remove(i);
				count = count - item.getCount();
			}
			if (count <= 0) {
				return;
			}
		}
	}

}
