package com.henan.culture.domain.entity.mail;

import com.google.common.collect.Sets;
import com.henan.culture.enums.MailSendType;
import com.henan.culture.infrastructure.springredis.base.BaseEntityMapper;
import com.henan.culture.infrastructure.springredis.common.MapperType;
import com.henan.culture.infrastructure.springredis.common.RedisMapperType;
import com.henan.culture.infrastructure.util.IdManager;
import com.henan.culture.infrastructure.util.IdType;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@RedisMapperType(type = MapperType.STRING_HASH)
public class Mail extends BaseEntityMapper<String> {
	private String title; //标题
	private String content; //内容
	private transient int sendType; //发送类型MailSendType
	private Date sendDate; //发送日期
	private transient Set<Integer> receivers = Sets.newHashSet(); //收件人
	private Map<Integer, Long> reward; // 奖励
	private int mailId;//邮件id

	
	public Mail(String id, int serverId, String title, String content, Map<Integer, Long> rewards, MailSendType sendType){
		setId(id);
		setServerId(serverId); 
		this.title = title;
		this.content = content;
		this.sendDate = new Date(); 
		this.reward = rewards; 
		this.sendType = sendType.getType(); 
	}
	
	public Mail(int serverId, String title, String content, Map<Integer, Long> rewards, MailSendType sendType){
		setId(serverId+"_"+ IdManager.genId(IdType.Mail));
		setServerId(serverId); 
		this.title = title;
		this.content = content;
		this.sendDate = new Date(); 
		this.reward = rewards; 
		this.sendType = sendType.getType(); 
	}
	
	public Mail(int serverId, int mailId, String title, String content, Map<Integer, Long> rewards, MailSendType sendType){
		setId(serverId+"_"+IdManager.genId(IdType.Mail));
		setServerId(serverId);
		this.mailId = mailId;
		this.title = title;
		this.content = content;
		this.sendDate = new Date(); 
		this.reward = rewards; 
		this.sendType = sendType.getType(); 
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getMailId() {
		return mailId;
	}


	public String getContent() {
		return content;
	}
	public int getSendType() {
		return sendType;
	}
	

	public Date getSendDate() {
		return sendDate;
	}
	
	public Set<Integer> getReceivers() {
		return receivers;
	}
	
	public void addReceivers(Set<Integer> receivers){
		this.receivers.addAll(receivers);
	}
	
	public void setReceivers(Set<Integer> receivers){
		this.receivers = receivers; 
	}
	
	public Map<Integer, Long> getReward() {
		return reward;
	}
	
	public boolean isHaveReward(){
		return reward != null && !this.reward.isEmpty(); 
	}


	public boolean isCanDel() {
		if(this.sendType == MailSendType.All.getType() 
				|| this.sendType == MailSendType.Group.getType()){
			return false;
		}
		return true;
	}

	
	public long getSendDateTime() {
		if(this.sendDate == null) {
			return System.currentTimeMillis();
		}
		return this.sendDate.getTime();
	}
	
	public void saveDB() {
		saveDB();
	}
}
