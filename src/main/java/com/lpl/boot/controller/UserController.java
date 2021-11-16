package com.lpl.boot.controller;


import com.lpl.boot.pojo.User;
import com.lpl.boot.service.UserService;


import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "用户管理")
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;


    //查询所有
    @GetMapping("findAll")
    @ApiOperation(value = "获取用户列表")
    public List<User> findAll(){
        return  userService.findAll();
    }


    //通过id查询
    @GetMapping("findById")
    @ApiOperation(value = "查询用户信息",notes = "根据id查询用户信息")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User findById(@ApiParam("id") @RequestParam("id") Long id){
        return userService.findById(id);
    }


    //分页查询
    @ApiOperation(value = "分页查询",notes = "根据页数和个数查询用户信息")

    @GetMapping("findPage")
    public List<User> findPage(@ApiParam("page") @RequestParam("page") Integer page, @ApiParam("pageSize") @RequestParam("pageSize") Integer pageSize){
        return userService.findPage(page,pageSize);
    }

    @ApiOperation(value = "保存",notes = "通过用户姓名保存")
    @GetMapping("save")
    public Long save(@ApiParam("name") @RequestParam("name") String name){
        return userService.save(name);
    }
}
