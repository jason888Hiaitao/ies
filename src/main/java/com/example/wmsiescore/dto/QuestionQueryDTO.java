package com.example.wmsiescore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

/**
 * 试题查询条件DTO
 */
@Data
@Schema(description = "试题查询条件对象")
public class QuestionQueryDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "试题名称（模糊查询）", example = "Java基础")
    private String questionName;
    
    @Schema(description = "试题类型ID", example = "1")
    private Integer questionTypeId;
    
    @Schema(description = "题库ID", example = "1")
    private Integer fieldId;
    
    @Schema(description = "知识点ID", example = "1")
    private Integer pointId;
    
    @Schema(description = "页码", example = "1")
    private Integer pageNum = 1;
    
    @Schema(description = "每页大小", example = "10")
    private Integer pageSize = 10;
}
