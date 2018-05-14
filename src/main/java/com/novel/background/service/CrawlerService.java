package com.novel.background.service;

import com.novel.background.pojo.Crawler;

public interface CrawlerService {
	
	/**
	 * 爬数据
	 * @param url
	 */
	public void crawlerNovelData(String url);
	
	/**
	 *	添加crawler数据 
	 * @param crawler
	 */
	public void addCrawler(Crawler crawler);
	
	/**
	 * 查询全部的爬虫配置
	 * @return
	 */
	public Crawler selectCrawler();
}
