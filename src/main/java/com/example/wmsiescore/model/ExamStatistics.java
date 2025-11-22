package com.example.wmsiescore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "考试统计表实体类")
@Data
public class ExamStatistics {
    @Schema(description = "试卷ID", example = "1")
    private Long examPaperId;
    @Schema(description = "考试标题", example = "数学期末考试")
    private String examTitle;
    @Schema(description = "总参与人数", example = "100")
    private Integer totalParticipants; // 总参与人数
    @Schema(description = "实际参考人数", example = "95")
    private Integer actualParticipants; // 实际参考人数
    @Schema(description = "缺考人数", example = "5")
    private Integer absentCount; // 缺考人数
    @Schema(description = "平均分", example = "78.5")
    private BigDecimal averageScore; // 平均分
    @Schema(description = "最高分", example = "98")
    private Integer maxScore; // 最高分
    @Schema(description = "最低分", example = "45")
    private Integer minScore; // 最低分
    @Schema(description = "及格率", example = "0.85")
    private BigDecimal passRate; // 及格率
    @Schema(description = "排名列表")
    private List<ExamRanking> rankings; // 排名列表
}
