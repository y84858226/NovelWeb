package com.novel.service.impl;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.novel.dao.IIndexQueryDao;
import com.novel.pojo.Novel;
import com.novel.pojo.NovelChapterList;
import com.novel.service.IIndexQueryService;
import com.novel.util.JsonReaderUtils;
/**
 * 主页查询
 * @author kainan
 *
 */
@Service
public class IndexQueryServiceImpl implements IIndexQueryService {
	
	private static Log log = LogFactory.getLog(IndexQueryServiceImpl.class);
	
	@Autowired
	private IIndexQueryDao indexQueryDao;
	/**
	 * 获取推荐书籍
	 * @return
	 */
	@Override
	public JSONArray getRecommendBooks() {
		long StartTime = System.currentTimeMillis();
		List<Novel> books = indexQueryDao.selectRecommendBooks();
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(books);
		log.info("查询耗时：" + (System.currentTimeMillis() - StartTime));
		return jsonArray;
	}
	/**
	 * 获取搜索框推荐值
	 * @return
	 */
	@Override
	public JSONObject getRecommendVal() {
		long StartTime = System.currentTimeMillis();
		String bookName = indexQueryDao.selectRecommendValue();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("recommendVal", bookName);
		log.info("查询耗时：" + (System.currentTimeMillis() - StartTime));
		return jsonObject;
	}
	/**
	 * 获取热门书籍
	 */
	@Override
	public JSONArray getHotBooks() {
		long StartTime = System.currentTimeMillis();
		List<Novel> books = indexQueryDao.selectHotBooks();
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(books);
		log.info("查询耗时：" + (System.currentTimeMillis() - StartTime));
		return jsonArray;
	}
	/**
	 * 获取分类的书籍
	 */
	@Override
	public JSONArray getClassifyBooks(String param) {
		long StartTime = System.currentTimeMillis();
		List<Novel> books = indexQueryDao.selectClassifyBooks(param);
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(books);
		log.info("查询耗时：" + (System.currentTimeMillis() - StartTime));
		return jsonArray;
	}
	/**
	 * 分页获取分类图书
	 */
	@Override
	public JSONArray getClassifyBooksByPage(String param) {
		long StartTime = System.currentTimeMillis();
		JSONObject paramJo = JSONObject.parseObject(param);
		List<Novel> books = indexQueryDao.selectClassifyBooksByPages(
				paramJo.getString("classifyName"), paramJo.getIntValue("start"), paramJo.getIntValue("end"));
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(books);
		log.info("查询耗时：" + (System.currentTimeMillis() - StartTime));
		return jsonArray;
	}
	/**
	 * 获取书籍详细信息
	 */
	@Override
	public JSONObject getBookDetail(String bookid) {
		long StartTime = System.currentTimeMillis();
		String path = "";
		String bookDetailStr = JsonReaderUtils.JsonReader(path);
		log.info("查询耗时：" + (System.currentTimeMillis() - StartTime));
		return null;
	}
	/**
	 * 获取目录信息
	 */
	@Override
	public JSONArray getBookDirectory(String bookid) {
		long StartTime = System.currentTimeMillis();
		JSONArray jsonArray = new JSONArray();
		log.info("查询耗时：" + (System.currentTimeMillis() - StartTime));
		return jsonArray;
	}

}
