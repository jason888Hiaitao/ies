package com.example.wmsiescore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@ApiModel(value = "ExamPaper", description = "考试试卷表实体类")
@Data
public class ExamPaper {
    @ApiModelProperty(value = "试卷ID", example = "1")
    private Long id;
    @ApiModelProperty(value = "试卷名称", example = "数学期末考试")
    private String name;
    @ApiModelProperty(value = "试卷描述", example = "数学期末考试试卷")
    private String description;
    @ApiModelProperty(value = "所属分类ID", example = "1")
    private Long categoryId; // 所属分类ID
    @ApiModelProperty(value = "权限范围（如部门或分组）", example = "技术部")
    private String permission; // 权限范围（如部门或分组）
    @ApiModelProperty(value = "创建时间", example = "2023-01-01 10:00:00")
    private Timestamp createTime;
    @ApiModelProperty(value = "更新时间", example = "2023-01-01 10:00:00")
    private Timestamp updateTime;
    @ApiModelProperty(value = "创建人", example = "admin")
    private String createBy;
    @ApiModelProperty(value = "更新人", example = "admin")
    private String updateBy;
}