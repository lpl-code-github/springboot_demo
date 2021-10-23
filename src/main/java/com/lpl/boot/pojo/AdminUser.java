package com.lpl.boot.pojo;

import lombok.Data;

@Data
public class AdminUser {

    private  Long id;

    private String username;

    private String password;

    private Long createTime;
}