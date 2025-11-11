package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.exception.BusinessException;
import com.example.wmsiescore.mapper.EtExamPaperMapper;
import com.example.wmsiescore.mapper.EtUserExamHistoryMapper;
import com.example.wmsiescore.mapper.EtUserGroupMapper;
import com.example.wmsiescore.mapper.UserMapper;
import com.example.wmsiescore.model.*;
import com.example.wmsiescore.service.EtUserExamHistoryService;
import com.example.wmsiescore.service.PendingExamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 待考试列表服务实现类
 * 重构后的业务逻辑：
 * 1. 先查询用户下的所有考试记录（提交时间不为空的）
 * 2. 然后查询用户可见试卷（is_visible=1，试卷状态=1，且试卷与用户是同一个部门validdpt或者该试卷与用户是同一个群组validsource）
 * 3. 用户即可见，又没考过的试卷就是待考试列表
 */
@Slf4j
@Service
public class PendingExamServiceImpl implements PendingExamService {

    @Autowired
    private EtExamPaperMapper etExamPaperMapper;
    @Autowired
    private EtUserExamHistoryMapper etUserExamHistoryMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EtUserGroupMapper etUserGroupMapper;

    @Override
    public List<Long> getCompletedExamPaperIds(Long userId) {
        return etExamPaperMapper.getCompletedExamPaperIds(userId);
    }

    @Override
    public List<EtExamPaper> getVisibleExamPapersForUser(Long userId) {
        return etExamPaperMapper.getVisibleExamPapersForUser(userId);
    }

    /**
     * 获取指定用户的待考试列表
     * <p>
     * 该方法会查询用户可见的所有试卷（基于部门和群组），并过滤掉用户已完成的试卷
     *
     * @param userId 用户ID，必须大于0
     * @return 用户待考试的试卷列表，包含试卷基本信息
     * @throws BusinessException 当用户ID无效或用户不存在时抛出
     */
    @Override
    public List<PendingExamDTO> getPendingExamsForUser(Long userId) {
        // 边界场景处理：参数校验
        if (userId == null || userId <= 0) {
            throw new BusinessException("用户ID不能为空或小于等于0");
        }
        
        try {
            // 1. 查询用户信息并校验用户是否存在
            EtUser user = userMapper.getUser(userId);
            if (user == null) {
                throw new BusinessException("用户不存在，用户ID: " + userId);
            }
            
            // 2. 查询用户已完成的试卷ID列表
            List<Long> completedExamPaperIds = etExamPaperMapper.getCompletedExamPaperIds(userId);
            if (completedExamPaperIds == null) {
                completedExamPaperIds = new ArrayList<>();
            }
            
            // 3. 查询用户可见的试卷（基于部门）
            List<EtExamPaper> examPapersByDepartment = new ArrayList<>();
            String department = user.getDepartment();
            if (department != null && !department.trim().isEmpty()) {
                examPapersByDepartment = etExamPaperMapper.getExamPapersByUserAndDepartment(department);
                if (examPapersByDepartment == null) {
                    examPapersByDepartment = new ArrayList<>();
                }
            }
            
            // 4. 查询用户关联的群组
            List<EtExamPaper> examPapersByGroups = new ArrayList<>();
            List<EtUserGroup> userGroups = etUserGroupMapper.getUserGroupsByUsername(user.getUsername());
            if (userGroups != null && !userGroups.isEmpty()) {
                List<Long> fieldIds = userGroups.stream()
                        .map(EtUserGroup::getFieldId)
                        .filter(fieldId -> fieldId != null && fieldId > 0)
                        .collect(Collectors.toList());
                
                if (!fieldIds.isEmpty()) {
                    examPapersByGroups = etExamPaperMapper.getExamPapersByFieldIds(fieldIds);
                    if (examPapersByGroups == null) {
                        examPapersByGroups = new ArrayList<>();
                    }
                }
            }
            
            // 5. 合并所有可见试卷并去重
            Set<Long> paperIdSet = new HashSet<>();
            List<EtExamPaper> allVisiblePapers = new ArrayList<>();
            
            // 添加部门试卷
            for (EtExamPaper paper : examPapersByDepartment) {
                if (paper != null && paper.getId() != null && paperIdSet.add(paper.getId())) {
                    allVisiblePapers.add(paper);
                }
            }
            
            // 添加群组试卷
            for (EtExamPaper paper : examPapersByGroups) {
                if (paper != null && paper.getId() != null && paperIdSet.add(paper.getId())) {
                    allVisiblePapers.add(paper);
                }
            }
            
            // 6. 过滤掉已完成的试卷，转换为PendingExamDTO
            List<Long> finalCompletedExamPaperIds = completedExamPaperIds;
            return allVisiblePapers.stream()
                    .filter(paper -> !finalCompletedExamPaperIds.contains(paper.getId()))
                    .map(this::convertToPendingExamDTO)
                    .collect(Collectors.toList());
                    
        } catch (Exception e) {
            // 记录异常日志
            log.error("获取用户待考试列表时发生异常，用户ID: {}, 错误信息: {}", userId, e.getMessage(), e);
            // 返回空列表而不是抛出异常，保证系统稳定性
            return new ArrayList<>();
        }
    }
    
    /**
     * 将EtExamPaper转换为PendingExamDTO
     */
    private PendingExamDTO convertToPendingExamDTO(EtExamPaper paper) {
        PendingExamDTO dto = new PendingExamDTO();
        dto.setId(paper.getId());
        dto.setTitle(paper.getName());
        dto.setDescription(paper.getContent());
        dto.setStartTime(paper.getCreateTime());
        dto.setEndTime(paper.getUpdateTime());
        dto.setDuration(paper.getDuration());
        dto.setMaxAttempts(paper.getExamCount());
        dto.setTotalScore(paper.getTotalPoint().intValue());
        dto.setPassScore(paper.getPassPoint().intValue());
        return dto;
    }

    /**
     * 根据用户ID查询用户考试记录，且提交时间不为空
     * @param userId 用户ID
     * @return 用户考试记录列表，按提交时间降序排列
     */
    public List<EtUserExamHistory> getUserExamHistoryWithSubmitTime(Long userId) {
        // 边界场景处理：参数校验
        if (userId == null || userId <= 0) {
            log.warn("getUserExamHistoryWithSubmitTime: 用户ID无效，userId: {}", userId);
            return new ArrayList<>();
        }
        
        try {
            log.info("开始查询用户考试历史记录，用户ID: {}", userId);
            
            List<EtUserExamHistory> history = etUserExamHistoryMapper.getUserExamHistoryWithSubmitTime(userId);
            if (history == null) {
                history = new ArrayList<>();
            }
            
            log.info("用户考试历史记录查询完成，用户ID: {}, 记录数量: {}", userId, history.size());
            return history;
            
        } catch (Exception e) {
            log.error("查询用户考试历史记录时发生异常，用户ID: {}, 错误信息: {}", userId, e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}