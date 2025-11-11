package com.example.wmsiescore.service;

import com.example.wmsiescore.model.EtUserExamHistory;
import com.example.wmsiescore.model.ExamRanking;
import com.example.wmsiescore.model.ExamStatistics;

import java.util.List;

public interface EtExamAnalysisService {
    /**
     * 获取考试排名
     */
    List<ExamRanking> getExamRankings(Long examPaperId);

    /**
     * 获取考试统计信息
     */
    ExamStatistics getExamStatistics(Long examPaperId);

    /**
     * 获取考试总人数
     */
    Integer getTotalParticipants(Long examPaperId);

    /**
     * 获取实际参考人数
     */
    Integer getActualParticipants(Long examPaperId);

    /**
     * 获取参考名单
     */
    List<EtUserExamHistory> getParticipantList(Long examPaperId);

    /**
     * 获取缺考名单
     */
    List<EtUserExamHistory> getAbsentList(Long examPaperId);

    /**
     * 获取平均分
     */
    java.math.BigDecimal getAverageScore(Long examPaperId);

    /**
     * 获取员工考试时长
     */
    List<EtUserExamHistory> getUserExamDurations(Long examPaperId);

    /**
     * 导出考试结果
     */
    List<EtUserExamHistory> exportExamResults(Long examPaperId);

    /**
     * 记录用户考试历史
     */
    Long recordUserExamHistory(EtUserExamHistory history);

    /**
     * 更新用户考试历史
     */
    Boolean updateUserExamHistory(EtUserExamHistory history);

    /**
     * 获取用户考试历史
     */
    EtUserExamHistory getUserExamHistory(Long userId, Long examPaperId);
}