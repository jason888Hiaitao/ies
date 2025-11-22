package com.example.wmsiescore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;

@Schema(description = "考试记录表实体类")
@Data
public class EtExamRecord {
    @Schema(description = "记录ID", example = "1")
    private Long id;
    @Schema(description = "用户ID", example = "1")
    private Long userId;
    @Schema(description = "试卷ID", example = "1")
    private Long examPaperId;
    @Schema(description = "开始时间", example = "2023-01-01 10:00:00")
    private Timestamp startTime;
    @Schema(description = "结束时间", example = "2023-01-01 12:00:00")
    private Timestamp endTime;
    @Schema(description = "答案内容", example = "JSON格式的答案")
    private String answers;
    @Schema(description = "考试状态", example = "completed")
    private String status;
    
    // 扩展字段用于考试分析
    @Schema(description = "考试名称", example = "数学期末考试")
    private String examName;
    @Schema(description = "总题目数", example = "50")
    private Integer totalQuestions;
    @Schema(description = "正确题目数", example = "40")
    private Integer correctQuestions;
    @Schema(description = "错误题目数", example = "10")
    private Integer wrongQuestions;
    @Schema(description = "总分", example = "100.0")
    private Double totalScore;
    @Schema(description = "及格分数", example = "60.0")
    private Double passingScore;
    @Schema(description = "获得分数", example = "80.0")
    private Double obtainedScore;
}
