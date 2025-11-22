package com.example.wmsiescore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 试题列表查询DTO
 */
@Data
@Schema(description = "试题列表查询对象")
public class QuestionListDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "试题ID", example = "1")
    private Long questionId;
    
    @Schema(description = "试题名称", example = "Java基础题目")
    private String questionName;
    
    @Schema(description = "试题类型ID", example = "1")
    private Long questionTypeId;
    
    @Schema(description = "知识点ID", example = "1")
    private Long pointId;
    
    @Schema(description = "知识点名称", example = "Java基础")
    private String pointName;
    
    @Schema(description = "题库ID", example = "1")
    private Long fieldId;
    
    @Schema(description = "题库名称", example = "Java题库")
    private String fieldName;

    @Schema(description = "分值", example = "5.0")
    private BigDecimal points;

    @Schema(description = "难度", example = "简单")
    private String difficulty;
    
    @Schema(description = "知识点列表", example = "[\"Java基础\", \"面向对象\"]")
    private List<String> knowledgePointNames;
}
