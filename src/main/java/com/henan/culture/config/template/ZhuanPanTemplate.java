package com.henan.culture.config.template;

import com.henan.culture.utils.config.excel.FileConfig;
import lombok.Data;

/**
 * @description: 答题
 * @author: chenwei
 * @create: 2022-04-23 15:58
 **/
@Data
@FileConfig("zhuanpan")
public class ZhuanPanTemplate {
    private int id;
    private int weight;
    private String prize;

}
