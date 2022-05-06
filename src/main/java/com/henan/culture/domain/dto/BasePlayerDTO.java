package com.henan.culture.domain.dto;

import com.henan.culture.domain.entity.player.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-19 11:16
 **/
@NoArgsConstructor
@Data
public class BasePlayerDTO {
    private int id;
    private String name;
    private String headIcon;
    private String wxOpenId;

    public BasePlayerDTO(Player player){
        this.id = player.getId();
        this.name = player.getName();
        this.wxOpenId = player.getWxOpenId();
        this.headIcon = player.getHeadIcon();
    }

}
