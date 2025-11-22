package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.enums.ExamPaperStatusEnum;
import com.example.wmsiescore.enums.QuestionTypeEnum;
import com.example.wmsiescore.exception.ResourceNotFoundException;
import com.example.wmsiescore.exception.ParameterValidationException;
import com.example.wmsiescore.dao.UnifiedEtExamPaperDao;
import com.example.wmsiescore.dao.UnifiedEtQuestionDao;
import com.example.wmsiescore.dao.UnifiedEtUserExamHistoryDao;
import com.example.wmsiescore.model.EtExamPaper;
import com.example.wmsiescore.model.EtQuestion;
import com.example.wmsiescore.model.EtUserExamHistory;
import com.example.wmsiescore.model.ExamAnalysis;
import com.example.wmsiescore.model.PendingExamDTO;
import com.example.wmsiescore.dto.QuestionAnswerDTO;
import com.example.wmsiescore.dto.ExamSubmissionDTO;
import com.example.wmsiescore.dto.ExamStartResult;
import com.example.wmsiescore.vo.QuestionVO;
import com.example.wmsiescore.service.ExamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.alibaba.fastjson.JSONObject;

@Slf4j
@Service
public class ExamServiceImpl implements ExamService {

//    @Autowired
//    private ExamMapper examMapper;

    @Autowired
    private UnifiedEtExamPaperDao unifiedEtExamPaperDao;
    
    @Autowired
    private UnifiedEtQuestionDao unifiedEtQuestionDao;
    
    @Autowired
    private UnifiedEtUserExamHistoryDao unifiedEtUserExamHistoryDao;

    @Override
    public ExamStartResult startExam(Long userId, Long examPaperId) {
        // 参数验证
        if (userId == null || userId <= 0) {
            throw new ParameterValidationException("userId", "用户ID必须为正数");
        }
        if (examPaperId == null || examPaperId <= 0) {
            throw new ParameterValidationException("examPaperId", "试卷ID必须为正数");
        }

        // 检查试卷是否存在且可考试
        EtExamPaper examPaper = unifiedEtExamPaperDao.selectById(examPaperId);
        if (examPaper == null) {
            throw new ResourceNotFoundException("试卷", examPaperId);
        }
        if (!ExamPaperStatusEnum.PUBLISHED.getCode().equals(examPaper.getStatus())) {
            throw new ParameterValidationException("examPaperId", "试卷未发布，无法开始考试");
        }

        // 创建考试历史记录
        EtUserExamHistory examHistory = new EtUserExamHistory();
        examHistory.setUserId(userId);
        examHistory.setExamPaperId(examPaperId);
        examHistory.setDuration(examPaper.getDuration());
        examHistory.setCreateTime(new Timestamp(new Date().getTime()));
        examHistory.setPointGet(BigDecimal.ZERO); // 初始分数为0
        
        // 保存考试历史记录
        unifiedEtUserExamHistoryDao.insertSelective(examHistory);
        
        // 获取试卷题目列表
        List<EtQuestion> questions = unifiedEtQuestionDao.selectByExamPaperId(examPaperId);
        if (questions == null) {
            questions = new ArrayList<>();
        }
        
        // 转换为QuestionVO，排除参考答案等敏感信息
        List<QuestionVO> questionVOs = new ArrayList<>();
        for (EtQuestion question : questions) {
            QuestionVO vo = new QuestionVO();
            vo.setId(question.getId());
            vo.setTitle(question.getName());
            vo.setContent(question.getContent());
            vo.setQuestionTypeId(question.getQuestionTypeId());
            vo.setDifficulty(question.getDifficulty());
            vo.setScore(question.getPoints());
            vo.setGroupId(question.getGroupId());
            vo.setCreator(question.getCreator());
            vo.setCreateTime(question.getCreateTime());
            vo.setUpdateTime(question.getLastModify());
            vo.setIsVisible(question.getIsVisible());
            vo.setExposeTimes(question.getExposeTimes());
            vo.setRightTimes(question.getRightTimes());
            vo.setWrongTimes(question.getWrongTimes());
            // 不包含参考答案、选项等敏感信息
            questionVOs.add(vo);
        }
        
        // 构建返回结果
        ExamStartResult result = new ExamStartResult();
        result.setExamHistoryId(examHistory.getHistId());
        result.setQuestions(questionVOs);
        
        log.info("用户{}开始考试，试卷ID：{}，考试记录ID：{}，题目数量：{}", userId, examPaperId, examHistory.getHistId(), questions.size());
        return result;
    }

