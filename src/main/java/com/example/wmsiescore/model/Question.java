package com.example.wmsiescore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@ApiModel(value = "Question", description = "题目表实体类")
@Data
public class Question {
    @ApiModelProperty(value = "题目ID", example = "1")
    private Long id;
    @ApiModelProperty(value = "题目内容", example = "1+1等于多少？")
    private String content;
    @ApiModelProperty(value = "题型：选择题、填空题等", example = "选择题")
    private String type; // 题型：选择题、填空题等
    @ApiModelProperty(value = "选择题选项（JSON格式）", example = "[\"A.1\", \"B.2\", \"C.3\", \"D.4\"]")
    private String options; // 选择题选项（JSON格式）
    @ApiModelProperty(value = "答案", example = "B")
    private String answer;
    @ApiModelProperty(value = "所属分类ID", example = "1")
    private Long categoryId; // 所属分类ID
    @ApiModelProperty(value = "创建时间", example = "2023-01-01 10:00:00")
    private Timestamp createTime;
    @ApiModelProperty(value = "更新时间", example = "2023-01-01 10:00:00")
    private Timestamp updateTime;
    @ApiModelProperty(value = "创建人", example = "admin")
    private String createBy;
    @ApiModelProperty(value = "更新人", example = "admin")
    private String updateBy;
}