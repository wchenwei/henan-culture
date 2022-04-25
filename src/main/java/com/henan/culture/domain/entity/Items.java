package com.henan.culture.domain.entity;

import com.henan.culture.enums.ItemType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class Items {
	private int type;//物品类型 和 ItemType枚举对应
	private int id;		//玩家资源id PlayerAssetEnum对应
	private long count;

	public Items(int id) {
		this.id = id;
	}

	
	public Items(int id, long count, int type) {
		this.id = id;
		this.count = count;
		this.type = type;
	}
	public Items(int id, long count, ItemType itemType){
		this(id,count,itemType.getType());
	}
	
	public void addCount(long add) {
		this.count += add;
	}
	//按照百分比增加数量
	public void addCountRate(double rate) {
		//四舍五入
		this.count += (int)(count*rate+0.5);
	}
	
	public void reduceCount(long reduce) {
		this.count -= reduce;
	}

	public void setItemType(ItemType itemType) {
		this.type = itemType.getType();
	}
	
	public Items clone() {
		Items clone = new Items(id);
		clone.setType(type);
		clone.setCount(count);
		return clone;
	}
	
	public boolean isSameItem(Items item) {
		return this.type == item.getType() && this.id == item.getId();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Items)) {
			return false;
		}
		Items items = (Items) o;
		return type == items.type && id == items.id && count == items.count;
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, id, count);
	}
}










