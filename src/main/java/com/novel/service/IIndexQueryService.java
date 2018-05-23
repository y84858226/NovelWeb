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
}
