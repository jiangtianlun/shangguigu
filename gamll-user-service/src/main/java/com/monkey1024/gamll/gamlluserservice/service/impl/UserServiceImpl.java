package com.monkey1024.gamll.gamlluserservice.service.impl;

import bean.UmsMember;


import com.alibaba.dubbo.config.annotation.Service;
import com.monkey1024.gamll.gamlluserservice.dao.mapper.UserDao;
import service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;
    @Override
    public List<UmsMember> getUsers() {
        List<UmsMember> umsMembers = userDao.selectAll();
        return umsMembers;
    }
}
