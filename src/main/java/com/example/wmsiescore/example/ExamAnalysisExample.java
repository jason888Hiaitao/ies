package com.example.wmsiescore.example;

import com.example.wmsiescore.model.ExamAnalysis;
import com.example.wmsiescore.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 考试分析功能使用示例
 */
@Component
public class ExamAnalysisExample {

    @Autowired
    private ExamService examService;

    /**
     * 分析考试结果示例
     */
    public void analyzeExamExample() {
        try {
            // 假设考试历史记录ID为1
            Long historyId = 1L;
            
            // 调用分析方法
            ExamAnalysis analysis = examService.analyzeExam(historyId);
            
            // 输出分析结果
            System.out.println("=== 考试分析报告 ===");
            System.out.println("标题: " + analysis.getTitle());
            
            // 考试基本信息
            if (analysis.getExamInfo() != null) {
                ExamAnalysis.ExamInfo examInfo = analysis.getExamInfo();
                System.out.println("\n--- 考试信息 ---");
                System.out.println("考试名称: " + examInfo.getName());
                System.out.println("开始时间: " + examInfo.getTime());
                System.out.println("提交时间: " + examInfo.getSubmitTime());
                System.out.println("总题数: " + examInfo.getTotalQuestions());
                System.out.println("正确题数: " + examInfo.getCorrectQuestions());
                System.out.println("错误题数: " + examInfo.getWrongQuestions());
                System.out.println("考试时长: " + examInfo.getDuration() + "分钟");
                System.out.println("实际用时: " + examInfo.getActualDuration() + "分钟");
            }
            
            // 分数信息
            if (analysis.getScoreInfo() != null) {
                ExamAnalysis.ScoreInfo scoreInfo = analysis.getScoreInfo();
                System.out.println("\n--- 分数信息 ---");
                System.out.println("总分: " + scoreInfo.getTotalScore());
                System.out.println("及格分: " + scoreInfo.getPassingScore());
                System.out.println("得分: " + scoreInfo.getObtainedScore());
                System.out.println("得分率: " + scoreInfo.getScoreRate() + "%");
                System.out.println("是否及格: " + (scoreInfo.getIsPassed() ? "是" : "否"));
            }
            
            // 知识点分析
            if (analysis.getKnowledgePoints() != null && !analysis.getKnowledgePoints().isEmpty()) {
                System.out.println("\n--- 知识点掌握情况 ---");
                for (ExamAnalysis.KnowledgePoint kp : analysis.getKnowledgePoints()) {
                    System.out.println("知识点: " + kp.getCategory());
                    System.out.println("  题目总数: " + kp.getTotalQuestions());
                    System.out.println("  正确题数: " + kp.getCorrectQuestions());
                    System.out.println("  正确率: " + kp.getCorrectRate() + "%");
                    System.out.println();
                }
            }
            
            // 题目详情
            if (analysis.getQuestionDetails() != null && !analysis.getQuestionDetails().isEmpty()) {
                System.out.println("--- 题目详情 ---");
                for (ExamAnalysis.QuestionDetail detail : analysis.getQuestionDetails()) {
                    System.out.println("题目ID: " + detail.getQuestionId());
                    System.out.println("标题: " + detail.getTitle());
                    System.out.println("内容: " + detail.getContent());
                    System.out.println("题型: " + detail.getType());
                    System.out.println("分类: " + detail.getCategory());
                    System.out.println("分值: " + detail.getScore());
                    System.out.println("难度: " + detail.getDifficulty());
                    System.out.println("用户答案: " + detail.getUserAnswer());
                    System.out.println("正确答案: " + detail.getCorrectAnswer());
                    System.out.println("是否正确: " + (detail.getIsCorrect() ? "是" : "否"));
                    if (detail.getAnalysis() != null) {
                        System.out.println("答案解析: " + detail.getAnalysis());
                    }
                    System.out.println("-------------------");
                }
            }
            
        } catch (Exception e) {
            System.err.println("考试分析失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 批量分析多个考试记录
     */
    public void batchAnalyzeExams(Long[] historyIds) {
        System.out.println("=== 批量考试分析 ===");
        
        for (Long historyId : historyIds) {
            try {
                System.out.println("\n分析考试记录ID: " + historyId);
                ExamAnalysis analysis = examService.analyzeExam(historyId);
                
                // 简要输出关键信息
                if (analysis.getExamInfo() != null && analysis.getScoreInfo() != null) {
                    System.out.println("考试: " + analysis.getExamInfo().getName());
                    System.out.println("得分: " + analysis.getScoreInfo().getObtainedScore() + 
                                     "/" + analysis.getScoreInfo().getTotalScore() +
                                     " (" + analysis.getScoreInfo().getScoreRate() + "%)");
                    System.out.println("状态: " + (analysis.getScoreInfo().getIsPassed() ? "及格" : "不及格"));
                }
                
            } catch (Exception e) {
                System.err.println("分析考试记录 " + historyId + " 失败: " + e.getMessage());
            }
        }
    }
    
    /**
     * 生成学习建议
     */
    public void generateStudySuggestions(Long historyId) {
        try {
            ExamAnalysis analysis = examService.analyzeExam(historyId);
            
            System.out.println("=== 学习建议 ===");
            
            if (analysis.getKnowledgePoints() != null) {
                System.out.println("基于考试结果的学习建议:");
                
                for (ExamAnalysis.KnowledgePoint kp : analysis.getKnowledgePoints()) {
                    if (kp.getCorrectRate() < 60.0) {
                        System.out.println("• " + kp.getCategory() + " - 需要加强复习 (正确率: " + kp.getCorrectRate() + "%)");
                    } else if (kp.getCorrectRate() < 80.0) {
                        System.out.println("• " + kp.getCategory() + " - 有提升空间 (正确率: " + kp.getCorrectRate() + "%)");
                    } else {
                        System.out.println("• " + kp.getCategory() + " - 掌握良好 (正确率: " + kp.getCorrectRate() + "%)");
                    }
                }
            }
            
            if (analysis.getScoreInfo() != null && !analysis.getScoreInfo().getIsPassed()) {
                System.out.println("\n总体建议: 本次考试未及格，建议全面复习各知识点，特别是正确率较低的部分。");
            } else if (analysis.getScoreInfo() != null && analysis.getScoreInfo().getScoreRate() < 85.0) {
                System.out.println("\n总体建议: 考试已通过，但仍有提升空间，建议针对薄弱环节进行专项练习。");
            } else {
                System.out.println("\n总体建议: 考试成绩优秀，继续保持！");
            }
            
        } catch (Exception e) {
            System.err.println("生成学习建议失败: " + e.getMessage());
        }
    }
}