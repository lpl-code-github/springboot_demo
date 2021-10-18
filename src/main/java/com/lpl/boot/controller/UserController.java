package com.lpl.boot.controller;


import com.lpl.boot.pojo.User;
import com.lpl.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {



    @Autowired
    private UserService userService;
    //查询所有
    @GetMapping("findAll")
    public List<User> findAll(){

        return userService.findAll();
    }
    //通过id查询
    @GetMapping("findById")
    public User findById(@RequestParam("id") Long id){
        return userService.findById(id);
    }


    //分页查询
    @GetMapping("findPage")
    public List<User> findPage(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize){
        return userService.findPage(page,pageSize);
    }

    @GetMapping("save")
    public Long save(@RequestParam("name") String name){
        return userService.save(name);
    }
}
