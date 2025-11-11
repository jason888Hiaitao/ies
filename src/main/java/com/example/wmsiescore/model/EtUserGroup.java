package com.example.wmsiescore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@ApiModel(value = "EtUserGroup", description = "用户分组表实体类")
@Data
public class EtUserGroup {
    @ApiModelProperty(value = "分组ID", example = "1")
    private Long id;
    @ApiModelProperty(value = "分组名称", example = "开发组")
    private String groupName; // 分组名称
    @ApiModelProperty(value = "分组描述", example = "开发相关分组")
    private String description; // 分组描述
    @ApiModelProperty(value = "状态：active-激活，inactive-停用", example = "active")
    private String status; // 状态：active-激活，inactive-停用
    @ApiModelProperty(value = "创建人ID", example = "1")
    private Long createdBy; // 创建人ID
    @ApiModelProperty(value = "创建时间", example = "2023-01-01 10:00:00")
    private Timestamp createTime; // 创建时间
    @ApiModelProperty(value = "更新时间", example = "2023-01-01 10:00:00")
    private Timestamp updateTime; // 更新时间
    
    // 新增字段
    @ApiModelProperty(value = "部门", example = "技术部")
    private String department; // 部门
    @ApiModelProperty(value = "组名", example = "开发组")
    private String groupname; // 组名
    @ApiModelProperty(value = "临时部门", example = "临时技术部")
    private String temporaryDepartment; // 临时部门
    @ApiModelProperty(value = "临时组名", example = "临时开发组")
    private String temporaryGroupname; // 临时组名
    @ApiModelProperty(value = "领域ID", example = "1")
    private Long fieldId; // 领域ID
    @ApiModelProperty(value = "省份", example = "北京")
    private String province; // 省份
    @ApiModelProperty(value = "公司", example = "ABC公司")
    private String company; // 公司
}