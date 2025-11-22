package com.example.wmsiescore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Schema(description = "开始考试请求参数")
public class StartExamRequest {

    @NotNull(message = "用户ID不能为空")
    @Positive(message = "用户ID必须为正数")
    @Schema(description = "用户ID", required = true, example = "1")
    private Long userId;

    @NotNull(message = "试卷ID不能为空")
    @Positive(message = "试卷ID必须为正数")
    @Schema(description = "试卷ID", required = true, example = "1")
    private Long examPaperId;
}
