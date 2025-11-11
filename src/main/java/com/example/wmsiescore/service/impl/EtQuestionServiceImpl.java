package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.mapper.EtExamPaperMapper;
import com.example.wmsiescore.mapper.EtExamPaperQuestionMapper;
import com.example.wmsiescore.mapper.EtQuestionMapper;
import com.example.wmsiescore.model.EtExamPaper;
import com.example.wmsiescore.model.EtExamPaperQuestion;
import com.example.wmsiescore.model.EtQuestion;
import com.example.wmsiescore.service.EtQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class EtQuestionServiceImpl implements EtQuestionService {
    @Autowired
    private EtQuestionMapper etQuestionMapper;
    
    @Autowired
    private EtExamPaperQuestionMapper etExamPaperQuestionMapper;
    
    @Autowired
    private EtExamPaperMapper etExamPaperMapper;

    @Override
    @Transactional
    public Long addQuestion(EtQuestion question) {
        question.setCreateTime(new Timestamp(System.currentTimeMillis()));
        question.setLastModify(new Timestamp(System.currentTimeMillis()));
        if (question.getIsVisible() == null) {
            question.setIsVisible(true);
        }
        if (question.getExposeTimes() == null) {
            question.setExposeTimes(0);
        }
        if (question.getRightTimes() == null) {
            question.setRightTimes(0);
        }
        if (question.getWrongTimes() == null) {
            question.setWrongTimes(0);
        }
        etQuestionMapper.insertQuestion(question);
        return question.getId();
    }

    @Override
    @Transactional
    public Boolean updateQuestion(EtQuestion question) {
        question.setLastModify(new Timestamp(System.currentTimeMillis()));
        return etQuestionMapper.updateQuestion(question) > 0;
    }

    @Override
    @Transactional
    public Boolean deleteQuestion(Long id) {
        // 先删除试卷中的关联
        List<EtExamPaperQuestion> examPaperQuestions = etExamPaperQuestionMapper.listExamPapersByQuestion(id);
        for (EtExamPaperQuestion epq : examPaperQuestions) {
            etExamPaperQuestionMapper.deleteExamPaperQuestion(epq.getId());
        }
        // 再删除试题
        return etQuestionMapper.deleteQuestion(id) > 0;
    }

    @Override
    public EtQuestion getQuestionById(Long id) {
        return etQuestionMapper.getQuestionById(id);
    }

    @Override
    public List<EtQuestion> getQuestionsByGroupId(Long groupId) {
        return etQuestionMapper.listQuestionsByGroupId(groupId);
    }

    @Override
    public List<EtQuestion> getQuestionsByType(Long questionTypeId) {
        return etQuestionMapper.listQuestionsByType(questionTypeId);
    }

    @Override
    public List<EtQuestion> getAllQuestions() {
        return etQuestionMapper.listAllQuestions();
    }

    @Override
    public List<EtQuestion> getVisibleQuestions() {
        return etQuestionMapper.listVisibleQuestions();
    }

    @Override
    public List<EtQuestion> searchQuestions(String keyword) {
        return etQuestionMapper.searchQuestions(keyword);
    }

    @Override
    public List<EtQuestion> getQuestionsByDifficulty(String difficulty) {
        return etQuestionMapper.listQuestionsByDifficulty(difficulty);
    }

    @Override
    public List<EtQuestion> getQuestionsByCreator(String creator) {
        return etQuestionMapper.listQuestionsByCreator(creator);
    }

    @Override
    @Transactional
    public Boolean updateQuestionStatistics(Long id, Integer exposeTimes, Integer rightTimes, Integer wrongTimes) {
        return etQuestionMapper.updateQuestionStatistics(id, exposeTimes, rightTimes, wrongTimes) > 0;
    }

    @Override
    @Transactional
    public Boolean setQuestionVisibility(Long id, Boolean isVisible) {
        return etQuestionMapper.updateQuestionVisibility(id, isVisible) > 0;
    }

    @Override
    @Transactional
    public Boolean addQuestionToExamPaper(Long examPaperId, Long questionId, Integer score) {
        // 检查试题是否已在试卷中
        EtExamPaperQuestion existing = etExamPaperQuestionMapper.getExamPaperQuestion(examPaperId, questionId);
        if (existing != null) {
            return false; // 试题已存在
        }
        
        EtExamPaperQuestion examPaperQuestion = new EtExamPaperQuestion();
        examPaperQuestion.setExamPaperId(examPaperId);
        examPaperQuestion.setQuestionId(questionId);
        examPaperQuestion.setScore(score);
        examPaperQuestion.setStatus("active");
        
        // 获取最大排序号
        Integer maxSortOrder = etExamPaperQuestionMapper.getMaxSortOrder(examPaperId);
        examPaperQuestion.setSortOrder(maxSortOrder != null ? maxSortOrder + 1 : 1);
        examPaperQuestion.setCreateTime(new Timestamp(System.currentTimeMillis()));
        
        etExamPaperQuestionMapper.insertExamPaperQuestion(examPaperQuestion);
        return true;
    }

    @Override
    @Transactional
    public Boolean removeQuestionFromExamPaper(Long examPaperId, Long questionId) {
        return etExamPaperQuestionMapper.deleteExamPaperQuestionByPaperAndQuestion(examPaperId, questionId) > 0;
    }

    @Override
    public List<EtExamPaperQuestion> getQuestionsByExamPaper(Long examPaperId) {
        return etExamPaperQuestionMapper.listQuestionsByExamPaper(examPaperId);
    }

    @Override
    @Transactional
    public Boolean batchAddQuestionsToExamPaper(Long examPaperId, List<Long> questionIds) {
        for (Long questionId : questionIds) {
            EtQuestion question = etQuestionMapper.getQuestionById(questionId);
            if (question != null) {
                addQuestionToExamPaper(examPaperId, questionId, question.getPoints().intValue());
            }
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean batchRemoveQuestionsFromExamPaper(Long examPaperId, List<Long> questionIds) {
        for (Long questionId : questionIds) {
            removeQuestionFromExamPaper(examPaperId, questionId);
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean adjustQuestionOrder(Long examPaperQuestionId, Integer sortOrder) {
        return etExamPaperQuestionMapper.updateQuestionSortOrder(examPaperQuestionId, sortOrder) > 0;
    }

    @Override
    @Transactional
    public Boolean setExamPaperPermission(Long examPaperId, String targetGroupId, String targetUserId) {
        EtExamPaper examPaper = etExamPaperMapper.getExamPaperById(examPaperId);
        if (examPaper == null) {
            return false;
        }
//        examPaper.setTargetGroupId(targetGroupId);
//        examPaper.setTargetUserId(targetUserId);
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return etExamPaperMapper.updateExamPaper(examPaper) > 0;
    }

    
}