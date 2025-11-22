package com.example.wmsiescore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;

@Schema(description = "题目表实体类")
@Data
public class Question {
    @Schema(description = "题目ID", example = "1")
    private Long id;
    @Schema(description = "题目内容", example = "1+1等于多少？")
    private String content;
    @Schema(description = "题型：选择题、填空题等", example = "选择题")
    private String type; // 题型：选择题、填空题等
    @Schema(description = "选择题选项（JSON格式）", example = "[\"A.1\", \"B.2\", \"C.3\", \"D.4\"]")
    private String options; // 选择题选项（JSON格式）
    @Schema(description = "答案", example = "B")
    private String answer;
    @Schema(description = "所属分类ID", example = "1")
    private Long categoryId; // 所属分类ID
    @Schema(description = "创建时间", example = "2023-01-01 10:00:00")
    private Timestamp createTime;
    @Schema(description = "更新时间", example = "2023-01-01 10:00:00")
    private Timestamp updateTime;
    @Schema(description = "创建人", example = "admin")
    private String createBy;
    @Schema(description = "更新人", example = "admin")
    private String updateBy;
}
