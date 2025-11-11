package com.example.wmsiescore.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 试题列表查询DTO
 */
@Data
@ApiModel(value = "QuestionListDTO", description = "试题列表查询对象")
public class QuestionListDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "试题ID", example = "1")
    private Long questionId;
    
    @ApiModelProperty(value = "试题名称", example = "Java基础题目")
    private String questionName;
    
    @ApiModelProperty(value = "试题类型ID", example = "1")
    private Long questionTypeId;
    
    @ApiModelProperty(value = "知识点ID", example = "1")
    private Long pointId;
    
    @ApiModelProperty(value = "知识点名称", example = "Java基础")
    private String pointName;
    
    @ApiModelProperty(value = "题库ID", example = "1")
    private Long fieldId;
    
    @ApiModelProperty(value = "题库名称", example = "Java题库")
    private String fieldName;

    @ApiModelProperty(value = "分值", example = "5.0")
    private BigDecimal points;

    @ApiModelProperty(value = "难度", example = "简单")
    private String difficulty;
    
    @ApiModelProperty(value = "知识点列表", example = "[\"Java基础\", \"面向对象\"]")
    private List<String> knowledgePointNames;
}