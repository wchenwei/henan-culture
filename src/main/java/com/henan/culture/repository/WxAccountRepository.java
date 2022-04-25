package com.henan.culture.repository;

import com.henan.culture.domain.entity.WxAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WxAccountRepository extends JpaRepository<WxAccount, Integer> {

    /**
     * 根据OpenId查询用户信息
     */
    WxAccount findByWxOpenid(String wxOpenId);
}
