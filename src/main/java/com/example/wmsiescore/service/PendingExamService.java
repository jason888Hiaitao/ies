package com.example.wmsiescore.service;

import com.example.wmsiescore.model.EtExamPaper;
import com.example.wmsiescore.model.PendingExamDTO;

import java.util.List;

/**
 * 待考试列表服务接口
 * 重构后的业务逻辑：
 * 1. 先查询用户下的所有考试记录（提交时间不为空的）
 * 2. 然后查询用户可见试卷（is_visible=1，试卷状态=1，且试卷与用户是同一个部门validdpt或者该试卷与用户是同一个群组validsource）
 * 3. 用户即可见，又没考过的试卷就是待考试列表
 */
public interface PendingExamService {

    /**
     * 获取用户已完成的试卷ID列表（提交时间不为空）
     * @param userId 用户ID
     * @return 已完成的试卷ID列表
     */
    List<Long> getCompletedExamPaperIds(Long userId);

    /**
     * 获取用户可见的试卷列表（is_visible=1，paper_status=1，且部门或群组匹配）
     * @param userId 用户ID
     * @return 用户可见的试卷列表
     */
    List<EtExamPaper> getVisibleExamPapersForUser(Long userId);

    /**
     * 获取用户待参加的考试列表（可见且未考过的试卷）
     * @param userId 用户ID
     * @return 待参加的考试列表
     */
    List<PendingExamDTO> getPendingExamsForUser(Long userId);
}