    @Override
    public boolean submitExam(Long recordId, String answers) {
        // 参数验证
        if (recordId == null || recordId <= 0) {
            throw new ParameterValidationException("recordId", "考试记录ID必须为正数");
        }
        if (!StringUtils.hasText(answers)) {
            throw new ParameterValidationException("answers", "答案不能为空");
        }

        // 检查考试记录是否存在
        EtUserExamHistory history = unifiedEtUserExamHistoryDao.selectById(recordId);
        if (history == null) {
            throw new ResourceNotFoundException("考试历史记录", recordId);
        }
        //提交的时候要计算答案与试卷的答案比对，然后计算得分，考试时长

        // 解析前端提交的答案（Map格式JSON，key=考题id）
        ObjectMapper objectMapper = new ObjectMapper();
        Map<Long, String> userAnswers;
        try {
            userAnswers = objectMapper.readValue(answers, Map.class);
        } catch (Exception e) {
            throw new ParameterValidationException("answers", "答案格式错误，必须是JSON格式");
        }
        
        return submitExam(recordId, userAnswers);
    }
    
    @Override
    public boolean submitExam(Long recordId, Map<Long, String> userAnswers) {
        // 参数验证
        if (recordId == null || recordId <= 0) {
            throw new ParameterValidationException("recordId", "考试记录ID必须为正数");
        }
        if (userAnswers == null || userAnswers.isEmpty()) {
            throw new ParameterValidationException("userAnswers", "用户答案不能为空");
        }

        // 检查考试记录是否存在
        EtUserExamHistory history = unifiedEtUserExamHistoryDao.selectById(recordId);
        if (history == null) {
            throw new ResourceNotFoundException("考试历史记录", recordId);
        }
        
        // 获取试卷信息
        EtExamPaper examPaper = unifiedEtExamPaperDao.selectById(history.getExamPaperId());
        if (examPaper == null) {
            throw new ResourceNotFoundException("试卷", history.getExamPaperId());
        }
        
        // 计算考试得分
        BigDecimal totalScore = BigDecimal.ZERO;
        for (Map.Entry<Long, String> entry : userAnswers.entrySet()) {
            Long questionId = entry.getKey();
            String userAnswer = entry.getValue();
            
            // 获取题目信息
            EtQuestion question = unifiedEtQuestionDao.selectById(questionId);
            if (question == null) {
                log.warn("题目ID {} 不存在，跳过评分", questionId);
                continue;
            }
            
            // 根据题型进行答案比对和得分计算
            boolean isCorrect = false;
            Long questionType = question.getQuestionTypeId();
            
            if (QuestionTypeEnum.SINGLE_CHOICE.getCode().equals(questionType) || QuestionTypeEnum.TRUE_FALSE.getCode().equals(questionType)) {
                // 单选题和填空题：精确匹配
                isCorrect = userAnswer != null && userAnswer.trim().equals(question.getReference().trim());
            } else if (QuestionTypeEnum.MULTIPLE_CHOICE.getCode().equals(questionType)) {
                // 多选题：需要处理多个答案选项
                isCorrect = compareMultipleChoiceAnswers(userAnswer, question.getReference());
            }
            
            if (isCorrect) {
                totalScore = totalScore.add(question.getPoints());
                log.info("题目ID {} 答对，获得 {} 分", questionId, question.getPoints());
            } else {
                log.info("题目ID {} 答错，正确答案：{}，用户答案：{}", questionId, question.getReference(), userAnswer);
            }
        }
        
        // 计算考试时长（分钟）
        long startTime = history.getCreateTime().getTime();
        long submitTime = new Date().getTime();
        long durationMinutes = (submitTime - startTime) / (60 * 1000);
        
        // 更新考试记录
        history.setAnswerSheet(JSONObject.toJSONString(userAnswers));
        history.setSubmitTime(new Timestamp(submitTime));
        history.setPointGet(totalScore);
        history.setDuration((int) durationMinutes);
        
        // 保存更新的考试记录
        unifiedEtUserExamHistoryDao.updateById(history);
        
        log.info("考试记录{}提交成功，得分：{}，考试时长：{}分钟", recordId, totalScore, durationMinutes);
        return true;
    }
    
