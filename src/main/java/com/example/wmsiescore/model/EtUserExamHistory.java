package com.example.wmsiescore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Schema(description = "用户考试历史表实体类")
@Data
public class EtUserExamHistory {
    @Schema(description = "主键ID", example = "1")
    private Long histId; // 主键ID
    @Schema(description = "用户ID", example = "1")
    private Long userId; // 用户ID
    @Schema(description = "试卷ID", example = "1")
    private Long examPaperId; // 试卷ID
    @Schema(description = "考试内容", example = "考试详细内容")
    private String content; // 考试内容
    @Schema(description = "答题卡", example = "答题卡配置")
    private String answerSheet; // 答题卡
    @Schema(description = "考试时长（分钟）", example = "120")
    private Integer duration; // 考试时长（分钟）
    @Schema(description = "提交时间", example = "2023-01-01 12:00:00")
    private Timestamp submitTime; // 提交时间
    @Schema(description = "获得分数", example = "85.5")
    private BigDecimal pointGet; // 获得分数
    @Schema(description = "创建时间", example = "2023-01-01 10:00:00")
    private Timestamp createTime; // 创建时间
}
