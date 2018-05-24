package com.novel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.novel.pojo.NovelType;

@Mapper
@Repository
public interface NovelTypeDao {

	@Insert("insert into noveltype(typeName) values(#{typeName})")
	public void addNovelType(NovelType novelType);

	@Update("truncate table noveltype")
	public void truncateNovelType();

	@Select("select * from noveltype limit #{start},#{end}")
	public List<NovelType> selectNovelType(@Param("start") int start, @Param("end") int end);

	@Select("select count(*) from noveltype")
	public int selectNovelTypeCount();

}