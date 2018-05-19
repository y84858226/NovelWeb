package com.novel.background.service;

import java.util.List;

import com.novel.background.pojo.Crawler;

public interface CrawlerService {
	
	/**
	 * 爬数据
	 * @param url
	 */
	public void crawlerNovelData(Crawler crawler);
	
	/**
	 *	添加crawler数据 
	 * @param crawler
	 */
	public void addCrawler(Crawler crawler);
	
	/**
	 * 分页查询cawler表
	 * @return
	 */
	public List<Crawler> selectCrawlerByPage(int page,int limit);
	
	/**
	 * 查询crawler的总行数
	 * @return
	 */
	public int selectCrawlerCount();
	
	/**
	 * 删除crawler数据
	 * @param crawler
	 */
	public void deleteCrawler(Crawler crawler);
	
	/**
	 * 条件查询
	 * @param crawler
	 */
	public List<Crawler> selectCrawler(Crawler crawler);
}
