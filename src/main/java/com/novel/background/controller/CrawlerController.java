package com.novel.background.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.novel.background.pojo.Crawler;
import com.novel.background.service.CrawlerService;

/**
 * 爬虫系统
 * 
 * @author Yan Hua
 *
 */
// 证明是controller层并且返回json
@RestController
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.novel.background.service" }) // 添加的注解
public class CrawlerController {
	// 依赖注入
	@Autowired
	CrawlerService crawlerService;

	@RequestMapping("runCrawler")
	public void runCrawler() {
		crawlerService.crawlerNovelData("https://www.biquge5200.cc/");
	}
	
	@RequestMapping("addCrawler")
	public void addCrawler(Crawler crawler) {
		crawlerService.addCrawler(crawler);
	}
	
	@RequestMapping("selectCrawler")
	public Map<String,Object> selectCrawler(int page,int limit) {
		Map<String,Object> map=new LinkedHashMap<String,Object>();
		map.put("code",0);
		map.put("msg","");
		int count=crawlerService.selectCrawlerCount();
		map.put("count",count);
		List<Crawler> crawlerList=crawlerService.selectCrawler(page,limit);
		map.put("data",crawlerList);
		return map;
	}
	
	@RequestMapping("deleteCrawler")
	public void deleteCrawler(Crawler crawler) {
		crawlerService.deleteCrawler(crawler);
	}
	
	
}