package com.novel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import com.novel.dao.provider.CrawlerConfigProvider;
import com.novel.pojo.CrawlerConfig;

@Mapper
@Repository
public interface CrawlerConfigDao {

	@SelectProvider(type = CrawlerConfigProvider.class, method = "select")
	public List<CrawlerConfig> selectCrawlerConfig(CrawlerConfig crawlerConfig);
}