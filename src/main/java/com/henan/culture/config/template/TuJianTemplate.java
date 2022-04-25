package com.henan.culture.config.template;

import com.henan.culture.utils.config.excel.FileConfig;
import lombok.Data;

/**
 * @description: 图鉴
 * @author: chenwei
 * @create: 2022-04-23 15:52
 **/
@Data
@FileConfig("tujian")
public class TuJianTemplate {
    private int id;
    private int type;
    private String name;
    private String img;
    private String des;
}
