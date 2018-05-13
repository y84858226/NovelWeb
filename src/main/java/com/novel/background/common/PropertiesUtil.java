package com.novel.background.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//关于Properties类常用的操作
public class PropertiesUtil {
	private String configName;

	public PropertiesUtil(String configName) {
		this.configName = configName;
	}

	// 根据Key读取Value
	public String read(String key) {
		Properties pps = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(configName));
			pps.load(in);
			String value = pps.getProperty(key);
			return value;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 读取Properties的全部信息
	public Map getAll() {
		Properties pps = new Properties();
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(configName));
			pps.load(in);
			Enumeration en = pps.propertyNames(); // 得到配置文件的名字
			Map<String, String> map = new HashMap<String, String>();
			while (en.hasMoreElements()) {
				String strKey = (String) en.nextElement();
				String strValue = pps.getProperty(strKey);
				map.put(strKey, strValue);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 写入Properties信息
	public void write(String pKey, String pValue) {
		Properties pps = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream(configName);
			pps.load(in);
			OutputStream out = new FileOutputStream(configName);
			pps.setProperty(pKey, pValue);
			pps.store(out, "Update " + pKey + " name");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		PropertiesUtil util=new PropertiesUtil("config.properties");
		util.write("aa", "123");
		util.write("aa", "456");
		System.out.println(util.read("aa"));
	}
}