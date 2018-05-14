package com.novel.background.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

	@Select("select * from crawler limit #{start},#{end}")
	public List<Crawler> selectCrawler(@Param("start")int start,@Param("end") int end);

	@Delete("delete from crawler where id=#{id}")
	public void deleteCrawler(Crawler crawler);
	
	@Select("select count(*) from crawler")
	public int selectCrawlerCount();
}