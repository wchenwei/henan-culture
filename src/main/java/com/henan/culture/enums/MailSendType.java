package com.henan.culture.enums;


/**
 * 
 * ClassName: MailType. <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: 2018年3月14日 下午4:59:37 <br/>  
 *  
 * @author yanpeng  
 * @version
 */
public enum MailSendType {

	One(1,"单发"),
	Group(2,"群发"),
	All(3,"全部"),

	;
	
	/**
	 * @param type
	 * @param desc
	 */
	private MailSendType(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	
	public static MailSendType getMailType(int type){
		for (MailSendType kind : MailSendType.values()) {
			if(type == kind.getType()) return kind; 
		}
		return null;
	}

	private int type;
	
	private String desc;

	public int getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}
}
