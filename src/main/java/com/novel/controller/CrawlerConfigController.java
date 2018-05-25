package com.novel.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.novel.pojo.CrawlerConfig;
import com.novel.pojo.NovelType;
import com.novel.service.CrawlerConfigService;
import com.novel.service.NovelTypeService;

@RestController // 证明是controller层并且返回json
@EnableAutoConfiguration
@Scope("prototype") // 原型模式
public class CrawlerConfigController {

	@Autowired
	CrawlerConfigService crawlerConfigService;

	@RequestMapping("selectCrawlerConfig")
	public Map<String, Object> selectCrawlerConfig(CrawlerConfig crawlerConfig) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("code", 0);
		map.put("msg", "");
		List<CrawlerConfig> crawlerConfigs = crawlerConfigService.selectConfig(crawlerConfig);
		map.put("count", crawlerConfigs.size());
		map.put("data", crawlerConfigs);
		return map;
	}
	
	@RequestMapping("addCrawlerConfig")
	public void addCrawlerConfig(CrawlerConfig crawlerConfig) {
		crawlerConfigService.addCrawlerConfig(crawlerConfig);
	}
	
	@RequestMapping("updateCrawlerConfig")
	public void updateCrawlerConfig(CrawlerConfig crawlerConfig) {
		crawlerConfigService.updateCrawlerConfig(crawlerConfig);
	}
}