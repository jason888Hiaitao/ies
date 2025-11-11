package com.example.wmsiescore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@ApiModel(value = "EtUserExamHistory", description = "用户考试历史表实体类")
@Data
public class EtUserExamHistory {
    @ApiModelProperty(value = "主键ID", example = "1")
    private Long histId; // 主键ID
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long userId; // 用户ID
    @ApiModelProperty(value = "试卷ID", example = "1")
    private Long examPaperId; // 试卷ID
    @ApiModelProperty(value = "考试内容", example = "考试详细内容")
    private String content; // 考试内容
    @ApiModelProperty(value = "答题卡", example = "答题卡配置")
    private String answerSheet; // 答题卡
    @ApiModelProperty(value = "考试时长（分钟）", example = "120")
    private Integer duration; // 考试时长（分钟）
    @ApiModelProperty(value = "提交时间", example = "2023-01-01 12:00:00")
    private Timestamp submitTime; // 提交时间
    @ApiModelProperty(value = "获得分数", example = "85.5")
    private BigDecimal pointGet; // 获得分数
    @ApiModelProperty(value = "创建时间", example = "2023-01-01 10:00:00")
    private Timestamp createTime; // 创建时间
}