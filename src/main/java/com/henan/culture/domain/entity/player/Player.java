package com.henan.culture.domain.entity.player;

import com.henan.culture.domain.dto.BasePlayerDTO;
import com.henan.culture.domain.dto.MailDTO;
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
@RedisMapperType(db = 10, type = MapperType.STRING_HASH, isZip = true)
public class Player extends BaseEntityMapper<Integer> {
    private String wxOpenId;
    private String dayMark;// 每日重置标志
    private String name;// 名称
    private long registerTime;// 注册世界
    private PlayerBag playerBag = new PlayerBag(); // 背包
    private PlayerPoked playerPoked = new PlayerPoked(); // 图鉴
    private PlayerScore playerScore = new PlayerScore();//积分
    private PlayerMail playerMail = new PlayerMail();//邮件
    private PlayerShare playerShare = new PlayerShare();//分享


    public BasePlayerDTO buildBasePlayerDTO(){
        return new BasePlayerDTO(this);
    }

    public PlayerDTO buildDTO(){
        return new PlayerDTO(this);
    }

    public MailDTO buildMailDTO(){
        return new MailDTO(this);
    }

}
