package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.dao.UnifiedEtExamPaperDao;
import com.example.wmsiescore.dto.ExamPaperQueryDTO;
import com.example.wmsiescore.dto.ExamPaperSaveDTO;
import com.example.wmsiescore.dto.PageResult;
import com.example.wmsiescore.dto.query.EtExamPaperQuery;
import com.example.wmsiescore.model.EtExamPaper;
import com.example.wmsiescore.service.AdminExamPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * 管理员试卷服务实现类
 */
@Service
public class AdminExamPaperServiceImpl implements AdminExamPaperService {
    
    @Autowired
    private UnifiedEtExamPaperDao unifiedEtExamPaperDao;
    
    @Override
    public PageResult getExamPaperList(ExamPaperQueryDTO queryDTO) {
        int offset = (queryDTO.getPageNum() - 1) * queryDTO.getPageSize();
        
        // 查询总数
        Long total = unifiedEtExamPaperDao.countExamPaperList(
            queryDTO.getName(),
            queryDTO.getStatus(),
            queryDTO.getValiddpt(),
            queryDTO.getValidsource(),
            queryDTO.getPaperType()
        );
        
        // 查询列表数据
        List<EtExamPaper> examPaperList = unifiedEtExamPaperDao.selectExamPaperList(
            queryDTO.getName(),
            queryDTO.getStatus(),
            queryDTO.getValiddpt(),
            queryDTO.getValidsource(),
            queryDTO.getPaperType(),
            offset,
            queryDTO.getPageSize()
        );

        return new PageResult(queryDTO.getPageNum(), queryDTO.getPageSize(), total, examPaperList);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveExamPaper(ExamPaperSaveDTO examPaperSaveDTO) {
        if (examPaperSaveDTO == null || examPaperSaveDTO.getOperation() == null) {
            return false;
        }
        
        switch (examPaperSaveDTO.getOperation().toLowerCase()) {
            case "create":
                return createExamPaper(examPaperSaveDTO);
            case "update":
                return updateExamPaper(examPaperSaveDTO);
            case "delete":
                return deleteExamPaper(examPaperSaveDTO.getId());
            case "batch-delete":
                return deleteExamPapers(examPaperSaveDTO.getIds());
            default:
                return false;
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteExamPapers(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }

        try {
            for (Long id : ids) {
                unifiedEtExamPaperDao.deleteById(id);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 创建试卷
     */
    private Boolean createExamPaper(ExamPaperSaveDTO examPaperSaveDTO) {
        try {
            EtExamPaper examPaper = new EtExamPaper();
            examPaper.setName(examPaperSaveDTO.getName());
            examPaper.setContent(examPaperSaveDTO.getContent());
            examPaper.setDuration(examPaperSaveDTO.getDuration());
            examPaper.setPassPoint(examPaperSaveDTO.getPassPoint());
            examPaper.setTotalPoint(examPaperSaveDTO.getTotalPoint());
            examPaper.setStatus(examPaperSaveDTO.getStatus());
            examPaper.setSummary(examPaperSaveDTO.getSummary());
            examPaper.setIsVisible(examPaperSaveDTO.getIsVisible());
            examPaper.setAnswerSheet(examPaperSaveDTO.getAnswerSheet());
            examPaper.setGroupId(examPaperSaveDTO.getGroupId());
            examPaper.setIsSubjective(examPaperSaveDTO.getIsSubjective());
            examPaper.setCreator(examPaperSaveDTO.getCreator());
            examPaper.setPaperType(examPaperSaveDTO.getPaperType());
            examPaper.setFieldId(examPaperSaveDTO.getFieldId());
            examPaper.setValidsource(examPaperSaveDTO.getValidsource());
            examPaper.setValiddpt(examPaperSaveDTO.getValiddpt());
            examPaper.setExamCount(examPaperSaveDTO.getExamCount());
            examPaper.setAnswerHide(examPaperSaveDTO.getAnswerHide());
            
            Timestamp now = new Timestamp(System.currentTimeMillis());
            examPaper.setCreateTime(now);
            examPaper.setUpdateTime(now);
            
            return unifiedEtExamPaperDao.insert(examPaper) > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 更新试卷
     */
    private Boolean updateExamPaper(ExamPaperSaveDTO examPaperSaveDTO) {
        try {
            EtExamPaper examPaper = new EtExamPaper();
            examPaper.setId(examPaperSaveDTO.getId());
            examPaper.setName(examPaperSaveDTO.getName());
            examPaper.setContent(examPaperSaveDTO.getContent());
            examPaper.setDuration(examPaperSaveDTO.getDuration());
            examPaper.setPassPoint(examPaperSaveDTO.getPassPoint());
            examPaper.setTotalPoint(examPaperSaveDTO.getTotalPoint());
            examPaper.setStatus(examPaperSaveDTO.getStatus());
            examPaper.setSummary(examPaperSaveDTO.getSummary());
            examPaper.setIsVisible(examPaperSaveDTO.getIsVisible());
            examPaper.setAnswerSheet(examPaperSaveDTO.getAnswerSheet());
            examPaper.setGroupId(examPaperSaveDTO.getGroupId());
            examPaper.setIsSubjective(examPaperSaveDTO.getIsSubjective());
            examPaper.setCreator(examPaperSaveDTO.getCreator());
            examPaper.setPaperType(examPaperSaveDTO.getPaperType());
            examPaper.setFieldId(examPaperSaveDTO.getFieldId());
            examPaper.setValidsource(examPaperSaveDTO.getValidsource());
            examPaper.setValiddpt(examPaperSaveDTO.getValiddpt());
            examPaper.setExamCount(examPaperSaveDTO.getExamCount());
            examPaper.setAnswerHide(examPaperSaveDTO.getAnswerHide());
            examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            
            return unifiedEtExamPaperDao.updateById(examPaper) > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 删除试卷
     */
    private Boolean deleteExamPaper(Long id) {
        try {
            return unifiedEtExamPaperDao.deleteById(id) > 0;
        } catch (Exception e) {
            return false;
        }
    }
}