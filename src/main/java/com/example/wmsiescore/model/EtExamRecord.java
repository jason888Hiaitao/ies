package com.example.wmsiescore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@ApiModel(value = "EtExamRecord", description = "考试记录表实体类")
@Data
public class EtExamRecord {
    @ApiModelProperty(value = "记录ID", example = "1")
    private Long id;
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long userId;
    @ApiModelProperty(value = "试卷ID", example = "1")
    private Long examPaperId;
    @ApiModelProperty(value = "开始时间", example = "2023-01-01 10:00:00")
    private Timestamp startTime;
    @ApiModelProperty(value = "结束时间", example = "2023-01-01 12:00:00")
    private Timestamp endTime;
    @ApiModelProperty(value = "答案内容", example = "JSON格式的答案")
    private String answers;
    @ApiModelProperty(value = "考试状态", example = "completed")
    private String status;
    
    // 扩展字段用于考试分析
    @ApiModelProperty(value = "考试名称", example = "数学期末考试")
    private String examName;
    @ApiModelProperty(value = "总题目数", example = "50")
    private Integer totalQuestions;
    @ApiModelProperty(value = "正确题目数", example = "40")
    private Integer correctQuestions;
    @ApiModelProperty(value = "错误题目数", example = "10")
    private Integer wrongQuestions;
    @ApiModelProperty(value = "总分", example = "100.0")
    private Double totalScore;
    @ApiModelProperty(value = "及格分数", example = "60.0")
    private Double passingScore;
    @ApiModelProperty(value = "获得分数", example = "80.0")
    private Double obtainedScore;
}