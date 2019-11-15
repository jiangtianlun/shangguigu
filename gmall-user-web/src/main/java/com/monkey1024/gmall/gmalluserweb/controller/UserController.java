package com.monkey1024.gmall.gmalluserweb.controller;


import bean.UmsMember;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;

import java.util.List;

@Controller
public class UserController {
    @Reference
    UserService userService;

    @GetMapping("getusers")
    @ResponseBody
    public List<UmsMember> getUsers()
    {
        return userService.getUsers();
    }
}
