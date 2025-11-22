package com.example.wmsiescore.service;

import com.example.wmsiescore.dto.ExamHistoryQueryDTO;
import com.example.wmsiescore.dto.PageResult;

/**
 * 管理员考试历史服务接口
 */
public interface AdminExamHistoryService {
    
    /**
     * 分页查询考试历史列表
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    PageResult getExamHistoryList(ExamHistoryQueryDTO queryDTO);
}