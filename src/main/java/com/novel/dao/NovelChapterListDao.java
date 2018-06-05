package com.novel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.novel.dao.provider.NovelChapterListProvider;
import com.novel.pojo.NovelChapterList;

@Mapper
@Repository
public interface NovelChapterListDao {

	@Insert("insert into novelchapterlist(crawlerConfigId,novelId,chapterNum,chapterName,chapterLink) values(#{crawlerConfigId},#{novelId},#{chapterNum},#{chapterName},#{chapterLink})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public void addNovelChapterList(NovelChapterList novelChapterList);

	@SelectProvider(type = NovelChapterListProvider.class, method = "select")
	public List<NovelChapterList> selectNovelChapterList(NovelChapterList chapterList);

	@Select("select * from novelchapterlist where id in(select max(id) as id from novelchapterlist where novelId=#{novelId})")
	public NovelChapterList selectMaxchapter(NovelChapterList chapterList);

	@Update("update novelchapterlist set filePath=#{filePath} where id=#{id}")
	public void updateFilePath(NovelChapterList novelChapterList);
	
	@Select("select novelId,chapterNum,chapterName,filePath from novelchapterlist where novelId =#{novelId}")
	public List<NovelChapterList> selectChapterNameAndPath(NovelChapterList novelChapterList);
}