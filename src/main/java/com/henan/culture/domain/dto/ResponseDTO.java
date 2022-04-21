package com.henan.culture.domain.dto;

import com.google.common.collect.Maps;
import com.henan.culture.enums.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @description:
 * @author: chenwei
 * @create: 2022-04-20 10:24
 **/
public class ResponseDTO {
    private Integer code;
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

    public static ResponseDTO Fail(){
        return buildDTO(ResponseStatus.ERROR);
    }

    public ResponseDTO addProperty(String key, Object object){
        this.data.put(key, object);
        return this;
    }


}
