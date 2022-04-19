package com.henan.culture.domain.db;

import com.henan.culture.domain.entity.player.Player;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-16 18:01
 **/
public class PlayerUtil {

    public static Player getPlayer(int id){
        return Player.queryOne(id, id, Player.class);
    }

}
