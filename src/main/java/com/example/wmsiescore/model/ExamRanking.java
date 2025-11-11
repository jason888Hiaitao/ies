package com.example.wmsiescore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "ExamRanking", description = "考试排名表实体类")
@Data
public class ExamRanking {
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long userId;
    @ApiModelProperty(value = "用户姓名", example = "张三")
    private String userName;
    @ApiModelProperty(value = "用户账号", example = "zhangsan")
    private String userAccount;
    @ApiModelProperty(value = "得分", example = "85")
    private Integer score;
    @ApiModelProperty(value = "总分", example = "100")
    private Integer totalScore;
    @ApiModelProperty(value = "排名", example = "1")
    private Integer rank;
    @ApiModelProperty(value = "实际用时（分钟）", example = "90")
    private Integer actualDuration;
    @ApiModelProperty(value = "提交时间", example = "2023-01-01 12:00:00")
    private Date submitTime;
}