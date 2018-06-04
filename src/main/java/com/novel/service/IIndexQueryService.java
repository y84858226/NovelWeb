package com.novel.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * 主页查询
 * @author kainan
 *
 */
public interface IIndexQueryService {
	
	/**
	 * 获取搜索框推荐值
	 * @return
	 */
	JSONObject getRecommendVal();
	/**
	 * 获取推荐书籍
	 * @return
	 */
	JSONArray getRecommendBooks();
	/**
	 * 获取热门书籍
	 */
	JSONArray getHotBooks();
	/**
	 * 获取分类的书籍
	 */
	JSONArray getClassifyBooks(String param); 
	/**
	 * 分页获取分类的书籍
	 */
	JSONArray getClassifyBooksByPage(String param); 
	/**
	 * 获取书籍详细信息
	 */
	JSONObject getBookDetail(String bookid);
	/**
	 * 获取目录信息
	 */
	JSONArray getBookDirectory(String bookid);
}
