package com.example.wmsiescore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;

@Schema(description = "考试试卷表实体类")
@Data
public class ExamPaper {
    @Schema(description = "试卷ID", example = "1")
    private Long id;
    @Schema(description = "试卷名称", example = "数学期末考试")
    private String name;
    @Schema(description = "试卷描述", example = "数学期末考试试卷")
    private String description;
    @Schema(description = "所属分类ID", example = "1")
    private Long categoryId; // 所属分类ID
    @Schema(description = "权限范围（如部门或分组）", example = "技术部")
    private String permission; // 权限范围（如部门或分组）
    @Schema(description = "创建时间", example = "2023-01-01 10:00:00")
    private Timestamp createTime;
    @Schema(description = "更新时间", example = "2023-01-01 10:00:00")
    private Timestamp updateTime;
    @Schema(description = "创建人", example = "admin")
    private String createBy;
    @Schema(description = "更新人", example = "admin")
    private String updateBy;
}
