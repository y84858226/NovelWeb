package com.novel.dao.provider;

import org.apache.ibatis.jdbc.SQL;

import com.novel.pojo.Novel;
import com.novel.pojo.NovelChapterList;

/**
 * 生产动态sql
 * 
 * @author huayanh
 *
 */
public class NovelChapterListProvider {

	public String select(NovelChapterList novelChapterList) {
		return new SQL() {
			{
				SELECT("*");
				FROM("novelchapterlist");
				if (novelChapterList.getId() != null) {
					WHERE("id=#{id}");
				}
				if (novelChapterList.getCrawlerConfigId() != null) {
					WHERE("crawlerConfigId=#{crawlerConfigId}");
				}
				if (novelChapterList.getNovelId() != null) {
					WHERE("novelId=#{novelId}");
				}
				if (novelChapterList.getChapterNum() != null) {
					WHERE("chapterNum=#{chapterNum}");
				}
				if (novelChapterList.getChapterName() != null) {
					WHERE("chapterName=#{chapterName}");
				}
				if (novelChapterList.getChapterLink() != null) {
					WHERE("chapterLink=#{chapterLink}");
				}
				if (novelChapterList.getFilePath() != null) {
					WHERE("filePath=#{filePath}");
				}

			}
		}.toString();
	}
}
