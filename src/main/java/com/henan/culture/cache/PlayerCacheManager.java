package com.henan.culture.cache;

import com.google.common.cache.*;
import com.henan.culture.domain.db.PlayerUtil;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.domain.entity.WxAccount;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-18 19:44
 **/
public class PlayerCacheManager {
    private static final PlayerCacheManager instance = new PlayerCacheManager();
    public static PlayerCacheManager getInstance() {
        return instance;
    }

    public final Player DefaultValue;
    private final LoadingCache<Integer, Player> cache;

    private PlayerCacheManager() {
        DefaultValue = new Player();
        DefaultValue.setId(-1);
        cache = CacheBuilder.newBuilder()
                .maximumSize(4000)
                .expireAfterAccess(20, TimeUnit.MINUTES)//设置时间对象没有被读/写访问则对象从内存中删除
                .recordStats()
                .removalListener(new RemovalListener<Integer, Player>() {
                    @Override
                    public void onRemoval(RemovalNotification<Integer, Player> notification) {
                        doRemoveFoSave(notification.getValue());
                        System.err.println("cache del player "+notification.getKey());
                    }
                })
                .build(new CacheLoader<Integer, Player>() {

                    @Override
                    public Player load(Integer key) {
                        Player player = PlayerUtil.getPlayer(key);
                        return player == null ? DefaultValue : player;
                    }
                });
    }

    public Player getPlayer(WxAccount account) {
        Player player = cache.getUnchecked(account.getId());
        return player.getId().equals(DefaultValue.getId()) ? null : player;
    }

    public void removePlayerCache(int playerId) {
        cache.invalidate(playerId);
    }

    public void clearAllCache() {
        cache.invalidateAll();
    }

    public Player getPlayerOrNull(int playerId) {
        try {
            return cache.getIfPresent(playerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void doRemoveFoSave(Player player) {
        player.saveDB();
    }


    public void addPlayerToCache(Player player) {
        cache.put(player.getId(), player);
    }

    public LoadingCache<Integer, Player> getCache() {
        return cache;
    }


}
