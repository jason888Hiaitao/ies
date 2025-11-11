package com.example.wmsiescore.dto;

import com.example.wmsiescore.vo.QuestionVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 考试开始结果DTO
 * 包含考试记录ID和考题列表
 */
@Data
@ApiModel(value = "ExamStartResult", description = "考试开始结果对象")
public class ExamStartResult {
    
    @ApiModelProperty(value = "考试记录ID", example = "1")
    private Long examHistoryId;
    
    @ApiModelProperty(value = "考题列表")
    private List<QuestionVO> questions;
}