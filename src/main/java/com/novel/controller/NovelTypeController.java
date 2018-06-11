package com.novel.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.novel.pojo.NovelType;
import com.novel.service.NovelTypeService;

@RestController // 证明是controller层并且返回json
@EnableAutoConfiguration
@Scope("prototype") // 原型模式
public class NovelTypeController {

	@Autowired
	NovelTypeService novelTypeService;

	@RequestMapping("selectNovelType")
	public Map<String, Object> selectNovelType(int page, int limit) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("code", 0);
		map.put("msg", "");
		int count = novelTypeService.selectNovelTypeCount();
		map.put("count", count);
		List<NovelType> novelTypeList = novelTypeService.selectNovelType(page, limit);
		map.put("data", novelTypeList);
		return map;
	}

	@RequestMapping("addNovelType")
	public void addNovelType(NovelType novelType) {
		novelTypeService.addNovelType(novelType);
	}

	@RequestMapping("deleteNovelType")
	public void deleteNovelType(String ids) {
		String id[] = ids.split(",");
		for (String string : id) {
			if (!string.equals("")) {
				NovelType novelType = new NovelType();
				novelType.setId(Integer.parseInt(string));
				novelTypeService.deleteNovelType(novelType);
			}
		}
	}
}