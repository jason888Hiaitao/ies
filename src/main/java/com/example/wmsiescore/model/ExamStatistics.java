package com.example.wmsiescore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@ApiModel(value = "ExamStatistics", description = "考试统计表实体类")
@Data
public class ExamStatistics {
    @ApiModelProperty(value = "试卷ID", example = "1")
    private Long examPaperId;
    @ApiModelProperty(value = "考试标题", example = "数学期末考试")
    private String examTitle;
    @ApiModelProperty(value = "总参与人数", example = "100")
    private Integer totalParticipants; // 总参与人数
    @ApiModelProperty(value = "实际参考人数", example = "95")
    private Integer actualParticipants; // 实际参考人数
    @ApiModelProperty(value = "缺考人数", example = "5")
    private Integer absentCount; // 缺考人数
    @ApiModelProperty(value = "平均分", example = "78.5")
    private BigDecimal averageScore; // 平均分
    @ApiModelProperty(value = "最高分", example = "98")
    private Integer maxScore; // 最高分
    @ApiModelProperty(value = "最低分", example = "45")
    private Integer minScore; // 最低分
    @ApiModelProperty(value = "及格率", example = "0.85")
    private BigDecimal passRate; // 及格率
    @ApiModelProperty(value = "排名列表")
    private List<ExamRanking> rankings; // 排名列表
}