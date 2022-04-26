package com.henan.culture.domain.vo;


import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.enums.ShareType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-26 13:48
 **/
@NoArgsConstructor
@Data
public class PlayerData {
    private int id;
    private long maxScore;// 最大积分
    private int maxChapter;//最大章节
    private int normalShare;//主页分享
    private int topShare;//顶部分享
    private int reliveShare;//重生分享
    private Map<Integer,Long> items;//道具

    public PlayerData(Player player) {
        this.id = player.getId();
        this.maxChapter = player.getPlayerChapter().getId();
        this.maxScore = player.getPlayerScore().getMaxScore();
        this.normalShare = player.getPlayerShare().getShareCount(ShareType.Normal);
        this.topShare = player.getPlayerShare().getShareCount(ShareType.Top);
        this.reliveShare = player.getPlayerShare().getShareCount(ShareType.Relive);
        this.items = player.getPlayerBag().getItemMap();
    }

    public static PlayerData buildData(Player player){
        return new PlayerData(player);
    }
}
