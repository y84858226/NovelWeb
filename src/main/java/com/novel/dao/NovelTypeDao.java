package com.novel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
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

	@Insert("insert into noveltype(originalTypeName,typeName) values(#{originalTypeName},#{typeName})")
	public void addNovelType(NovelType novelType);

	@Select("select * from noveltype limit #{start},#{end}")
	public List<NovelType> selectNovelType(@Param("start") int start, @Param("end") int end);

	@Select("select count(*) from noveltype")
	public int selectNovelTypeCount();

	@Select("select * from noveltype")
	public List<NovelType> selectNovelTypeAll();

	@Update("update novel set typeName=#{typeName} where typeName=#{originalTypeName}")
	public void updateNovelType(NovelType novelType);

	@Delete("delete from noveltype where id=#{id}")
	public void deleteNovelType(NovelType novelType);

}