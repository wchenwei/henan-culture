package com.henan.culture.controller.dto;

import com.henan.culture.domain.entity.player.Player;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO 返回值token对象
 */
@Data
@AllArgsConstructor
public class TokenDTO {
    private String token;
    private Player player;
}
