package com.henan.culture.domain.entity.player;

import lombok.Data;

/**
 * @description: 章节
 * @author: chenwei
 * @create: 2022-04-19 15:18
 **/
@Data
public class PlayerChapter {
    private Integer id = 1;

    public boolean checkAndResetChapter(int chapter){
        if (chapter > id){
            this.id = chapter;
            return true;
        }
        return false;
    }
}
