package com.lpl.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lpl.boot.pojo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> findPermissionByRole(Integer roleId);
}

