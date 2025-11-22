package com.example.wmsiescore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Schema(description = "考试排名表实体类")
@Data
public class ExamRanking {
    @Schema(description = "用户ID", example = "1")
    private Long userId;
    @Schema(description = "用户姓名", example = "张三")
    private String userName;
    @Schema(description = "用户账号", example = "zhangsan")
    private String userAccount;
    @Schema(description = "得分", example = "85")
    private Integer score;
    @Schema(description = "总分", example = "100")
    private Integer totalScore;
    @Schema(description = "排名", example = "1")
    private Integer rank;
    @Schema(description = "实际用时（分钟）", example = "90")
    private Integer actualDuration;
    @Schema(description = "提交时间", example = "2023-01-01 12:00:00")
    private Date submitTime;
}
