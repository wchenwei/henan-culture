package com.henan.culture.infrastructure.config.excel;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.PathUtil;
import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.io.resource.ResourceUtil;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.henan.culture.infrastructure.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.CharSet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Slf4j
public abstract class ExcleConfig {
	public abstract void loadConfig();
//	public abstract List<String> getDownloadFile();

	
	public static <C> String getJson(Class<C> t) {
		FileConfig fileConfig = t.getAnnotation(FileConfig.class);
		String json = readFile(Constants.JsonPath +fileConfig.value()+".json");
		return json;
	}

//	public List<String> getConfigName(Class...configs) {
//		List<String> names = Lists.newArrayList();
//		for (Class c : configs) {
//			FileConfig fileConfig = (FileConfig) c.getAnnotation(FileConfig.class);
//			names.add(fileConfig.value());
//		}
//		return names;
//	}
	
	public static String readFile(String path) {
		try {
			StringBuffer sb = new StringBuffer();
			Resource resource = ResourceUtil.getResourceObj(path);
			BufferedReader reader = resource.getReader(Charsets.UTF_8);
			reader.lines().forEach(e -> sb.append(e));
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
