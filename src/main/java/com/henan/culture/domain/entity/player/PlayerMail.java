package com.henan.culture.domain.entity.player;

import com.google.common.collect.Lists;
import com.henan.culture.domain.entity.mail.Mail;
import com.henan.culture.enums.MailState;

import java.util.Comparator;
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
	private ConcurrentHashMap<Integer, Integer> sysMailMap = new ConcurrentHashMap<Integer, Integer>();
	
	public int getMailState(Integer id){
		return sysMailMap.get(id);
	}
	
	public List<Integer> getMailIdList() {
		List<Integer> idList = sysMailMap.entrySet().stream()
				.filter(e -> e.getValue() != MailState.Del.getType())
				.filter(e -> e.getValue() != MailState.Get.getType())
				.map(e -> e.getKey())
				.sorted(Comparator.comparing(Integer::intValue).reversed())
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
	public void getReward(Integer id) {
		sysMailMap.put(id, MailState.Get.getType());
	}

	/**
	 * 能否领奖
	 * @author yanpeng
	 * @param mail
	 * @return
	 */
	public boolean canGetReward(Mail mail) {
		if(!isHaveMail(mail) || !mail.isHaveReward())
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
	public List<Integer> readAll(){
		List<Integer> list = Lists.newArrayList();
		sysMailMap.forEach((x,y)->{
			if(readAll(x,y)){
				list.add(x);
			}
		});
		return list; 
	}
	
	private boolean readAll(Integer id,int state){
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
	
	public int getState(Integer id){
		return sysMailMap.get(id);
	}
	
	public ConcurrentNavigableMap<Integer, Integer> getMailSortMap() {
		ConcurrentSkipListMap<Integer, Integer> sortMap = new ConcurrentSkipListMap();
		sortMap.putAll(this.sysMailMap);
		return sortMap.descendingMap();
	}
}
