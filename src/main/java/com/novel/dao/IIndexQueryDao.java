package com.novel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.novel.pojo.Novel;


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
}
