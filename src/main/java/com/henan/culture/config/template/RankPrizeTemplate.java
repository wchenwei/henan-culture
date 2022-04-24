package com.henan.culture.config.template;

import com.henan.culture.infrastructure.config.excel.FileConfig;
import lombok.Data;

/**
 * @description: 排行奖励
 * @author: chenwei
 * @create: 2022-04-23 15:47
 **/
@FileConfig("rankprize")
@Data
public class RankPrizeTemplate {
    private Integer id;
    private String ranking;
    private String prize;
}
