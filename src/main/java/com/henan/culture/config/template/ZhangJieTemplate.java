package com.henan.culture.config.template;

import com.henan.culture.infrastructure.config.excel.FileConfig;
import lombok.Data;

/**
 * @description: 章节
 * @author: chenwei
 * @create: 2022-04-23 15:56
 **/
@Data
@FileConfig("zhangjie")
public class ZhangJieTemplate {
    private int id;
    private String title;
    private String content;
    private String dot;

}
