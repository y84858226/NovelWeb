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
				if (novelChapterList.getNovelId() != null) {
					WHERE("novelId=#{novelId}");
				}
				if (novelChapterList.getChapterName() != null) {
					WHERE("chapterName=#{chapterName}");
				}
				if (novelChapterList.getChapterLink() != null) {
					WHERE("chapterLink=#{chapterLink}");
				}
			}
		}.toString();
	}
}
