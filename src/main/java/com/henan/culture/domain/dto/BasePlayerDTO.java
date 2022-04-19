package com.henan.culture.domain.dto;

import com.henan.culture.domain.entity.player.Player;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-19 11:16
 **/
@NoArgsConstructor
public class BasePlayerDTO {
    private int id;
    private String name;

    public BasePlayerDTO(Player player){
        this.id = player.getId();
        this.name = player.getName();
    }

}
