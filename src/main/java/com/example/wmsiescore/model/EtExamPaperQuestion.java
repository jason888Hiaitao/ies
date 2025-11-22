package com.example.wmsiescore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;

@Schema(description = "试卷题目关联表实体类")
@Data
public class EtExamPaperQuestion {
    @Schema(description = "主键ID", example = "1")
    private Long id;
    @Schema(description = "试卷ID", example = "1")
    private Long examPaperId; // 试卷ID
    @Schema(description = "题目ID", example = "1")
    private Long questionId; // 题目ID
    @Schema(description = "题目排序", example = "1")
    private Integer sortOrder; // 题目排序
    @Schema(description = "题目在试卷中的分值（可覆盖原题分值）", example = "10")
    private Integer score; // 题目在试卷中的分值（可覆盖原题分值）
    @Schema(description = "状态：active-激活，inactive-停用", example = "active")
    private String status; // 状态：active-激活，inactive-停用
    @Schema(description = "创建时间", example = "2023-01-01 10:00:00")
    private Timestamp createTime; // 创建时间
    @Schema(description = "更新时间", example = "2023-01-01 10:00:00")
    private Timestamp updateTime; // 更新时间
}
