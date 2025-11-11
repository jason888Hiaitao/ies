package com.example.wmsiescore.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 考试提交请求DTO
 * 统一考试提交的入参对象
 */
@Data
@ApiModel(value = "ExamSubmitRequestDTO", description = "考试提交请求对象")
public class ExamSubmitRequestDTO {

    @ApiModelProperty(value = "考试记录ID", example = "1", required = true)
    @NotNull(message = "考试记录ID不能为空")
    private Long examHistoryId;

    @ApiModelProperty(value = "用户ID", example = "1001", required = true)
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @ApiModelProperty(value = "试卷ID", example = "1", required = true)
    @NotNull(message = "试卷ID不能为空")
    private Long examPaperId;

    @ApiModelProperty(value = "答案列表")
    private List<QuestionAnswerDTO> answers;

    /**
     * 无参构造函数
     */
    public ExamSubmitRequestDTO() {
    }

    /**
     * 全参构造函数
     */
    public ExamSubmitRequestDTO(Long examHistoryId, Long userId, Long examPaperId, List<QuestionAnswerDTO> answers) {
        this.examHistoryId = examHistoryId;
        this.userId = userId;
        this.examPaperId = examPaperId;
        this.answers = answers;
    }
}