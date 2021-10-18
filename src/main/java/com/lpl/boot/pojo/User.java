package com.lpl.boot.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

//使用@Data注释，不用自己写get set方法
@Data
@TableName("user")
public class User {
    //@TableField("id")
    private Long id;
    private String name;
    private String email;
    private Integer age;

}
