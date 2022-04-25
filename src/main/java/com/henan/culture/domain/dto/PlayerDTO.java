package com.henan.culture.domain.dto;

import com.henan.culture.domain.entity.mail.Mail;
import com.henan.culture.domain.entity.player.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-19 10:32
 **/
@Data
@Accessors(chain = true)
public class PlayerDTO extends BasePlayerDTO {
    private long maxScore;// 最高分
    private Map<Integer, Long> itemMap; // 道具
    private Map<Integer, Integer> shareCntMap;// 分享次数

    public PlayerDTO(Player player) {
        super(player);
        this.maxScore = player.getPlayerScore().getMaxScore();
        this.itemMap = player.getPlayerBag().getItemMap();
        this.shareCntMap = player.getPlayerShare().getShareCntMap();
    }
}
