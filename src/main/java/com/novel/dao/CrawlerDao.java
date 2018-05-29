package com.novel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import com.novel.dao.provider.CrawlerProvider;
import com.novel.pojo.Crawler;

@Mapper // 声明是一个Mapper,与springbootApplication中的@MapperScan二选一写上即可
@Repository
public interface CrawlerDao {
	/**
	 * 添加crawler
	 * 
	 * @param crawler
	 */
	@Insert("insert into crawler(crawlerName,crawlerUrl,crawlerStatus) values(#{crawlerName},#{crawlerUrl},#{crawlerStatus})")
	@Options(useGeneratedKeys = true, keyProperty = "id") 
	public void addCrawler(Crawler crawler);

	@Select("select * from crawler limit #{start},#{end}")
	public List<Crawler> selectCrawlerByPage(@Param("start") int start, @Param("end") int end);

	@Delete("delete from crawler where id=#{id}")
	public void deleteCrawler(Crawler crawler);

	@Select("select count(*) from crawler")
	public int selectCrawlerCount();

	@SelectProvider(type = CrawlerProvider.class, method = "select")
	public List<Crawler> selectCrawler(Crawler crawler);
	
	@UpdateProvider(type=CrawlerProvider.class,method="update")
	public void updateCrawler(Crawler crawler);
}