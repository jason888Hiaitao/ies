package com.example.wmsiescore.service;

import com.example.wmsiescore.model.EtExamPaper;
import com.example.wmsiescore.model.PendingExamDTO;

import java.util.List;

public interface EtExamPaperService {
    /**
     * 创建考试试卷
     */
    Long createExamPaper(EtExamPaper examPaper);

    /**
     * 更新考试试卷
     */
    Boolean updateExamPaper(EtExamPaper examPaper);

    /**
     * 删除考试试卷
     */
    Boolean deleteExamPaper(Long id);

    /**
     * 获取考试试卷详情
     */
    EtExamPaper getExamPaperById(Long id);

    /**
     * 推送考试给单个员工
     */
    Boolean pushExamToUser(Long examPaperId, String userId);

    /**
     * 推送考试给分组
     */
    Boolean pushExamToGroup(Long examPaperId, String groupId);

    /**
     * 设置考试时间范围
     */
    Boolean setExamTimeRange(Long examPaperId, java.sql.Timestamp startTime, java.sql.Timestamp endTime);

    /**
     * 设置考试时长
     */
    Boolean setExamDuration(Long examPaperId, Integer duration);

    /**
     * 设置考试次数
     */
    Boolean setMaxAttempts(Long examPaperId, Integer maxAttempts);

    /**
     * 设置总分数
     */
    Boolean setTotalScore(Long examPaperId, Integer totalScore);

    /**
     * 设置及格分数
     */
    Boolean setPassScore(Long examPaperId, Integer passScore);

    /**
     * 发布考试
     */
    Boolean publishExam(Long examPaperId);

    /**
     * 获取用户的考试列表
     */
    List<EtExamPaper> getExamPapersForUser(String userId);

    /**
     * 获取分组的考试列表
     */
    List<EtExamPaper> getExamPapersForGroup(String groupId);

    /**
     * 获取所有考试试卷列表
     */
    List<EtExamPaper> listAllExamPapers();

    /**
     * 发布考试试卷
     */
    Boolean publishExamPaper(Long examPaperId);

    /**
     * 取消发布考试试卷
     */
    Boolean unpublishExamPaper(Long examPaperId);

    /**
     * 获取用户已完成的试卷ID列表（提交时间不为空）
     */
    List<Long> getCompletedExamPaperIds(Long userId);

    /**
     * 获取用户可见的试卷列表（is_visible=1，paper_status=1，且部门或群组匹配）
     */
    List<EtExamPaper> getVisibleExamPapersForUser(Long userId);

    /**
     * 获取用户待参加的考试列表
     */
    List<PendingExamDTO> getPendingExamsForUser(Long userId);
}