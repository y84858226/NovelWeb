package com.novel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novel.dao.CrawlerConfigDao;
import com.novel.pojo.CrawlerConfig;
import com.novel.service.CrawlerConfigService;

@Service
public class CrawlerConfigServiceImpl implements CrawlerConfigService {

	@Autowired
	CrawlerConfigDao crawlerConfigDao;

	@Override
	public List<CrawlerConfig> selectConfig(CrawlerConfig crawlerConfig) {
		return crawlerConfigDao.selectCrawlerConfig(crawlerConfig);
	}

	@Override
	public void addCrawlerConfig(CrawlerConfig crawlerConfig) {
		crawlerConfigDao.addCrawlerConfig(crawlerConfig);
	}

	@Override
	public void updateCrawlerConfig(CrawlerConfig crawlerConfig) {
		crawlerConfigDao.updateCrawlerConfig(crawlerConfig);
	}

}
