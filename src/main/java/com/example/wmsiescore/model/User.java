package com.example.wmsiescore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "User", description = "用户表实体类")
@Data
public class User {
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long id;
    @ApiModelProperty(value = "用户名", example = "admin")
    private String username;
    @ApiModelProperty(value = "密码", example = "encrypted_password")
    private String password;
}