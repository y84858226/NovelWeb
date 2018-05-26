package com.novel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.novel.pojo.Novel;
import com.novel.pojo.NovelChapterList;


/**
 * 主页查询
 * @author kainan
 *
 */
@Mapper // 声明是一个Mapper,与springbootApplication中的@MapperScan二选一写上即可
@Repository
public interface IIndexQueryDao {
	/**
	 * 获取推荐书籍
	 * @return
	 */
	@Select("select * from novel order by rand() limit 8")
	public List<Novel> selectRecommendBooks();
	
	/**
	 * 获取推荐书籍
	 * @return
	 */
	@Select("select name from novel order by rand() limit 1")
	public String selectRecommendValue();
	
	/**
	 * 获取热门书籍
	 */
	@Select("select * from novel order by clickView limit 5")
	public List<Novel> selectHotBooks();
	
	/**
	 * 获取分类的书籍
	 */
	@Select("select * from novel where typeName = #{typeName} order by rand() limit 8 ")
	public List<Novel> selectClassifyBooks(@Param("typeName") String typeName);
	
	/**
	 * 分页获取分类的书籍
	 */
	@Select("select * from novel where typeName = #{classifyName} limit #{start},#{end}")
	public List<Novel> selectClassifyBooksByPages(@Param("classifyName") String typeName,@Param("start") int start, @Param("end") int end);
	
	/**
	 * 获取书籍详细信息
	 */
	@Select("select * from novel where name = #{bookName}")
	public Novel selectBookDetail(@Param("bookName") String bookName);
	
	/**
	 * 获取目录信息
	 */
	@Select("select b.* from novel a,novelchapterlist b where a.name = #{bookName} and a.id = b.novelId")
	public List<NovelChapterList> selectBookDirectory(@Param("bookName") String bookName);
}
