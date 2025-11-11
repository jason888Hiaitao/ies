package com.example.wmsiescore.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 试题查询条件DTO
 */
@Data
@ApiModel(value = "QuestionQueryDTO", description = "试题查询条件对象")
public class QuestionQueryDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "试题名称（模糊查询）", example = "Java基础")
    private String questionName;
    
    @ApiModelProperty(value = "试题类型ID", example = "1")
    private Integer questionTypeId;
    
    @ApiModelProperty(value = "题库ID", example = "1")
    private Integer fieldId;
    
    @ApiModelProperty(value = "知识点ID", example = "1")
    private Integer pointId;
    
    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNum = 1;
    
    @ApiModelProperty(value = "每页大小", example = "10")
    private Integer pageSize = 10;
}