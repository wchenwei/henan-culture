package com.henan.culture.domain.entity.player;

import com.google.common.collect.Maps;
import com.henan.culture.enums.ShareType;
import lombok.Data;

import java.util.Map;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-23 14:34
 **/
@Data
public class PlayerShare {
    private Map<Integer,Integer> shareCntMap = Maps.newHashMap();

    public void addShare(ShareType shareType){
        this.shareCntMap.merge(shareType.getType(), 1, Integer::sum);
    }

    public int getShareCount(ShareType shareType){
        return shareCntMap.getOrDefault(shareType.getType(),0);
    }

    public void clear(){
        this.shareCntMap.clear();
    }
}
