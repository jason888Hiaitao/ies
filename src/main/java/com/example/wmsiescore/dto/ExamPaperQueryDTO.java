package com.example.wmsiescore.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 试卷查询DTO
 */
@Data
@ApiModel(value = "ExamPaperQueryDTO", description = "试卷查询请求对象")
public class ExamPaperQueryDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "试卷名称", example = "Java基础测试卷")
    private String name;
    
    @ApiModelProperty(value = "试卷状态", example = "active")
    private String status;
    
    @ApiModelProperty(value = "有效部门", example = "技术部")
    private String validdpt;
    
    @ApiModelProperty(value = "有效来源", example = "内部题库")
    private String validsource;
    
    @ApiModelProperty(value = "试卷类型", example = "正式考试")
    private String paperType;
    
    @ApiModelProperty(value = "页码，从1开始", example = "1", required = true)
    private Integer pageNum = 1;
    
    @ApiModelProperty(value = "每页大小", example = "10", required = true)
    private Integer pageSize = 10;
}