package com.papamh.service.impi;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.papamh.dao.UserQueryAll;
import com.papamh.pojo.User;
import com.papamh.service.UserService;

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