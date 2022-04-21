package com.henan.culture.domain.entity.player;

import com.google.common.collect.Lists;
import com.henan.culture.domain.entity.mail.Mail;
import com.henan.culture.enums.MailState;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

/**
 * 邮件
 * @version
 */
public class PlayerMail{
	private ConcurrentHashMap<String, Integer> sysMailMap = new ConcurrentHashMap<String, Integer>();  
	
	public int getMailState(String id){
		return sysMailMap.get(id);
	}
	
	public List<String> getMailIdList() {
		List<String> idList = sysMailMap.entrySet().stream()
				.filter(e -> e.getValue() != MailState.Del.getType())
				.map(e -> e.getKey())
				.sorted((v1, v2) -> {
					if(v1.length() == v2.length()) {
						return v2.compareTo(v1);
					}
					return v2.length() - v1.length();
				})
				.collect(Collectors.toList());
		return idList;
	}

	/**
	 * 新邮件
	 */
	public void addMail(Mail mail) {
		if(mail.isHaveReward()){
			sysMailMap.put(mail.getId(), MailState.NewReward.getType());
		}else{
			sysMailMap.put(mail.getId(), MailState.NewMail.getType());
		}
	}

	/**
	 * 阅读邮件
	 */
	public void readMail(Mail mail) {
		if(mail.isHaveReward()){
			sysMailMap.put(mail.getId(),MailState.ReadNoGet.getType());
		}else{
			sysMailMap.put(mail.getId(),MailState.Read.getType());
		}
	}

	/**
	 * 领取附件
	 */
	public void getReward(String id) {
		sysMailMap.put(id, MailState.Get.getType());
	}

	/**
	 * 能否领奖
	 * @author yanpeng
	 * @param mail
	 * @return
	 */
	public boolean canGetReward(Mail mail) {
		if(!mail.isHaveReward())
			return false;
		int state = sysMailMap.get(mail.getId());
		if(state == MailState.NewReward.getType() || state == MailState.ReadNoGet.getType()) {
			return true;
		}
		return false;
	}
	public boolean isHaveMail(Mail mail) {
		return sysMailMap.containsKey(mail.getId());
	}
	
	/**
	 * 全部已读并返回带附件的邮件id
	 *
	 * @author yanpeng 
	 * @return  
	 *
	 */
	public List<String> readAll(){
		List<String> list = Lists.newArrayList();
		sysMailMap.forEach((x,y)->{
			if(readAll(x,y)){
				list.add(x);
			}
		});
		return list; 
	}
	
	private boolean readAll(String id,int state){
		switch(MailState.getState(state)) {
			case NewMail:
				sysMailMap.put(id, MailState.Read.getType());
				return false; 
			case NewReward:
			case ReadNoGet:
				sysMailMap.put(id, MailState.Get.getType());
				return true; 
			default :
				return false; 
		}
	}
	
	/**
	 * 获取玩家邮件数量
	 *
	 * @author yanpeng 
	 * @return  
	 *
	 */
	public int getCount(){
		int size = 0; 
		for(Integer state:sysMailMap.values()){
			if(state != MailState.Del.getType()){
				size ++; 
			}
		}
		return size; 
	}
	
	public int getState(String id){
		return sysMailMap.get(id);
	}
	
	public ConcurrentNavigableMap<String, Integer> getMailSortMap() {
		ConcurrentSkipListMap<String, Integer> sortMap = new ConcurrentSkipListMap<String, Integer>((v1, v2) -> {
			if(v1.length() == v2.length()) {
				return v1.compareTo(v2);
			}
			return v1.length() - v2.length();
		});
		sortMap.putAll(this.sysMailMap);
		return sortMap.descendingMap();
	}
}
