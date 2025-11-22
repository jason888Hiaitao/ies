package com.example.wmsiescore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Schema(description = "待考试信息对象")
public class PendingExamDTO {
    @Schema(description = "考试ID", example = "1")
    private Long id;
    @Schema(description = "考试标题", example = "Java基础测试")
    private String title;
    @Schema(description = "考试描述", example = "Java基础知识测试")
    private String description;
    @Schema(description = "开始时间", example = "2024-01-01 10:00:00")
    private Timestamp startTime;
    @Schema(description = "结束时间", example = "2024-01-01 12:00:00")
    private Timestamp endTime;
    @Schema(description = "考试时长（分钟）", example = "120")
    private Integer duration;
    @Schema(description = "总分", example = "100")
    private Integer totalScore;
    @Schema(description = "及格分", example = "60")
    private Integer passScore;
    @Schema(description = "最大尝试次数", example = "3")
    private Integer maxAttempts;
    @Schema(description = "已尝试次数", example = "1")
    private Integer attemptedCount;
    @Schema(description = "是否可以重考", example = "true")
    private Boolean canRetake;
    @Schema(description = "考试状态", example = "待考试")
    private String status;
}
