package com.example.wmsiescore.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@ApiModel(description = "开始考试请求参数")
public class StartExamRequest {

    @NotNull(message = "用户ID不能为空")
    @Positive(message = "用户ID必须为正数")
    @ApiModelProperty(value = "用户ID", required = true, example = "1")
    private Long userId;

    @NotNull(message = "试卷ID不能为空")
    @Positive(message = "试卷ID必须为正数")
    @ApiModelProperty(value = "试卷ID", required = true, example = "1")
    private Long examPaperId;
}