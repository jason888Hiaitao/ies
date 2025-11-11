package com.example.wmsiescore.model;

import com.example.wmsiescore.dto.QuestionAnswerDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(value = "ExamAnalysis", description = "考试分析结果对象")
public class ExamAnalysis {
    @ApiModelProperty(value = "分析标题", example = "Java基础考试分析")
    private String title;
    @ApiModelProperty(value = "考试信息")
    private ExamInfo examInfo;
    @ApiModelProperty(value = "分数信息")
    private ScoreInfo scoreInfo;
    @ApiModelProperty(value = "知识点分析")
    private List<KnowledgePoint> knowledgePoints;
    @ApiModelProperty(value = "题目详情")
    private List<QuestionDetail> questionDetails;

    @Data
    @ApiModel(value = "ExamInfo", description = "考试信息")
    public static class ExamInfo {
        @ApiModelProperty(value = "考试名称", example = "Java基础测试")
        private String examName;
        @ApiModelProperty(value = "考试时间", example = "2024-01-01 10:00:00")
        private String examTime;
        @ApiModelProperty(value = "考生姓名", example = "张三")
        private String studentName;
        @ApiModelProperty(value = "考生ID", example = "1001")
        private Long studentId;
        @ApiModelProperty(value = "考试名称", example = "Java基础测试")
        private String name;
        @ApiModelProperty(value = "考试时间", example = "2024-01-01 10:00:00")
        private String time;
        @ApiModelProperty(value = "提交时间", example = "2024-01-01 11:30:00")
        private String submitTime;
        @ApiModelProperty(value = "总题数", example = "50")
        private Integer totalQuestions;
        @ApiModelProperty(value = "正确题数", example = "42")
        private Integer correctQuestions;
        @ApiModelProperty(value = "错误题数", example = "8")
        private Integer wrongQuestions;
        @ApiModelProperty(value = "考试时长（分钟）", example = "120")
        private Integer duration;
        @ApiModelProperty(value = "实际用时（分钟）", example = "90")
        private Integer actualDuration;
    }

    @Data
    @ApiModel(value = "ScoreInfo", description = "分数信息")
    public static class ScoreInfo {
        @ApiModelProperty(value = "总分", example = "100.0")
        private BigDecimal totalScore;
        @ApiModelProperty(value = "及格分", example = "60.0")
        private BigDecimal passingScore;
        @ApiModelProperty(value = "得分", example = "85.0")
        private BigDecimal obtainedScore;
        @ApiModelProperty(value = "是否通过", example = "true")
        private Boolean isPassed;
        @ApiModelProperty(value = "得分率", example = "0.85")
        private Double scoreRate;
    }

    @Data
    @ApiModel(value = "KnowledgePoint", description = "知识点分析")
    public static class KnowledgePoint {
        @ApiModelProperty(value = "题目分类", example = "Java基础")
        private String category;
        @ApiModelProperty(value = "该类题目总数", example = "10")
        private Integer totalQuestions;
        @ApiModelProperty(value = "该类正确题目数", example = "8")
        private Integer correctQuestions;
        @ApiModelProperty(value = "正确率", example = "0.8")
        private Double correctRate;
    }

    @Data
    @ApiModel(value = "QuestionDetail", description = "题目详情")
    public static class QuestionDetail {
        @ApiModelProperty(value = "问题ID", example = "1")
        private Long questionId;
        @ApiModelProperty(value = "题目标题", example = "Java基础题目")
        private String title;
        @ApiModelProperty(value = "题目内容", example = "题目详细内容")
        private String content;
        @ApiModelProperty(value = "题目类型", example = "单选题")
        private String type;
        @ApiModelProperty(value = "题目分类", example = "Java基础")
        private String category;
        @ApiModelProperty(value = "题目分值", example = "10.0")
        private BigDecimal score;
        @ApiModelProperty(value = "题目难度", example = "中等")
        private String difficulty;
        @ApiModelProperty(value = "用户答案")
        private QuestionAnswerDTO userAnswer;
        @ApiModelProperty(value = "正确答案", example = "A")
        private String correctAnswer;
        @ApiModelProperty(value = "是否正确", example = "true")
        private Boolean isCorrect;
        @ApiModelProperty(value = "答案解析", example = "该题考查的是Java基础知识")
        private String analysis;
    }
}