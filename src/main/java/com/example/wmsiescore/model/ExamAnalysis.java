package com.example.wmsiescore.model;

import com.example.wmsiescore.dto.QuestionAnswerDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "考试分析结果对象")
public class ExamAnalysis {
    @Schema(description = "分析标题", example = "Java基础考试分析")
    private String title;
    @Schema(description = "考试信息")
    private ExamInfo examInfo;
    @Schema(description = "分数信息")
    private ScoreInfo scoreInfo;
    @Schema(description = "知识点分析")
    private List<KnowledgePoint> knowledgePoints;
    @Schema(description = "题目详情")
    private List<QuestionDetail> questionDetails;

    @Data
    @Schema(description = "考试信息")
    public static class ExamInfo {
        @Schema(description = "考试名称", example = "Java基础测试")
        private String examName;
        @Schema(description = "考试时间", example = "2024-01-01 10:00:00")
        private String examTime;
        @Schema(description = "考生姓名", example = "张三")
        private String studentName;
        @Schema(description = "考生ID", example = "1001")
        private Long studentId;
        @Schema(description = "考试名称", example = "Java基础测试")
        private String name;
        @Schema(description = "考试时间", example = "2024-01-01 10:00:00")
        private String time;
        @Schema(description = "提交时间", example = "2024-01-01 11:30:00")
        private String submitTime;
        @Schema(description = "总题数", example = "50")
        private Integer totalQuestions;
        @Schema(description = "正确题数", example = "42")
        private Integer correctQuestions;
        @Schema(description = "错误题数", example = "8")
        private Integer wrongQuestions;
        @Schema(description = "考试时长（分钟）", example = "120")
        private Integer duration;
        @Schema(description = "实际用时（分钟）", example = "90")
        private Integer actualDuration;
    }

    @Data
    @Schema(description = "分数信息")
    public static class ScoreInfo {
        @Schema(description = "总分", example = "100.0")
        private BigDecimal totalScore;
        @Schema(description = "及格分", example = "60.0")
        private BigDecimal passingScore;
        @Schema(description = "得分", example = "85.0")
        private BigDecimal obtainedScore;
        @Schema(description = "是否通过", example = "true")
        private Boolean isPassed;
        @Schema(description = "得分率", example = "0.85")
        private Double scoreRate;
    }

    @Data
    @Schema(description = "知识点分析")
    public static class KnowledgePoint {
        @Schema(description = "题目分类", example = "Java基础")
        private String category;
        @Schema(description = "该类题目总数", example = "10")
        private Integer totalQuestions;
        @Schema(description = "该类正确题目数", example = "8")
        private Integer correctQuestions;
        @Schema(description = "正确率", example = "0.8")
        private Double correctRate;
    }

    @Data
    @Schema(description = "题目详情")
    public static class QuestionDetail {
        @Schema(description = "问题ID", example = "1")
        private Long questionId;
        @Schema(description = "题目标题", example = "Java基础题目")
        private String title;
        @Schema(description = "题目内容", example = "题目详细内容")
        private String content;
        @Schema(description = "题目类型", example = "单选题")
        private String type;
        @Schema(description = "题目分类", example = "Java基础")
        private String category;
        @Schema(description = "题目分值", example = "10.0")
        private BigDecimal score;
        @Schema(description = "题目难度", example = "中等")
        private String difficulty;
        @Schema(description = "用户答案")
        private QuestionAnswerDTO userAnswer;
        @Schema(description = "正确答案", example = "A")
        private String correctAnswer;
        @Schema(description = "是否正确", example = "true")
        private Boolean isCorrect;
        @Schema(description = "答案解析", example = "该题考查的是Java基础知识")
        private String analysis;
    }
}
