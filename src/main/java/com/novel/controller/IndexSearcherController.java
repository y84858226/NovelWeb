package com.novel.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.novel.pojo.Novel;
import com.novel.service.SearchIndexService;

/**
 * 搜索
 * 
 * @author kainan
 *
 */
// 证明是controller层并且返回json
@RestController
@EnableAutoConfiguration
public class IndexSearcherController {
	@Autowired
	SearchIndexService searchIndexService;

	@RequestMapping("searchIndex")
	public List<Novel> runCrawler(HttpServletRequest request, String param) {
		List<Novel> books = new ArrayList<>();
		String WEB_APP_PATH = request.getSession().getServletContext().getRealPath("/");
		String INDEX_PATH = WEB_APP_PATH + "data" + File.separator + "index";
		books = searchIndexService.searchData(param, INDEX_PATH);
		return books;
	}

}
