package com.lpl.boot.service;

import com.lpl.boot.mapper.AdminUserMapper;
import com.lpl.boot.mapper.PermissionMapper;
import com.lpl.boot.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Service
@Slf4j
public class AuthService {

//    @Autowired
//    private AdminUserMapper adminUserMapper;
//    @Autowired
//    private RoleMapper roleMapper;
//    @Autowired
//    private PermissionMapper permissionMapper;

    public boolean auth(HttpServletRequest request, Authentication authentication){
        log.info("request url:{}",request.getRequestURI());
        String requestURI = request.getRequestURI();
        Object principal = authentication.getPrincipal();
        //如果未登录
        if (principal == null || "anonymousUser".equals(principal)){
            return false;//false代表拦截
        }
        //SecurityUserService返回的userDetails
        UserDetails userDetails = (UserDetails) principal;
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            MySimpleGrantedAuthority grantedAuthority = (MySimpleGrantedAuthority) authority;
            String[] paths = StringUtils.split(requestURI, "?");
            if (paths[0].equals(grantedAuthority.getPath())){
                return true;//true代表放行
            }
        }
        return false;//false代表拦截
    }
}
