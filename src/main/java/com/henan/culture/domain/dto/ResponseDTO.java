package com.henan.culture.domain.dto;

import com.google.common.collect.Maps;
import com.henan.culture.domain.entity.player.Player;
import com.henan.culture.enums.ResponseStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-20 10:24
 **/
@Data
@Accessors(chain = true)
public class ResponseDTO {
    private Integer code;
    private String content;
    private Map<String, Object> data = Maps.newHashMap();


    public ResponseDTO(ResponseStatus status) {
        this.code = status.getCode();
    }

    public static ResponseDTO buildDTO(ResponseStatus status){
        return new ResponseDTO(status);
    }

    public static ResponseDTO Suc(){
        return buildDTO(ResponseStatus.OK);
    }

    public static ResponseDTO Suc(BasePlayerDTO playerDTO){
        return buildDTO(ResponseStatus.OK).addProperty("player", playerDTO);
    }

    public static ResponseDTO Fail(){
        return buildDTO(ResponseStatus.ERROR);
    }

    public static ResponseDTO Fail(String content){
        return buildDTO(ResponseStatus.ERROR).setContent(content);
    }

    public ResponseDTO addProperty(String key, Object object){
        this.data.put(key, object);
        return this;
    }


}
