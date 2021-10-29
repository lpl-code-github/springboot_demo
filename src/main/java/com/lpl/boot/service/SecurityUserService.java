package com.lpl.boot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lpl.boot.mapper.PermissionMapper;
import com.lpl.boot.mapper.RoleMapper;
import com.lpl.boot.pojo.AdminUser;
import com.lpl.boot.pojo.Permission;
import com.lpl.boot.pojo.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.lpl.boot.mapper.AdminUserMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SecurityUserService implements UserDetailsService {
    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username:{}",username);

        /**
         * 当用户登录的时候 spring security就会将请求 转发到此
         * 根据用户名查找用户
         *  不存在抛出异常
         *  存在就将用户名、密码、授权列表组装成spring security的user对象 并返回
         */

        LambdaQueryWrapper<AdminUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminUser::getUsername,username).last("limit 1");
        AdminUser adminUser = this.adminUserMapper.selectOne(queryWrapper);
        if (adminUser == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<GrantedAuthority> authorityList = new ArrayList<>();

        //查询角色和角色对应的权限 并赋予当前的登录用户，并告知spring security框架
        List<Role> roleList = roleMapper.findRoleListByUserId(adminUser.getId());
        for (Role role : roleList) {
            List<Permission> permissionList = permissionMapper.findPermissionByRole(role.getId());
//            authorityList.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleKeyword()));
            authorityList.add(new MySimpleGrantedAuthority("ROLE_"+role.getRoleKeyword()));
            for (Permission permission : permissionList) {
//                authorityList.add(new SimpleGrantedAuthority(permission.getPermissionKeyword()));
                authorityList.add(new MySimpleGrantedAuthority(permission.getPermissionKeyword(),permission.getPath()));
            }
        }
        UserDetails userDetails = new User(username,adminUser.getPassword(),authorityList);
        //剩下的认证 由框架完成
        return userDetails;
    }
}