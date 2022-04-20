package com.henan.culture.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CodeDTO {
    @NotEmpty(message = "缺少参数code或code不合法")
    private String code;
    private String name;
}
