package com.example.wmsiescore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Schema(description = "提交考试请求参数")
public class SubmitExamRequest {

    @NotNull(message = "考试记录ID不能为空")
    @Positive(message = "考试记录ID必须为正数")
    @Schema(description = "考试记录ID", required = true, example = "1")
    private Long recordId;

    @NotBlank(message = "答案不能为空")
    @Schema(description = "答案JSON字符串", required = true, example = "{\"1\":\"A\",\"2\":\"B\"}")
    private String answers;
}
