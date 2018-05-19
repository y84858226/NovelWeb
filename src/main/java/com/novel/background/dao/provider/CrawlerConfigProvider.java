package com.novel.background.dao.provider;

import org.apache.ibatis.jdbc.SQL;

import com.novel.background.pojo.CrawlerConfig;

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
				if (crawlerConfig.getId()!=null) {
					WHERE("id=#{id}");
				}
				if (crawlerConfig.getConfigId() != null) {
					WHERE("configId=#{configId}");
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
				if (crawlerConfig.getAppendResult() != null) {
					WHERE("appendResult=#{appendResult}");
				}
				if (crawlerConfig.getSort() != null) {
					WHERE("sort=#{sort}");
				}
			}
		}.toString();
	}
}
