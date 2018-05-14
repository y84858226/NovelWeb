package com.novel.background.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.novel.background.pojo.Crawler;

@Mapper // 声明是一个Mapper,与springbootApplication中的@MapperScan二选一写上即可
@Repository
public interface CrawlerDao {
	/**
	 * 添加crawler
	 * @param crawler
	 */
	@Insert("insert into crawler(crawlerName,crawlerUrl) values(#{crawlerName},#{crawlerUrl})")
	public void addCrawler(Crawler crawler);

	@Select("select * from crawler")
	public Crawler selectCrawler();
}