package com.novel.service;

import java.util.List;

import com.novel.pojo.CrawlerConfig;

public interface CrawlerConfigService {
	public List<CrawlerConfig> selectConfig(CrawlerConfig crawlerConfig);

	public void addCrawlerConfig(CrawlerConfig crawlerConfig);

	public void updateCrawlerConfig(CrawlerConfig crawlerConfig);
}
