package com.henan.culture.cache;

import com.google.common.cache.*;
import com.henan.culture.domain.entity.WxAccount;
import com.henan.culture.domain.repository.WxAccountRepository;
import com.henan.culture.infrastructure.util.SpringUtil;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-18 19:44
 **/
public class AccountCacheManager {
    private static final AccountCacheManager instance = new AccountCacheManager();

    public static AccountCacheManager getInstance() {
        return instance;
    }

    public final WxAccount DefaultValue;
    private final LoadingCache<String, WxAccount> cache;

    private AccountCacheManager() {
        DefaultValue = new WxAccount();
        DefaultValue.setWxOpenid("-1");
        cache = CacheBuilder.newBuilder()
                .maximumSize(4000)
                .expireAfterAccess(20, TimeUnit.MINUTES)//设置时间对象没有被读/写访问则对象从内存中删除
                .recordStats()
                .removalListener(new RemovalListener<String, WxAccount>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, WxAccount> notification) {
                        doRemoveFoSave(notification.getValue());
                        System.err.println("cache del wxaccount "+notification.getKey());
                    }
                })
                .build(new CacheLoader<String, WxAccount>() {

                    @Override
                    public WxAccount load(String key) {
                        WxAccountRepository wxAccountRepository = SpringUtil.getBean(WxAccountRepository.class);
                        WxAccount wxAccount = wxAccountRepository.findByWxOpenid(key);
                        return wxAccount == null ? DefaultValue : wxAccount;
                    }
                });
    }

    public WxAccount getAccount(String openId) {
        WxAccount account = cache.getUnchecked(openId);
        return account.getWxOpenid().equals(DefaultValue.getWxOpenid()) ? null : account;
    }

    public void removeAccountCache(String openId) {
        cache.invalidate(openId);
    }

    public void clearAllCache() {
        cache.invalidateAll();
    }

    public WxAccount getAccountOrNull(String openId) {
        try {
            return cache.getIfPresent(openId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void doRemoveFoSave(WxAccount account) {
        removeAccountCache(account.getWxOpenid());
    }


    public void addAccountToCache(WxAccount account) {
        cache.put(account.getWxOpenid(), account);
    }

    public LoadingCache<String, WxAccount> getCache() {
        return cache;
    }


}
