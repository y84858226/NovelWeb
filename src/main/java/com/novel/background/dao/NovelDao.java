package com.novel.background.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import com.novel.background.pojo.Novel;

@Mapper
@Repository
public interface NovelDao {

	@Insert("insert into novel(url,name,author,typeName,description,mainImage,createTime,"
			+ "updateTime,status) values(#{url},#{name},#{author},#{typeName},#{description},"
			+ "#{mainImage},#{createTime},#{updateTime},#{status})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public void addNovel(Novel novel);
}