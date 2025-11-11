package com.example.wmsiescore.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 问题答案DTO
 * 用于接收用户提交的答案数据
 */
@Data
@ApiModel(value = "QuestionAnswerDTO", description = "问题答案对象")
public class QuestionAnswerDTO {
    
    @ApiModelProperty(value = "问题ID", example = "1")
    private Long questionId;
    
    @ApiModelProperty(value = "用户答案", example = "A")
    private String answer;
    
    /**
     * 构造函数
     */
    public QuestionAnswerDTO() {
    }
    
    /**
     * 构造函数
     * @param questionId 问题ID
     * @param answer 答案
     */
    public QuestionAnswerDTO(Long questionId, String answer) {
        this.questionId = questionId;
        this.answer = answer;
    }
}