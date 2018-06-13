package com.novel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.novel.service.IIndexQueryService;
/**
 * 主页查询
 * @author kainan
 *
 */
//证明是controller层并且返回json
@RestController
@EnableAutoConfiguration
@Scope("prototype") // 原型模式
public class IndexQueryController {
	
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
	public JSONArray getClassifyBooks(String param) {
		return indexQueryService.getClassifyBooks(param);
	}
	
	/**
	 * 分页获取分类的书籍
	 */
	@RequestMapping("getClassifyBooksByPage")
	public JSONArray getClassifyBooksByPage(String param) {
		return indexQueryService.getClassifyBooksByPage(param);
	}
	/**
	 * 获取分类的书籍总条数
	 */
	@RequestMapping("getClassifyBooksCount")
	public int getClassifyBooksCount(String param) {
		return indexQueryService.getClassifyBooksCount(param);
	}
	/**
	 * 获取书籍详细信息
	 * @param bookName
	 * @return
	 */
	@RequestMapping("getBookDetail")
	public JSONObject getBookDetail(String param) {
		return indexQueryService.getBookDetail(param);
	}
	/**
	 * 获取目录
	 */
	@RequestMapping("getBookDirectory")
	public JSONArray getBookDirectory(String param) {
		return indexQueryService.getBookDirectory(param);
	}
}
