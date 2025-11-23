package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.dao.UnifiedEtUserExamHistoryDao;
import com.example.wmsiescore.model.EtUserExamHistory;
import com.example.wmsiescore.service.EtUserExamHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class EtUserExamHistoryServiceImpl implements EtUserExamHistoryService {

    @Autowired
    private UnifiedEtUserExamHistoryDao unifiedEtUserExamHistoryDao;

    @Override
    public List<EtUserExamHistory> findAll() {
        try {
            log.info("获取所有用户考试历史记录");
            List<EtUserExamHistory> histories = unifiedEtUserExamHistoryDao.selectAll();
            log.info("获取到{}条考试历史记录", histories.size());
            return histories;
        } catch (Exception e) {
            log.error("获取所有用户考试历史记录失败", e);
            throw new RuntimeException("获取考试历史记录失败", e);
        }
    }

    @Override
    public EtUserExamHistory findById(Long histId) {
        try {
            log.info("根据ID获取考试历史记录，ID: {}", histId);
            EtUserExamHistory history = unifiedEtUserExamHistoryDao.selectById(histId);
            if (history == null) {
                log.warn("未找到ID为{}的考试历史记录", histId);
            }
            return history;
        } catch (Exception e) {
            log.error("根据ID获取考试历史记录失败，ID: {}", histId, e);
            throw new RuntimeException("获取考试历史记录失败", e);
        }
    }

    @Override
    public int save(EtUserExamHistory etUserExamHistory) {
        try {
            log.info("保存考试历史记录，用户ID: {}, 试卷ID: {}", 
                etUserExamHistory.getUserId(), etUserExamHistory.getExamPaperId());
            int result = unifiedEtUserExamHistoryDao.insertSelective(etUserExamHistory);
            log.info("保存考试历史记录成功，影响行数: {}", result);
            return result;
        } catch (Exception e) {
            log.error("保存考试历史记录失败", e);
            throw new RuntimeException("保存考试历史记录失败", e);
        }
    }

    @Override
    public int update(EtUserExamHistory etUserExamHistory) {
        try {
            log.info("更新考试历史记录，ID: {}", etUserExamHistory.getHistId());
            int result = unifiedEtUserExamHistoryDao.updateById(etUserExamHistory);
            log.info("更新考试历史记录成功，影响行数: {}", result);
            return result;
        } catch (Exception e) {
            log.error("更新考试历史记录失败", e);
            throw new RuntimeException("更新考试历史记录失败", e);
        }
    }

    @Override
    public int delete(Long histId) {
        try {
            log.info("删除考试历史记录，ID: {}", histId);
            int result = unifiedEtUserExamHistoryDao.deleteById(histId);
            log.info("删除考试历史记录成功，影响行数: {}", result);
            return result;
        } catch (Exception e) {
            log.error("删除考试历史记录失败", e);
            throw new RuntimeException("删除考试历史记录失败", e);
        }
    }

    @Override
    public List<EtUserExamHistory> getUserExamHistory(Long userId) {
        try {
            log.info("获取用户考试历史记录，用户ID: {}", userId);
            List<EtUserExamHistory> histories = unifiedEtUserExamHistoryDao.selectByUserId(userId);
            if (histories == null) {
                histories = new ArrayList<>();
            }
            log.info("获取到用户{}的{}条考试历史记录", userId, histories.size());
            return histories;
        } catch (Exception e) {
            log.error("获取用户考试历史记录失败，用户ID: {}", userId, e);
            throw new RuntimeException("获取用户考试历史记录失败", e);
        }
    }

    @Override
    public List<EtUserExamHistory> getUserExamHistoryWithSubmitTime(Long userId) {
        try {
            log.info("获取用户已完成的考试历史记录，用户ID: {}", userId);
            List<EtUserExamHistory> histories = unifiedEtUserExamHistoryDao.getUserExamHistoryWithSubmitTime(userId);
            if (histories == null) {
                histories = new ArrayList<>();
            }
            log.info("获取到用户{}的{}条已完成考试历史记录", userId, histories.size());
            return histories;
        } catch (Exception e) {
            log.error("获取用户已完成的考试历史记录失败，用户ID: {}", userId, e);
            throw new RuntimeException("获取用户已完成考试历史记录失败", e);
        }
    }

    @Override
    public int countUserAttempts(Long userId, Long examPaperId) {
        try {
            log.info("统计用户对指定试卷的考试次数，用户ID: {}, 试卷ID: {}", userId, examPaperId);
            List<EtUserExamHistory> attemptsList = unifiedEtUserExamHistoryDao.selectByUserIdAndExamPaperId(userId, examPaperId);
            int attempts = attemptsList == null ? 0 : attemptsList.size();
            log.info("用户{}对试卷{}的考试次数: {}", userId, examPaperId, attempts);
            return attempts;
        } catch (Exception e) {
            log.error("统计用户考试次数失败，用户ID: {}, 试卷ID: {}", userId, examPaperId, e);
            throw new RuntimeException("统计用户考试次数失败", e);
        }
    }

    @Override
    public List<Long> getCompletedExamPaperIds(Long userId) {
        try {
            log.info("获取用户已完成的试卷ID列表，用户ID: {}", userId);
            List<Long> examPaperIds = unifiedEtUserExamHistoryDao.getCompletedExamPaperIds(userId);
            if (examPaperIds == null) {
                examPaperIds = new ArrayList<>();
            }
            log.info("获取到用户{}已完成的{}个试卷", userId, examPaperIds.size());
            return examPaperIds;
        } catch (Exception e) {
            log.error("获取用户已完成试卷ID列表失败，用户ID: {}", userId, e);
            throw new RuntimeException("获取用户已完成试卷ID列表失败", e);
        }
    }
}