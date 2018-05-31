package com.novel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.novel.dao.provider.NovelProvider;
import com.novel.pojo.Novel;

@Mapper
@Repository
public interface NovelDao {

	@Insert("insert into novel(name,author,typeName,description,status,lastChapterNum,createTime,"
			+ "updateTime,clickView,display) values(#{name},#{author},#{typeName},#{description},"
			+ "#{status},#{lastChapterNum},#{createTime},#{updateTime},#{clickView},#{display})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public void addNovel(Novel novel);

	@SelectProvider(type = NovelProvider.class, method = "select")
	public List<Novel> selectNovel(Novel novel);

	@Update("update novel set updateTime=#{updateTime} , lastChapterNum=#{lastChapterNum} where id=#{id}")
	public void updateTimeAndLastChapterNum(Novel novel);

	@Select("select distinct typeName from novel")
	public List<Novel> selectType();

}