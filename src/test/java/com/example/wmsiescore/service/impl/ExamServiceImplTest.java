package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.dto.ExamStartResult;
import com.example.wmsiescore.exception.ParameterValidationException;
import com.example.wmsiescore.exception.ResourceNotFoundException;
import com.example.wmsiescore.model.EtExamPaper;
import com.example.wmsiescore.model.EtQuestion;
import com.example.wmsiescore.model.EtUserExamHistory;
import com.example.wmsiescore.model.ExamAnalysis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamServiceImplTest {

//    @Mock
//    private ExamMapper examMapper;

    @InjectMocks
    private ExamServiceImpl examService;

    private EtUserExamHistory mockHistory;
    private EtExamPaper mockExamPaper;
    private List<EtQuestion> mockQuestions;
    private List<ExamAnalysis.KnowledgePoint> mockKnowledgePoints;

    @BeforeEach
    void setUp() {
        // 准备考试历史记录测试数据
        /*mockHistory = new EtUserExamHistory();
        mockHistory.setId(1L);
        mockHistory.setUserId(100L);
        mockHistory.setUserName("张三");
        mockHistory.setUserAccount("zhangsan");
        mockHistory.setExamPaperId(200L);
        mockHistory.setExamTitle("Java基础测试");
        mockHistory.setScore(80);
        mockHistory.setTotalScore(100);
        mockHistory.setDuration(120);
        mockHistory.setActualDuration(90);
        mockHistory.setStatus("completed");
        mockHistory.setStartTime(new Timestamp(System.currentTimeMillis() - 5400000)); // 1.5小时前
        mockHistory.setEndTime(new Timestamp(System.currentTimeMillis() - 3600000)); // 1小时前

        // 准备试卷测试数据
        mockExamPaper = new EtExamPaper();
        mockExamPaper.setId(200L);
        mockExamPaper.setTitle("Java基础测试");
        mockExamPaper.setDescription("Java基础知识测试");
        mockExamPaper.setDuration(120);
        mockExamPaper.setTotalScore(100);
        mockExamPaper.setPassScore(60);
        mockExamPaper.setStatus("published");

        // 准备题目测试数据
        EtQuestion question1 = new EtQuestion();
        question1.setId(1L);
        question1.setTitle("Java基础题目1");
        question1.setContent("Java是什么？");
        question1.setType("single_choice");
        question1.setCategory("Java基础");
        question1.setScore(10);
        question1.setDifficulty("easy");
        question1.setAnswer("A");
        question1.setAnalysis("Java是一种编程语言");

        EtQuestion question2 = new EtQuestion();
        question2.setId(2L);
        question2.setTitle("面向对象题目1");
        question2.setContent("什么是面向对象？");
        question2.setType("single_choice");
        question2.setCategory("面向对象");
        question2.setScore(10);
        question2.setDifficulty("medium");
        question2.setAnswer("B");
        question2.setAnalysis("面向对象是一种编程思想");

        mockQuestions = Arrays.asList(question1, question2);

        // 准备知识点测试数据
        ExamAnalysis.KnowledgePoint kp1 = new ExamAnalysis.KnowledgePoint();
        kp1.setCategory("Java基础");
        kp1.setTotalQuestions(1);
        kp1.setCorrectQuestions(1);
        kp1.setCorrectRate(100.0);

        ExamAnalysis.KnowledgePoint kp2 = new ExamAnalysis.KnowledgePoint();
        kp2.setCategory("面向对象");
        kp2.setTotalQuestions(1);
        kp2.setCorrectQuestions(0);
        kp2.setCorrectRate(0.0);

        mockKnowledgePoints = Arrays.asList(kp1, kp2);*/
    }

    @Test
    void testStartExam_Success() {
        // Given
        Long userId = 100L;
        Long examPaperId = 200L;
        
//        when(examMapper.getExamPaper(examPaperId)).thenReturn(mockExamPaper);

        // When
        ExamStartResult examStartResult = examService.startExam(userId, examPaperId);

        // Then
        assertEquals(0L, examStartResult.getExamHistoryId()); // 临时返回值
//        verify(examMapper).getExamPaper(examPaperId);
    }

    @Test
    void testStartExam_InvalidUserId() {
        // Given
        Long userId = -1L;
        Long examPaperId = 200L;

        // When & Then
        assertThrows(ParameterValidationException.class, () -> {
            examService.startExam(userId, examPaperId);
        });
    }

    @Test
    void testStartExam_InvalidExamPaperId() {
        // Given
        Long userId = 100L;
        Long examPaperId = null;

        // When & Then
        assertThrows(ParameterValidationException.class, () -> {
            examService.startExam(userId, examPaperId);
        });
    }

    @Test
    void testStartExam_ExamPaperNotFound() {
        // Given
        Long userId = 100L;
        Long examPaperId = 999L;
        
//        when(examMapper.getExamPaper(examPaperId)).thenReturn(null);

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            examService.startExam(userId, examPaperId);
        });
    }

    @Test
    void testStartExam_ExamPaperNotPublished() {
        // Given
        Long userId = 100L;
        Long examPaperId = 200L;
        mockExamPaper.setStatus("draft");
        
//        when(examMapper.getExamPaper(examPaperId)).thenReturn(mockExamPaper);

        // When & Then
        assertThrows(ParameterValidationException.class, () -> {
            examService.startExam(userId, examPaperId);
        });
    }

    @Test
    void testSubmitExam_Success() {
        // Given
        Long recordId = 1L;
        String answers = "{\"1\":\"A\",\"2\":\"B\"}";
        
//        when(examMapper.getUserExamHistory(recordId)).thenReturn(mockHistory);

        // When
        boolean result = examService.submitExam(recordId, answers);

        // Then
        assertTrue(result);
//        verify(examMapper).getUserExamHistory(recordId);
    }

    @Test
    void testSubmitExam_RecordNotFound() {
        // Given
        Long recordId = 999L;
        String answers = "{\"1\":\"A\"}";
        
//        when(examMapper.getUserExamHistory(recordId)).thenReturn(null);

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            examService.submitExam(recordId, answers);
        });
    }

    @Test
    void testSubmitExam_EmptyAnswers() {
        // Given
        Long recordId = 1L;
        String answers = "";

        // When & Then
        assertThrows(ParameterValidationException.class, () -> {
            examService.submitExam(recordId, answers);
        });
    }

    @Test
    void testAnalyzeExam_Success() {
        // Given
        Long recordId = 1L;
        
//        when(examMapper.getUserExamHistory(recordId)).thenReturn(mockHistory);
//        when(examMapper.getExamPaper(mockHistory.getExamPaperId())).thenReturn(mockExamPaper);
//        when(examMapper.getQuestionsByPaper(mockHistory.getExamPaperId())).thenReturn(mockQuestions);
//        when(examMapper.getKnowledgePoints(recordId)).thenReturn(mockKnowledgePoints);

        // When
        ExamAnalysis result = examService.analyzeExam(recordId);

        // Then
        assertNotNull(result);
        assertEquals("考试分析报告", result.getTitle());
        
        // 验证考试信息
        assertNotNull(result.getExamInfo());
        assertEquals("Java基础测试", result.getExamInfo().getName());
        assertEquals(2, result.getExamInfo().getTotalQuestions());
        assertEquals(120, result.getExamInfo().getDuration());
        assertEquals(90, result.getExamInfo().getActualDuration());
        
        // 验证分数信息
        assertNotNull(result.getScoreInfo());
        assertEquals(100, result.getScoreInfo().getTotalScore());
        assertEquals(60, result.getScoreInfo().getPassingScore());
        assertEquals(80, result.getScoreInfo().getObtainedScore());
        assertTrue(result.getScoreInfo().getIsPassed());
        assertEquals(80.0, result.getScoreInfo().getScoreRate());
        
        // 验证题目详情
        assertNotNull(result.getQuestionDetails());
        assertEquals(2, result.getQuestionDetails().size());
        
        // 验证知识点信息
        assertNotNull(result.getKnowledgePoints());
        assertEquals(2, result.getKnowledgePoints().size());
        assertEquals("Java基础", result.getKnowledgePoints().get(0).getCategory());
        assertEquals(100.0, result.getKnowledgePoints().get(0).getCorrectRate());
    }

    @Test
    void testAnalyzeExam_RecordNotFound() {
        // Given
        Long recordId = 999L;
        
//        when(examMapper.getUserExamHistory(recordId)).thenReturn(null);

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            examService.analyzeExam(recordId);
        });
    }

    @Test
    void testAnalyzeExam_ExamNotCompleted() {
        // Given
        Long recordId = 1L;
//        mockHistory.setStatus("incomplete");
        
//        when(examMapper.getUserExamHistory(recordId)).thenReturn(mockHistory);

        // When & Then
        assertThrows(ParameterValidationException.class, () -> {
            examService.analyzeExam(recordId);
        });
    }

    @Test
    void testAnalyzeExam_ExamPaperNotFound() {
        // Given
        Long recordId = 1L;
        
//        when(examMapper.getUserExamHistory(recordId)).thenReturn(mockHistory);
//        when(examMapper.getExamPaper(mockHistory.getExamPaperId())).thenReturn(null);

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            examService.analyzeExam(recordId);
        });
    }

    @Test
    void testAnalyzeExam_NoQuestions() {
        // Given
        Long recordId = 1L;
        
//        when(examMapper.getUserExamHistory(recordId)).thenReturn(mockHistory);
//        when(examMapper.getExamPaper(mockHistory.getExamPaperId())).thenReturn(mockExamPaper);
//        when(examMapper.getQuestionsByPaper(mockHistory.getExamPaperId())).thenReturn(Arrays.asList());

        // When & Then
        assertThrows(ParameterValidationException.class, () -> {
            examService.analyzeExam(recordId);
        });
    }

    @Test
    void testAnalyzeExam_InvalidRecordId() {
        // Given
        Long recordId = -1L;

        // When & Then
        assertThrows(ParameterValidationException.class, () -> {
            examService.analyzeExam(recordId);
        });
    }

    @Test
    void testAnalyzeExam_NullFieldsHandling() {
        // Given
        /*Long recordId = 1L;
        EtUserExamHistory historyWithNulls = new EtUserExamHistory();
        historyWithNulls.setId(recordId);
        historyWithNulls.setStatus("completed");
        historyWithNulls.setStartTime(new Timestamp(System.currentTimeMillis() - 5400000));
        historyWithNulls.setEndTime(new Timestamp(System.currentTimeMillis() - 3600000));
        historyWithNulls.setExamPaperId(200L);
        historyWithNulls.setScore(0);
        historyWithNulls.setTotalScore(0);
        
        EtExamPaper paperWithNulls = new EtExamPaper();
        paperWithNulls.setId(200L);
        paperWithNulls.setPassScore(60);
        
        when(examMapper.getUserExamHistory(recordId)).thenReturn(historyWithNulls);
        when(examMapper.getExamPaper(200L)).thenReturn(paperWithNulls);
        when(examMapper.getQuestionsByPaper(200L)).thenReturn(mockQuestions);
        when(examMapper.getKnowledgePoints(recordId)).thenReturn(Arrays.asList());

        // When
        ExamAnalysis result = examService.analyzeExam(recordId);

        // Then
        assertNotNull(result);
        assertNotNull(result.getExamInfo());
        assertEquals("未知考试", result.getExamInfo().getName());
        assertEquals(2, result.getExamInfo().getTotalQuestions());
        
        assertNotNull(result.getScoreInfo());
        assertEquals(0, result.getScoreInfo().getTotalScore());
        assertEquals(60, result.getScoreInfo().getPassingScore());
        assertEquals(0, result.getScoreInfo().getObtainedScore());
        assertFalse(result.getScoreInfo().getIsPassed());
        assertEquals(0.0, result.getScoreInfo().getScoreRate());*/
    }
}