    @Override
    public boolean submitExam(ExamSubmissionDTO examSubmission) {
        if (examSubmission == null) {
            throw new ParameterValidationException("考试提交数据不能为空");
        }
        
        // 转换为Map格式，复用原有逻辑
        Map<Long, String> userAnswers = new HashMap<>();
        if (examSubmission.getAnswers() != null) {
            for (QuestionAnswerDTO answer : examSubmission.getAnswers()) {
                if (answer.getQuestionId() != null && answer.getAnswer() != null) {
                    userAnswers.put(answer.getQuestionId(), answer.getAnswer());
                }
            }
        }
        
        return submitExam(examSubmission.getExamHistoryId(), userAnswers);
    }
    
    /**
     * 比较多选题答案
     * @param userAnswer 用户答案（逗号分隔的选项）
     * @param correctAnswer 正确答案（逗号分隔的选项）
     * @return 是否完全正确
     */
    private boolean compareMultipleChoiceAnswers(String userAnswer, String correctAnswer) {
        if (userAnswer == null || correctAnswer == null) {
            return false;
        }
        
        // 分割答案选项
        String[] userOptions = userAnswer.split(",");
        String[] correctOptions = correctAnswer.split(",");
        
        // 去重并排序
        Set<String> userSet = new TreeSet<>();
        Set<String> correctSet = new TreeSet<>();
        
        for (String option : userOptions) {
            String trimmed = option.trim();
            if (!trimmed.isEmpty()) {
                userSet.add(trimmed);
            }
        }
        
        for (String option : correctOptions) {
            String trimmed = option.trim();
            if (!trimmed.isEmpty()) {
                correctSet.add(trimmed);
            }
        }
        
        // 比较两个集合是否相等
        return userSet.equals(correctSet);
    }

    @Override
    public ExamAnalysis analyzeExam(Long recordId) {
        // 参数验证
        if (recordId == null || recordId <= 0) {
            throw new ParameterValidationException("recordId", "考试记录ID必须为正数");
        }

        // 1. 查询考试历史记录
        EtUserExamHistory history = unifiedEtUserExamHistoryDao.selectById(recordId);
        if (history == null) {
            throw new ResourceNotFoundException("考试历史记录", recordId);
        }

        // 2. 检查考试状态
        if (history.getSubmitTime()==null) {
            throw new ParameterValidationException("recordId", "考试尚未完成，无法进行分析");
        }

        // 3. 查询试卷信息
        EtExamPaper examPaper = unifiedEtExamPaperDao.selectById(history.getExamPaperId());
        if (examPaper == null) {
            throw new ResourceNotFoundException("试卷", history.getExamPaperId());
        }

        // 4. 查询试卷题目
        List<EtQuestion> questions = unifiedEtQuestionDao.selectByExamPaperId(history.getExamPaperId());
        if (questions == null || questions.isEmpty()) {
            throw new ParameterValidationException("examPaperId", "试卷中没有题目");
        }

        // 5. 构建分析报告
        ExamAnalysis analysis = new ExamAnalysis();
        analysis.setTitle("考试分析报告");
        
        // 考试信息
        ExamAnalysis.ExamInfo examInfo = new ExamAnalysis.ExamInfo();
        examInfo.setName(examPaper.getName());
        examInfo.setTime(formatTimestamp(history.getCreateTime()));
        examInfo.setSubmitTime(formatTimestamp(history.getSubmitTime()));
        examInfo.setTotalQuestions(questions.size());
        examInfo.setDuration(examPaper.getDuration());
        examInfo.setActualDuration(history.getDuration());

        analysis.setExamInfo(examInfo);
        

        
        // 题目详情分析
        List<ExamAnalysis.QuestionDetail> questionDetails = analyzeQuestionDetails(history, questions);
        analysis.setQuestionDetails(questionDetails);
        
        // 计算正确和错误题目数
        int correctCount = (int) questionDetails.stream().mapToLong(q -> q.getIsCorrect() ? 1 : 0).sum();
        int wrongCount = questionDetails.size() - correctCount;
        examInfo.setCorrectQuestions(correctCount);
        examInfo.setWrongQuestions(wrongCount);

        // 分数信息,设置考试总分、及格分、实际得分、是否及格标识
        ExamAnalysis.ScoreInfo scoreInfo = new ExamAnalysis.ScoreInfo();
        scoreInfo.setTotalScore(examPaper.getTotalPoint());
        scoreInfo.setPassingScore(examPaper.getPassPoint());
        scoreInfo.setObtainedScore(history.getPointGet());
        scoreInfo.setIsPassed(history.getPointGet().compareTo(examPaper.getPassPoint()) >= 0);
        analysis.setScoreInfo(scoreInfo);
        
        // 知识点分析
        List<ExamAnalysis.KnowledgePoint> knowledgePoints = analyzeKnowledgePoints(questionDetails);
        analysis.setKnowledgePoints(knowledgePoints);
        
        log.info("考试记录{}分析完成，正确{}题，错误{}题", recordId, correctCount, wrongCount);
        return analysis;
    }

