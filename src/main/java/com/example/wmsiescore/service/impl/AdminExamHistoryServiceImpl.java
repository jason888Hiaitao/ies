package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.dao.UnifiedEtUserExamHistoryDao;
import com.example.wmsiescore.dto.ExamHistoryDetailDTO;
import com.example.wmsiescore.dto.ExamHistoryQueryDTO;
import com.example.wmsiescore.dto.PageResult;
import com.example.wmsiescore.service.AdminExamHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 管理员考试历史服务实现类
 */
@Slf4j
@Service
public class AdminExamHistoryServiceImpl implements AdminExamHistoryService {
    
    @Autowired
    private UnifiedEtUserExamHistoryDao unifiedEtUserExamHistoryDao;
    
    @Override
    public PageResult getExamHistoryList(ExamHistoryQueryDTO queryDTO) {
        try {
            log.info("分页查询考试历史列表，试卷名：{}，部门：{}，团队：{}", 
                queryDTO.getExamPaperName(), queryDTO.getDepartment(), queryDTO.getGroupname());
            
            // 计算偏移量
            int offset = (queryDTO.getPageNum() - 1) * queryDTO.getPageSize();
            
            // 查询总数
            Long total = unifiedEtUserExamHistoryDao.countExamHistoryList(
                queryDTO.getExamPaperName(),
                queryDTO.getDepartment(),
                queryDTO.getGroupname()
            );
            
            // 查询列表数据
            List<ExamHistoryDetailDTO> examHistoryList = unifiedEtUserExamHistoryDao.selectExamHistoryList(
                queryDTO.getExamPaperName(),
                queryDTO.getDepartment(),
                queryDTO.getGroupname(),
                offset,
                queryDTO.getPageSize()
            );
            
            log.info("查询到{}条考试历史记录", examHistoryList.size());
            
            return new PageResult(queryDTO.getPageNum(), queryDTO.getPageSize(), total, examHistoryList);
        } catch (Exception e) {
            log.error("分页查询考试历史列表失败", e);
            throw new RuntimeException("查询考试历史列表失败", e);
        }
    }
}