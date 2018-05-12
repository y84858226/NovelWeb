package com.novel.background.service.impi;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novel.background.dao.UserQueryAll;
import com.novel.background.pojo.User;
import com.novel.background.service.UserService;

@Service
public class UserServiceImpl implements UserService{
     @Autowired
     UserQueryAll mapper;

    @Override
    public List<User> queryUserByUserName(String username) {
        List<User> list = mapper.selectUser(username);
        return list;
    }

}