    /**
     * 分析题目详情
     */
    private List<ExamAnalysis.QuestionDetail> analyzeQuestionDetails(EtUserExamHistory history, List<EtQuestion> questions) {
        List<ExamAnalysis.QuestionDetail> details = new ArrayList<>();
        
        // 获取用户答案
        Map<Long, QuestionAnswerDTO> userAnswers = getUserAnswers(history.getHistId());
        
        // 统计正确和错误题目数
        int correctCount = 0;
        int wrongCount = 0;
        
        for (EtQuestion question : questions) {
            ExamAnalysis.QuestionDetail detail = new ExamAnalysis.QuestionDetail();
            detail.setQuestionId(question.getId());
            detail.setTitle(question.getName());
            detail.setContent(question.getContent());
            detail.setType(getQuestionTypeName(question.getQuestionTypeId()));
            detail.setCategory(question.getKeyword() != null ? question.getKeyword(): "未分类");
            detail.setScore(question.getPoints());
            detail.setDifficulty(question.getDifficulty());
            detail.setCorrectAnswer(question.getReference());
            
            // 获取用户答案并判断是否正确
            QuestionAnswerDTO userAnswer = userAnswers.get(question.getId());
            detail.setUserAnswer(userAnswer);
            
            boolean isCorrect = isAnswerCorrect(userAnswer, question.getReference(), question.getQuestionTypeId());
            detail.setIsCorrect(isCorrect);
            
            // 统计正确和错误题目数
            if (isCorrect) {
                correctCount++;
            } else {
                wrongCount++;
            }
            
            details.add(detail);
        }
        
        log.info("题目详情分析完成，总题数：{}，正确题数：{}，错误题数：{}", questions.size(), correctCount, wrongCount);
        return details;
    }

    /**
     * 分析知识点掌握情况
     */
    private List<ExamAnalysis.KnowledgePoint> analyzeKnowledgePoints(List<ExamAnalysis.QuestionDetail> questionDetails) {
        Map<String, ExamAnalysis.KnowledgePoint> categoryMap = new HashMap<>();
        
        for (ExamAnalysis.QuestionDetail detail : questionDetails) {
            String category = StringUtils.hasText(detail.getCategory()) ? detail.getCategory() : "未分类";
            
            ExamAnalysis.KnowledgePoint kp = categoryMap.computeIfAbsent(category, k -> {
                ExamAnalysis.KnowledgePoint newKp = new ExamAnalysis.KnowledgePoint();
                newKp.setCategory(category);
                newKp.setTotalQuestions(0);
                newKp.setCorrectQuestions(0);
                return newKp;
            });
            
            kp.setTotalQuestions(kp.getTotalQuestions() + 1);
            if (detail.getIsCorrect()) {
                kp.setCorrectQuestions(kp.getCorrectQuestions() + 1);
            }
        }
        
        // 计算正确率
        List<ExamAnalysis.KnowledgePoint> result = new ArrayList<>(categoryMap.values());
        for (ExamAnalysis.KnowledgePoint kp : result) {
            if (kp.getTotalQuestions() > 0) {
                kp.setCorrectRate((double) kp.getCorrectQuestions() / kp.getTotalQuestions() * 100);
            } else {
                kp.setCorrectRate(0.0);
            }
        }
        
        return result;
    }

    /**
     * 获取用户答案
     */
    private Map<Long, QuestionAnswerDTO> getUserAnswers(Long historyId) {
        Map<Long, QuestionAnswerDTO> userAnswers = new HashMap<>();
        
        try {
            // 从考试历史记录中获取答案
            EtUserExamHistory history = unifiedEtUserExamHistoryDao.selectById(historyId);
            if (history != null && StringUtils.hasText(history.getAnswerSheet())) {
                // 解析JSON格式的答案
                ObjectMapper objectMapper = new ObjectMapper();
                userAnswers = objectMapper.readValue(history.getAnswerSheet(), Map.class);
            }
        } catch (Exception e) {
            log.error("解析用户答案失败，historyId: {}", historyId, e);
        }
        
        return userAnswers;
    }

