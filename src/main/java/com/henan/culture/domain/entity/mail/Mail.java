package com.henan.culture.domain.entity.mail;

import cn.hutool.core.util.StrUtil;
import com.henan.culture.enums.MailSendType;
import com.henan.culture.infrastructure.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
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
	private String receiver; //收件人
	private String reward; // 奖励
	@Transient
	private List<Integer> receives;
	private boolean oldMail; //未加载过的邮件

	
	public Mail(String title, String content, String reward, MailSendType sendType, String receiver){
		this.title = title;
		this.content = content;
		this.sendDate = new Date(); 
		this.reward = reward;
		this.sendType = sendType.getType();
		this.receiver = receiver;
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

	public void init(){
		if (StrUtil.isNotEmpty(getReceiver())){
			this.receives = StringUtil.splitStr2IntegerList(getReceiver(),",");
		}
	}
	
	public long getSendDateTime() {
		if(this.sendDate == null) {
			return System.currentTimeMillis();
		}
		return this.sendDate.getTime();
	}
}
