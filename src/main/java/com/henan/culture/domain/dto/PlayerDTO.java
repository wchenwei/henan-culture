package com.henan.culture.domain.dto;

import com.henan.culture.domain.entity.player.Player;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-19 10:32
 **/
@Data
@Accessors(chain = true)
public class PlayerDTO extends BasePlayerDTO {
    private String token;
    private int id; // accountId
    private String name;// 名称
    private int lifeCount;// 生命值


    public PlayerDTO(Player player) {
        super(player);
        this.lifeCount = player.getLifeCount();
    }
}
