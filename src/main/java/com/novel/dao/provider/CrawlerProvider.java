package com.novel.dao.provider;

import org.apache.ibatis.jdbc.SQL;
import com.novel.pojo.Crawler;

/**
 * 生产动态sql
 * 
 * @author huayanh
 *
 */
public class CrawlerProvider {

	public String select(Crawler crawler) {
		return new SQL() {
			{
				SELECT("*");
				FROM("crawler");
				if (crawler.getId() != null) {
					WHERE("id=#{id}");
				}
				if (crawler.getCrawlerName() != null) {
					WHERE("crawlerName=#{crawlerName}");
				}
				if (crawler.getCrawlerUrl() != null) {
					WHERE("crawlerUrl=#{crawlerUrl}");
				}
				if (crawler.getCrawlerStatus() != null) {
					WHERE("crawlerStatus=#{crawlerStatus}");
				}
			}
		}.toString();
	}

	public String update(Crawler crawler) {
		return new SQL() {
			{
				UPDATE("crawler");
				if (crawler.getCrawlerName() != null) {
					SET("crawlerName=#{crawlerName}");
				}
				if (crawler.getCrawlerUrl() != null) {
					SET("crawlerUrl=#{crawlerUrl}");
				}
				if (crawler.getCrawlerStatus() != null) {
					SET("crawlerStatus=#{crawlerStatus}");
				}
				WHERE("id=#{id}");
			}
		}.toString();
	}
}
