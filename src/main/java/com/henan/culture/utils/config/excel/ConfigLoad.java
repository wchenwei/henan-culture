package com.henan.culture.utils.config.excel;

import com.henan.culture.utils.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ConfigLoad {
	/**
	 * 启动服务器时调用
	 * 加载配置文件到内存
	 */
	public static void loadAllConfig() {
		//加载类
		Map<String, ExcleConfig> maps = SpringUtil.getBeanMap(ExcleConfig.class);
		for (ExcleConfig config : maps.values()) {
			try {
				config.loadConfig();
			} catch (Exception e) {
				log.error("配置文件加载失败",e);
				e.printStackTrace();
			}
		}
	}
}
