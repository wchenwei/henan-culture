package com.henan.culture.config.template;

import com.henan.culture.infrastructure.config.excel.FileConfig;
import lombok.Data;

/**
 * @description: 答题
 * @author: chenwei
 * @create: 2022-04-23 15:58
 **/
@Data
@FileConfig("dati")
public class DaTiTemplate {
    private int id;
    private int type;
    private String topic;
    private String option;
    private int answer;
    private String prize;

}
