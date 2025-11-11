package com.example.wmsiescore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
@ApiModel(value = "PendingExamDTO", description = "待考试信息对象")
public class PendingExamDTO {
    @ApiModelProperty(value = "考试ID", example = "1")
    private Long id;
    @ApiModelProperty(value = "考试标题", example = "Java基础测试")
    private String title;
    @ApiModelProperty(value = "考试描述", example = "Java基础知识测试")
    private String description;
    @ApiModelProperty(value = "开始时间", example = "2024-01-01 10:00:00")
    private Timestamp startTime;
    @ApiModelProperty(value = "结束时间", example = "2024-01-01 12:00:00")
    private Timestamp endTime;
    @ApiModelProperty(value = "考试时长（分钟）", example = "120")
    private Integer duration;
    @ApiModelProperty(value = "总分", example = "100")
    private Integer totalScore;
    @ApiModelProperty(value = "及格分", example = "60")
    private Integer passScore;
    @ApiModelProperty(value = "最大尝试次数", example = "3")
    private Integer maxAttempts;
    @ApiModelProperty(value = "已尝试次数", example = "1")
    private Integer attemptedCount;
    @ApiModelProperty(value = "是否可以重考", example = "true")
    private Boolean canRetake;
    @ApiModelProperty(value = "考试状态", example = "待考试")
    private String status;
}