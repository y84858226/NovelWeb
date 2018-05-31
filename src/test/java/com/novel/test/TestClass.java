package com.novel.test;

import java.io.File;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.novel.pojo.Novel;

public class TestClass {

	@Test
	public void testCreateFile() {
		String path = "src"+File.separator+"main"+File.separator+"webapp"+File.separator+"test";
		File file = new File(path);
		file.mkdir();
	}
	
	@Test
	public void testJson() {
		Novel novel=new Novel();
		novel.setName("小说");
		novel.setAuthor("啊实打实");
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(novel);
		System.out.println(jsonArray.toJSONString());
	}
}
