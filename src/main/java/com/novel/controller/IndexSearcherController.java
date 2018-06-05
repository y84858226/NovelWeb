package com.novel.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.novel.common.lucene.DataBaseIndex;
import com.novel.common.lucene.DataBaseSearcher;
import com.novel.pojo.Novel;

/**
 * 搜索
 * @author kainan
 *
 */
//证明是controller层并且返回json
@RestController
@EnableAutoConfiguration
public class IndexSearcherController {
	@RequestMapping("runSearch")
	public List<Novel> runCrawler(HttpServletRequest request,String param) {
		final String WEB_APP_PATH = request.getSession().getServletContext().getRealPath("/");
		final String INDEX_PATH  = WEB_APP_PATH + "data\\lucene\\data";
		DataBaseIndex.createIndex(INDEX_PATH);
		List<Novel> books = new ArrayList<>();
		books = DataBaseSearcher.searchData(param, INDEX_PATH);
		return books;
	}
}
