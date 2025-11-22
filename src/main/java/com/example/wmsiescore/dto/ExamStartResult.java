package com.example.wmsiescore.dto;

import com.example.wmsiescore.vo.QuestionVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 考试开始结果DTO
 * 包含考试记录ID和考题列表
 */
@Data
@Schema(description = "考试开始结果对象")
public class ExamStartResult {
    
    @Schema(description = "考试记录ID", example = "1")
    private Long examHistoryId;
    
    @Schema(description = "考题列表")
    private List<QuestionVO> questions;
}
