package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.dao.UnifiedEtExamPaperDao;
import com.example.wmsiescore.dao.UnifiedEtExamPaperQuestionDao;
import com.example.wmsiescore.dao.UnifiedEtQuestionDao;
import com.example.wmsiescore.model.EtExamPaper;
import com.example.wmsiescore.model.EtExamPaperQuestion;
import com.example.wmsiescore.model.EtQuestion;
import com.example.wmsiescore.service.EtQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EtQuestionServiceImpl implements EtQuestionService {
    @Autowired
    private UnifiedEtQuestionDao unifiedEtQuestionDao;

    @Autowired
    private UnifiedEtExamPaperQuestionDao unifiedEtExamPaperQuestionDao;

    @Autowired
    private UnifiedEtExamPaperDao unifiedEtExamPaperDao;

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
        unifiedEtQuestionDao.insertSelective(question);
        return question.getId();
    }

    @Override
    @Transactional
    public Boolean updateQuestion(EtQuestion question) {
        question.setLastModify(new Timestamp(System.currentTimeMillis()));
        return unifiedEtQuestionDao.updateById(question) > 0;
    }

    @Override
    @Transactional
    public Boolean deleteQuestion(Long id) {
        List<EtExamPaperQuestion> examPaperQuestions = unifiedEtExamPaperQuestionDao.selectByQuestionId(id);
        for (EtExamPaperQuestion epq : examPaperQuestions) {
            unifiedEtExamPaperQuestionDao.deleteById(epq.getId());
        }
        return unifiedEtQuestionDao.deleteById(id) > 0;
    }

    @Override
    public EtQuestion getQuestionById(Long id) {
        return unifiedEtQuestionDao.selectById(id);
    }

    @Override
    public List<EtQuestion> getQuestionsByGroupId(Long groupId) {
        return unifiedEtQuestionDao.selectByGroupId(groupId);
    }

    @Override
    public List<EtQuestion> getQuestionsByType(Long questionTypeId) {
        return unifiedEtQuestionDao.selectByQuestionTypeId(questionTypeId);
    }

    @Override
    public List<EtQuestion> getAllQuestions() {
        return unifiedEtQuestionDao.selectAll();
    }

    @Override
    public List<EtQuestion> getVisibleQuestions() {
        return unifiedEtQuestionDao.selectByIsVisible(true);
    }

    @Override
    public List<EtQuestion> searchQuestions(String keyword) {
        return unifiedEtQuestionDao.selectByNameLike(keyword);
    }

    @Override
    public List<EtQuestion> getQuestionsByDifficulty(String difficulty) {
        return unifiedEtQuestionDao.selectByDifficulty(difficulty);
    }

    @Override
    public List<EtQuestion> getQuestionsByCreator(String creator) {
        return unifiedEtQuestionDao.selectByCreator(creator);
    }

    @Override
    @Transactional
    public Boolean updateQuestionStatistics(Long id, Integer exposeTimes, Integer rightTimes, Integer wrongTimes) {
        EtQuestion question = new EtQuestion();
        question.setId(id);
        question.setExposeTimes(exposeTimes);
        question.setRightTimes(rightTimes);
        question.setWrongTimes(wrongTimes);
        question.setLastModify(new Timestamp(System.currentTimeMillis()));
        return unifiedEtQuestionDao.updateById(question) > 0;
    }

    @Override
    @Transactional
    public Boolean setQuestionVisibility(Long id, Boolean isVisible) {
        EtQuestion question = new EtQuestion();
        question.setId(id);
        question.setIsVisible(isVisible);
        question.setLastModify(new Timestamp(System.currentTimeMillis()));
        return unifiedEtQuestionDao.updateById(question) > 0;
    }

    @Override
    @Transactional
    public Boolean addQuestionToExamPaper(Long examPaperId, Long questionId, Integer score) {
        EtExamPaperQuestion existing = unifiedEtExamPaperQuestionDao.selectByExamPaperIdAndQuestionId(examPaperId, questionId);
        if (existing != null) {
            return false;
        }

        EtExamPaperQuestion examPaperQuestion = new EtExamPaperQuestion();
        examPaperQuestion.setExamPaperId(examPaperId);
        examPaperQuestion.setQuestionId(questionId);
        examPaperQuestion.setScore(score);
        examPaperQuestion.setStatus("active");

        List<EtExamPaperQuestion> existingQuestions = unifiedEtExamPaperQuestionDao.selectByExamPaperId(examPaperId);
        Integer maxSortOrder = existingQuestions.stream()
            .map(EtExamPaperQuestion::getSortOrder)
            .filter(Objects::nonNull)
            .max(Integer::compareTo)
            .orElse(0);
        examPaperQuestion.setSortOrder(maxSortOrder + 1);
        examPaperQuestion.setCreateTime(new Timestamp(System.currentTimeMillis()));
        examPaperQuestion.setUpdateTime(examPaperQuestion.getCreateTime());

        unifiedEtExamPaperQuestionDao.insertSelective(examPaperQuestion);
        return true;
    }

    @Override
    @Transactional
    public Boolean removeQuestionFromExamPaper(Long examPaperId, Long questionId) {
        EtExamPaperQuestion relation = unifiedEtExamPaperQuestionDao.selectByExamPaperIdAndQuestionId(examPaperId, questionId);
        if (relation == null) {
            return false;
        }
        return unifiedEtExamPaperQuestionDao.deleteById(relation.getId()) > 0;
    }

    @Override
    public List<EtExamPaperQuestion> getQuestionsByExamPaper(Long examPaperId) {
        return unifiedEtExamPaperQuestionDao.selectByExamPaperId(examPaperId);
    }

    @Override
    @Transactional
    public Boolean batchAddQuestionsToExamPaper(Long examPaperId, List<Long> questionIds) {
        for (Long questionId : questionIds) {
            EtQuestion question = unifiedEtQuestionDao.selectById(questionId);
            if (question != null && question.getPoints() != null) {
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
        EtExamPaperQuestion update = new EtExamPaperQuestion();
        update.setId(examPaperQuestionId);
        update.setSortOrder(sortOrder);
        update.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return unifiedEtExamPaperQuestionDao.updateById(update) > 0;
    }

    @Override
    @Transactional
    public Boolean setExamPaperPermission(Long examPaperId, String targetGroupId, String targetUserId) {
        EtExamPaper examPaper = unifiedEtExamPaperDao.selectById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return unifiedEtExamPaperDao.updateById(examPaper) > 0;
    }

}
