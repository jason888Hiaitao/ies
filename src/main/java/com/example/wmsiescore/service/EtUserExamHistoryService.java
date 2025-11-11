package com.example.wmsiescore.service;

import com.example.wmsiescore.model.EtUserExamHistory;

import java.util.List;

public interface EtUserExamHistoryService {
    List<EtUserExamHistory> findAll();
    EtUserExamHistory findById(Long histId);
    int save(EtUserExamHistory etUserExamHistory);
    int update(EtUserExamHistory etUserExamHistory);
    int delete(Long histId);

    // 获取用户考试历史
    List<EtUserExamHistory> getUserExamHistory(Long userId);

    // 获取用户已完成的考试历史（提交时间不为空）
    List<EtUserExamHistory> getUserExamHistoryWithSubmitTime(Long userId);

    // 获取用户对指定试卷的考试次数
    int countUserAttempts(Long userId, Long examPaperId);

    // 获取用户已完成的试卷ID列表
    List<Long> getCompletedExamPaperIds(Long userId);
}