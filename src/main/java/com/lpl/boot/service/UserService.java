package com.lpl.boot.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lpl.boot.mapper.UserMapper;
import com.lpl.boot.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //查询所有
    public List<User> findAll(){
        /**
         * 对Redis缓存的判断：
         * 如果缓存有，直接返回
         * 如果缓存没有，从数据库返回，并放到缓存
         */
        String userListJsonStr = redisTemplate.opsForValue().get("UserService.findAll");
        if (StringUtils.isNoneBlank(userListJsonStr)){
            List<User> users = JSON.parseArray(userListJsonStr, User.class);
            log.info("数据查询是走缓存的");
            return users;
        }else {
            List<User> users = userMapper.selectList(new LambdaQueryWrapper<>());
            redisTemplate.opsForValue().set("UserService.findAll",JSON.toJSONString(users),10, TimeUnit.SECONDS);
            log.info("数据查询走数据库，并放入缓存");
            return users;
        }
    }

    //分页
    public List<User> findPage(int current,int pageSize){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //分页查询
        Page page = new Page(current,pageSize);
        Page<User> userPage = userMapper.selectPage(page,queryWrapper);
        //打印日志
        log.info("total:{}",userPage.getTotal());
        log.info("pages:{}",userPage.getPages());

        return userPage.getRecords();
    }
    //插入数据
    public Long save(String name) {
        User user =new User();
        user.setAge(20);
        user.setName(name);
        user.setEmail("save@163.com");
        userMapper.insert(user);
        return user.getId();
    }
    //通过id查找
    public User findById(Long id){
        return userMapper.selectById(id);
    }
}
