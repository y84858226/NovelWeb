package com.novel.foreground.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.novel.foreground.service.IIndexQueryService;
/**
 * 主页查询
 * @author kainan
 *
 */
//证明是controller层并且返回json
@RestController
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.novel.foreground.controller" }) // 添加的注解
public class indexQueryController {
	
	@Autowired
	private IIndexQueryService indexQueryService;
	/**
	 * 获取搜索框推荐书籍
	 * @return
	 */
	@RequestMapping("getRecommendVal")
	public JSONObject getRecommendVal() {
		return indexQueryService.getRecommendVal();
	}
	/**
	 * 获取推荐书籍
	 * @return
	 */
	@RequestMapping("getRecommendBooks")
	public JSONArray getRecommendBooks() {
		return indexQueryService.getRecommendBooks();
	}
	/**
	 * 获取热门书籍
	 */
	@RequestMapping("getHotBooks")
	public JSONArray getHotBooks() {
		return indexQueryService.getHotBooks();
	}
	
	/**
	 * 获取分类的书籍
	 */
	@RequestMapping("getClassifyBooks")
	public JSONArray getClassifyBooks(JSONObject jClassifyName) {
		return indexQueryService.getClassifyBooks(jClassifyName);
	}
}
