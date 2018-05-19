package com.novel.background.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import com.novel.background.pojo.NovelChapterList;

@Mapper
@Repository
public interface NovelChapterListDao {

	@Insert("insert into novelchapterlist(novelId,chapterName,chapterLink) values(#{novelId},#{chapterName},#{chapterLink})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public void addNovelChapterList(NovelChapterList novelChapterList);
}