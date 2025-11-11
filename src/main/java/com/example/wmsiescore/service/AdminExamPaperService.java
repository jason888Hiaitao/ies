package com.example.wmsiescore.service;

import com.example.wmsiescore.dto.ExamPaperQueryDTO;
import com.example.wmsiescore.dto.ExamPaperSaveDTO;
import com.example.wmsiescore.dto.PageResult;

import java.util.List;

/**
 * 管理员试卷服务接口
 */
public interface AdminExamPaperService {
    
    /**
     * 分页查询试卷列表
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    PageResult getExamPaperList(ExamPaperQueryDTO queryDTO);
    
    /**
     * 保存试卷（创建/更新/删除）
     * @param examPaperSaveDTO 试卷保存信息
     * @return 操作结果
     */
    Boolean saveExamPaper(ExamPaperSaveDTO examPaperSaveDTO);
    
    /**
     * 批量删除试卷
     * @param ids 试卷ID列表
     * @return 操作结果
     */
    Boolean deleteExamPapers(List<Long> ids);
}