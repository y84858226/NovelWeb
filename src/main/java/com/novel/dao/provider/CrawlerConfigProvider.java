package com.novel.dao.provider;

import org.apache.ibatis.jdbc.SQL;

import com.novel.pojo.CrawlerConfig;

/**
 * 生产动态sql
 * 
 * @author huayanh
 *
 */
public class CrawlerConfigProvider {

	public String select(CrawlerConfig crawlerConfig) {
		return new SQL() {
			{
				SELECT("*");
				FROM("crawlerconfig");
				if (crawlerConfig.getId() != null) {
					WHERE("id=#{id}");
				}
				if (crawlerConfig.getCrawlerId() != null) {
					WHERE("crawlerId=#{crawlerId}");
				}
				if (crawlerConfig.getSelect() != null) {
					WHERE("select=#{select}");
				}
				if (crawlerConfig.getNum() != null) {
					WHERE("num=#{num}");
				}
				if (crawlerConfig.getAttrName() != null) {
					WHERE("attrName=#{attrName}");
				}
				if (crawlerConfig.getReg() != null) {
					WHERE("reg=#{reg}");
				}
				if (crawlerConfig.getRegGroupNum() != null) {
					WHERE("regGroupNum=#{regGroupNum}");
				}
				if (crawlerConfig.getHeadAppendResult() != null) {
					WHERE("appendResult=#{headAppendResult}");
				}
				if (crawlerConfig.getTailAppendResult() != null) {
					WHERE("appendResult=#{tailAppendResult}");
				}
				if (crawlerConfig.getReplaceResult() != null) {
					WHERE("appendResult=#{replaceResult}");
				}
				if (crawlerConfig.getSort() != null) {
					WHERE("sort=#{sort}");
				}
			}
		}.toString();
	}
}
