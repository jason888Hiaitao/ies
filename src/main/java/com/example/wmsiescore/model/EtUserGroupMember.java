package com.example.wmsiescore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@ApiModel(value = "EtUserGroupMember", description = "用户分组成员表实体类")
@Data
public class EtUserGroupMember {
    @ApiModelProperty(value = "成员ID", example = "1")
    private Long id;
    @ApiModelProperty(value = "分组ID", example = "1")
    private Long groupId; // 分组ID
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long userId; // 用户ID
    @ApiModelProperty(value = "用户姓名", example = "张三")
    private String userName; // 用户姓名
    @ApiModelProperty(value = "用户账号", example = "zhangsan")
    private String userAccount; // 用户账号
    @ApiModelProperty(value = "状态：active-激活，inactive-停用", example = "active")
    private String status; // 状态：active-激活，inactive-停用
    @ApiModelProperty(value = "创建时间", example = "2023-01-01 10:00:00")
    private Timestamp createTime; // 创建时间
    @ApiModelProperty(value = "更新时间", example = "2023-01-01 10:00:00")
    private Timestamp updateTime; // 更新时间
}