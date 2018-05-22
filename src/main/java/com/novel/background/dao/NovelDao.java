package com.novel.background.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.novel.background.dao.provider.NovelProvider;
import com.novel.background.pojo.Novel;

@Mapper
@Repository
public interface NovelDao {

	@Insert("insert into novel(path,name,author,typeName,description,mainImage,createTime,"
			+ "updateTime,clickView,status,display) values(#{path},#{name},#{author},#{typeName},#{description},"
			+ "#{mainImage},#{createTime},#{updateTime},#{clickView},#{status},#{display})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public void addNovel(Novel novel);

	@SelectProvider(type = NovelProvider.class, method = "select")
	public List<Novel> selectNovel(Novel novel);

	@Update("update novel set path=#{path} , mainImage=#{mainImage} where id=#{id}")
	public void updatePathAndImage(Novel novel);
}