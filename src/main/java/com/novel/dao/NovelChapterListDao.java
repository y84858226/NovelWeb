package com.novel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import com.novel.dao.provider.NovelChapterListProvider;
import com.novel.pojo.NovelChapterList;

@Mapper
@Repository
public interface NovelChapterListDao {

	@Insert("insert into novelchapterlist(novelId,chapterName,chapterLink,crawlerConfigId) values(#{novelId},#{chapterName},#{chapterLink},#{crawlerConfigId})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public void addNovelChapterList(NovelChapterList novelChapterList);

	@SelectProvider(type = NovelChapterListProvider.class, method = "select")
	public List<NovelChapterList> selectNovelChapterList(NovelChapterList chapterList);
	
	@Select("select * from novelchapterlist where id in(select max(id) as id from novelchapterlist where novelId=#{novelId})")
	public NovelChapterList selectMaxchapter(NovelChapterList chapterList);
}