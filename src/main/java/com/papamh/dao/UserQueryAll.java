package com.papamh.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.papamh.pojo.User;

@Mapper // 声明是一个Mapper,与springbootApplication中的@MapperScan二选一写上即可
@Repository
public interface UserQueryAll {
	/**
	 * 根据姓名查询数据
	 * 
	 * @param username
	 * @return 实体数据
	 */
	@Select("SELECT * FROM user WHERE username = #{username}")
	@ResultType(User.class)
	List<User> selectUser(String username);
}