    /**
     * 判断答案是否正确
     */
    private boolean isAnswerCorrect(QuestionAnswerDTO userAnswer, String correctAnswer, Long questionTypeId) {
        if (!StringUtils.hasText(userAnswer.getAnswer()) || !StringUtils.hasText(correctAnswer)) {
            return false;
        }
        
        // 根据题型ID判断答案正确性
        if (QuestionTypeEnum.SINGLE_CHOICE.getCode().equals(questionTypeId) || 
            QuestionTypeEnum.TRUE_FALSE.getCode().equals(questionTypeId)) {
            // 单选题和判断题：精确匹配
            return userAnswer.getAnswer().trim().equals(correctAnswer.trim());
        } else if (QuestionTypeEnum.MULTIPLE_CHOICE.getCode().equals(questionTypeId)) {
            // 多选题：需要处理多个答案选项
            return compareMultipleChoiceAnswers(userAnswer.getAnswer(), correctAnswer);
        } else {
            // 其他题型：精确匹配
            return userAnswer.getAnswer().trim().equals(correctAnswer.trim());
        }
    }
    
    /**
     * 根据题型ID获取题型名称
     */
    private String getQuestionTypeName(Long questionTypeId) {
        if (questionTypeId == null) {
            return "未知题型";
        }
        
        if (QuestionTypeEnum.SINGLE_CHOICE.getCode().equals(questionTypeId)) {
            return "单选题";
        } else if (QuestionTypeEnum.MULTIPLE_CHOICE.getCode().equals(questionTypeId)) {
            return "多选题";
        } else if (QuestionTypeEnum.TRUE_FALSE.getCode().equals(questionTypeId)) {
            return "判断题";
        } else {
            return "其他题型";
        }
    }





    /**
     * 格式化时间戳
     */
    private String formatTimestamp(java.sql.Timestamp timestamp) {
        if (timestamp == null) {
            return "未知时间";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(timestamp);
    }

    @Override
    public List<PendingExamDTO> getPendingExamsForUser(Long userId) {
        // 边界场景处理：参数校验
        if (userId == null || userId <= 0) {
            log.warn("getPendingExamsForUser: 用户ID无效，userId: {}", userId);
            return new ArrayList<>();
        }
        
        try {
            log.info("开始获取用户待考试列表，用户ID: {}", userId);
            
            List<PendingExamDTO> pendingExams = new ArrayList<>();
            log.info("用户待考试列表获取完成，用户ID: {}, 待考试数量: {}", userId, pendingExams.size());
            return pendingExams;
            
        } catch (Exception e) {
            log.error("获取用户待考试列表时发生异常，用户ID: {}, 错误信息: {}", userId, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public Object getQuestionDetail(Long questionId) {
        // 参数校验
        if (questionId == null || questionId <= 0) {
            log.warn("getQuestionDetail: 题目ID无效，questionId: {}", questionId);
            throw new ParameterValidationException("题目ID无效");
        }
        
        try {
            log.info("开始获取题目详情，题目ID: {}", questionId);
            
            EtQuestion question = unifiedEtQuestionDao.selectById(questionId);
            if (question == null) {
                log.warn("题目不存在，题目ID: {}", questionId);
                throw new ResourceNotFoundException("题目不存在");
            }
            
            // 更新题目曝光次数
            EtQuestion updateQuestion = new EtQuestion();
            updateQuestion.setId(questionId);
            updateQuestion.setExposeTimes(question.getExposeTimes() != null ? question.getExposeTimes() + 1 : 1);
            updateQuestion.setRightTimes(question.getRightTimes());
            updateQuestion.setWrongTimes(question.getWrongTimes());
            unifiedEtQuestionDao.updateById(updateQuestion);
            
            log.info("题目详情获取完成，题目ID: {}", questionId);
            return question;
            
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取题目详情时发生异常，题目ID: {}, 错误信息: {}", questionId, e.getMessage(), e);
            throw new RuntimeException("获取题目详情失败", e);
        }
    }
}