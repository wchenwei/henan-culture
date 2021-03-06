package com.henan.culture.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 实体 属性描述 这里只是简单示例，你可以自定义相关用户信息
 */
@Entity
@Table(name = "yy_account", indexes = {@Index(columnList = "name")
        ,@Index(columnList = "wxOpenid", unique = true)})
@Data
public class WxAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String wxOpenid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;// 注册时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    private Date lastLoginTime;//最后登录时间


}
