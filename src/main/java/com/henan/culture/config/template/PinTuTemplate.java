package com.henan.culture.config.template;

import com.henan.culture.utils.config.excel.FileConfig;
import lombok.Data;

/**
 * @description: 答题
 * @author: chenwei
 * @create: 2022-04-23 15:58
 **/
@Data
@FileConfig("pintu")
public class PinTuTemplate {
    private int id;
    private int type;
    private int dot;
    private String prize;

}
