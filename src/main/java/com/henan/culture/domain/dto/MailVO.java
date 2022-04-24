/**  
 * Project Name:SLG_GameFuture.  
 * File Name:mailVO.java  
 * Package Name:com.hm.action.mail.vo  
 * Date:2018年1月9日上午9:49:30  
 * Copyright (c) 2018, 北京中科奥科技有限公司 All Rights Reserved.  
 *  
*/  
  
package com.henan.culture.domain.dto;

import com.henan.culture.domain.entity.Items;
import com.henan.culture.domain.entity.mail.Mail;
import com.henan.culture.domain.entity.player.Player;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**  
 * 邮件VO基类
 */
@Data
public class MailVO {
	private int id; //邮件ID
	private String title; //标题
	private Date sendDate; //发送时间
	private int state; //状态
	private List<Items> reward; // 奖励
	private String content;
	
	public MailVO(Player player, Mail mail){
		this.id = mail.getId();
		this.title = mail.getTitle();
		this.sendDate = mail.getSendDate(); 
		this.state = player.getPlayerMail().getMailState(id);
		this.reward = mail.getRewardItems();
		this.content = mail.getContent(); 
	}
	
	
}
  
