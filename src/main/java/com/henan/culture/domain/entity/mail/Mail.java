package com.henan.culture.domain.entity.mail;

import cn.hutool.core.util.StrUtil;
import com.henan.culture.domain.entity.Items;
import com.henan.culture.enums.ItemType;
import com.henan.culture.enums.MailSendType;
import com.henan.culture.enums.RedisHashType;
import com.henan.culture.utils.util.ItemUtils;
import com.henan.culture.utils.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "y_mail")
@Data
@NoArgsConstructor
public class Mail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String title; //标题
	private String content; //内容
	private int sendType; //发送类型MailSendType
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@LastModifiedDate
	private Date sendDate; //发送日期
	private String reward; // 奖励
	@Transient
	private List<Items> rewardItems;
	@Transient
	private List<Integer> receives = new ArrayList<>();
	private boolean oldMail; //未加载过的邮件

	
	public Mail(String title, String content, String reward, MailSendType sendType){
		this.title = title;
		this.content = content;
		this.sendDate = new Date(); 
		this.reward = reward;
		this.sendType = sendType.getType();
	}


	public boolean isHaveReward(){
		return reward != null && !this.reward.isEmpty(); 
	}

	public boolean isHaveRealReward(){
		if (isHaveReward()){
			return ItemUtils.isContains(rewardItems, ItemType.RealGoods);
		}
		return false;
	}


	public boolean isCanDel() {
		if(this.sendType == MailSendType.All.getType() 
				|| this.sendType == MailSendType.Group.getType()){
			return false;
		}
		return true;
	}

	public void init(){
		if (MailSendType.All.getType() != sendType){
			String receivers = RedisHashType.MailReceivers.get(getId());
			if (StrUtil.isNotEmpty(receivers)){
				this.receives = StringUtil.splitStr2IntegerList(receivers,",");
			}
		}
		if (StrUtil.isNotEmpty(getReward())){
			this.rewardItems = ItemUtils.str2DefaultItemImmutableList(getReward());
		}
	}
	
	public long getSendDateTime() {
		if(this.sendDate == null) {
			return System.currentTimeMillis();
		}
		return this.sendDate.getTime();
	}
}
