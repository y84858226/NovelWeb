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
				if (crawlerConfig.getSelector() != null) {
					WHERE("selector=#{selector}");
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

	public String update(CrawlerConfig crawlerConfig) {

		return new SQL() {
			{
				UPDATE("crawlerconfig");
				if (crawlerConfig.getSelector() != null) {
					SET("selector=#{selector}");
				}
				if (crawlerConfig.getNum() != null) {
					SET("num=#{num}");
				}
				if (crawlerConfig.getAttrName() != null) {
					SET("attrName=#{attrName}");
				}
				if (crawlerConfig.getReg() != null) {
					SET("reg=#{reg}");
				}
				if (crawlerConfig.getRegGroupNum() != null) {
					SET("regGroupNum=#{regGroupNum}");
				}
				if (crawlerConfig.getHeadAppendResult() != null) {
					SET("headAppendResult=#{headAppendResult}");
				}
				if (crawlerConfig.getTailAppendResult() != null) {
					SET("tailAppendResult=#{tailAppendResult}");
				}
				if (crawlerConfig.getReplaceResult() != null) {
					SET("replaceResult=#{replaceResult}");
				}
				WHERE("id=#{id}");
			}
		}.toString();
	}
}
