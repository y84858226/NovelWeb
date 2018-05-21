package com.novel.background.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Scope;
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

@RestController // 证明是controller层并且返回json
@EnableAutoConfiguration
@Scope("prototype") // 原型模式
public class CrawlerController {
	// 依赖注入
	@Autowired
	CrawlerService crawlerService;

	@RequestMapping("addCrawler")
	public void addCrawler(Crawler crawler) {
		crawlerService.addCrawler(crawler);
	}

	@RequestMapping("selectCrawler")
	public Map<String, Object> selectCrawler(int page, int limit) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("code", 0);
		map.put("msg", "");
		int count = crawlerService.selectCrawlerCount();
		map.put("count", count);
		List<Crawler> crawlerList = crawlerService.selectCrawlerByPage(page, limit);
		map.put("data", crawlerList);
		return map;
	}

	@RequestMapping("deleteCrawler")
	public void deleteCrawler(String ids) {
		String id[] = ids.split(",");
		for (String string : id) {
			if (!string.equals("")) {
				Crawler crawler = new Crawler();
				crawler.setId(Integer.parseInt(string));
				crawlerService.deleteCrawler(crawler);
			}
		}
	}

	@RequestMapping("runCrawler")
	public String runCrawler(Crawler crawler) {
		List<Crawler> list = crawlerService.selectCrawler(crawler);
		crawler = list.get(0);
		crawlerService.crawlerNovelData(crawler);

		return crawler.getCrawlerName() + ":运行结束";
	}

	@RequestMapping("validReg")
	public String validReg(String text, String reg, String regGroupNum) {
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(text);
		String result = null;
		if (matcher.find()) {
			result = matcher.group(Integer.parseInt(regGroupNum));
		}
		return result;
	}
}