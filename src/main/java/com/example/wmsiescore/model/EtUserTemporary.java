package com.example.wmsiescore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;

@ApiModel(value = "EtUserTemporary", description = "临时用户表实体类")
@Data
public class EtUserTemporary {
    @ApiModelProperty(value = "用户ID", example = "1")
    private Integer id;
    @ApiModelProperty(value = "用户名", example = "temp_user")
    private String username;
    @ApiModelProperty(value = "密码", example = "encrypted_password")
    private String password;
    @ApiModelProperty(value = "邮箱", example = "temp@example.com")
    private String email;
    @ApiModelProperty(value = "电话", example = "13800138000")
    private String phone;
    @ApiModelProperty(value = "添加日期", example = "2023-01-01")
    private Date addDate;
    @ApiModelProperty(value = "过期日期", example = "2024-01-01")
    private Date expireDate;
    @ApiModelProperty(value = "添加人", example = "admin")
    private String addBy;
    @ApiModelProperty(value = "是否启用", example = "true")
    private Boolean enabled;
    @ApiModelProperty(value = "是否外部用户", example = "false")
    private Boolean ifOutSource;
    @ApiModelProperty(value = "真实姓名", example = "临时用户")
    private String truename;
    @ApiModelProperty(value = "领域ID", example = "1")
    private String fieldId;
    @ApiModelProperty(value = "省份", example = "北京")
    private String province;
    @ApiModelProperty(value = "公司", example = "ABC公司")
    private String company;
    @ApiModelProperty(value = "部门", example = "技术部")
    private String department;
    @ApiModelProperty(value = "组名", example = "开发组")
    private String groupname;
    @ApiModelProperty(value = "临时部门", example = "临时技术部")
    private String temporaryDepartment;
    @ApiModelProperty(value = "临时组名", example = "临时开发组")
    private String temporaryGroupname;
}