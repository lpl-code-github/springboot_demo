package com.lpl.boot.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//使用@Data注释，不用自己写get set方法
@Data
@TableName("user")
@ApiModel(description="用户实体")
public class User {
    //@TableField("id")
    @ApiModelProperty("用户编号")
    private Long id;
    @ApiModelProperty("用户姓名")
    private String name;
    @ApiModelProperty("用户邮箱")
    private String email;
    @ApiModelProperty("用户年龄")
    private Integer age;

}
