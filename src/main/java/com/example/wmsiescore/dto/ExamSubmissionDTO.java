package com.example.wmsiescore.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

/**
 * 考试提交DTO
 * 用于接收用户提交的完整考试答案数据
 */
@Data
@ApiModel(value = "ExamSubmissionDTO", description = "考试提交请求对象")
public class ExamSubmissionDTO {
    
    @ApiModelProperty(value = "考试记录ID", example = "1")
    private Long examHistoryId;
    
    @ApiModelProperty(value = "用户ID", example = "1001")
    private Long userId;
    
    @ApiModelProperty(value = "试卷ID", example = "1")
    private Long examPaperId;
    
    @ApiModelProperty(value = "答案列表")
    private List<QuestionAnswerDTO> answers;
    
    /**
     * 构造函数
     */
    public ExamSubmissionDTO() {
    }
    
    /**
     * 构造函数
     * @param examHistoryId 考试记录ID
     * @param userId 用户ID
     * @param examPaperId 试卷ID
     * @param answers 答案列表
     */
    public ExamSubmissionDTO(Long examHistoryId, Long userId, Long examPaperId, List<QuestionAnswerDTO> answers) {
        this.examHistoryId = examHistoryId;
        this.userId = userId;
        this.examPaperId = examPaperId;
        this.answers = answers;
    }
}