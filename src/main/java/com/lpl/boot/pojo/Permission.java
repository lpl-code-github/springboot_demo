package com.lpl.boot.pojo;

import lombok.Data;

@Data
public class Permission {
    private Integer id;

    private String name;

    private String desc;

    private String permissionKeyword;

    private String path;
}