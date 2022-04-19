package com.henan.culture.domain.entity.player;

import com.henan.culture.domain.dto.PlayerDTO;
import com.henan.culture.infrastructure.springredis.base.BaseEntityMapper;
import com.henan.culture.infrastructure.springredis.common.MapperType;
import com.henan.culture.infrastructure.springredis.common.RedisMapperType;
import lombok.Data;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-16 17:35
 **/
@Data
@RedisMapperType(type = MapperType.HASH_JSON)
public class Player extends BaseEntityMapper<Integer> {
    private String dayMark;// 每日重置标志
    private String name;// 名称
    private int lifeCount;// 生命值
    private int maxScore; // 最高积分
    private PlayerBag playerBag; // 背包
    private PlayerChapter playerChapter;// 章节
    private PlayerPoked playerPoked; // 图鉴



    public PlayerDTO buildDTO(){
        return new PlayerDTO(this);
    }
}
