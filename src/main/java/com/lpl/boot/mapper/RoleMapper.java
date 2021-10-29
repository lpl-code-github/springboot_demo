package com.lpl.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lpl.boot.pojo.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户id 查询所有的角色
     * @param userId
     * @return
     */
    List<Role> findRoleListByUserId(Long userId);
}