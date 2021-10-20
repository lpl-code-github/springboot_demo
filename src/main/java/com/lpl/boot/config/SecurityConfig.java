package com.lpl.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //下面这两行配置表示在内存中配置了两个用户,密码为main方法生成的密码
        auth.inMemoryAuthentication()
                .withUser("admin").roles("admin").password("$2a$10$5vy6739h8cZJlQ/svPtoNe0tbNYg6ESxyVmQTQj3xpzyqHfQz2jyq")
                .and()
                .withUser("user").roles("user").password("$2a$10$5vy6739h8cZJlQ/svPtoNe0tbNYg6ESxyVmQTQj3xpzyqHfQz2jyq");
    }

    /**
     * spring security提供了 BCryptPasswordEncoder来进行密码编码
     * 并作为了相关配置的默认配置
     * 只不过没有暴露为全局的 Bean
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        // 加密策略
        /**
         * MD5加密不安全，可以彩虹表破解
         * 使用SpringSecurity中的密码加密算法
         * BCryptPasswordEncoder方法采用SHA-256 +随机盐+密钥对密码进行加密
         */
        String lpl = new BCryptPasswordEncoder().encode("lpl");
        System.out.println(lpl);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() //开启登录认证
                .antMatchers("/user/findAll").hasRole("admin") //访问接口需要admin的角色
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated() // 其他所有的请求 只需要登录即可
                .and().formLogin()
                .loginPage("/login.html") //自定义的登录页面
                .loginProcessingUrl("/login") //登录处理接口
                .usernameParameter("username") //定义登录时的用户名的key 默认为username
                .passwordParameter("password") //定义登录时的密码key，默认是password
                .successHandler(new AuthenticationSuccessHandler() {//登录成功的处理
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("success");//如果登录成功返回success
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {//登录失败处理器
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("fail");
                    }
                })
                .permitAll() //通过 不拦截，更加前面配的路径决定，这是指和登录表单相关的接口 都通过
                .and().logout() //退出登录配置
                .logoutUrl("/logout") //退出登录接口
                .logoutSuccessHandler(new LogoutSuccessHandler() {//退出登录成功 处理器
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("logout success");
                    }
                })
                .permitAll() //退出登录的接口放行
                .and()
                .httpBasic()
                .and()
                .csrf().disable(); //csrf关闭 如果自定义登录 需要关闭

    }


}