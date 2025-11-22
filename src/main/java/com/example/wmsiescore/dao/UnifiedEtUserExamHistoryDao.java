package com.example.wmsiescore.dao;

import com.example.wmsiescore.dto.ExamHistoryDetailDTO;
import com.example.wmsiescore.dto.query.EtUserExamHistoryQuery;
import com.example.wmsiescore.model.EtUserExamHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户考试历史表统一DAO接口
 * 提供用户考试历史表的标准CRUD操作和高级查询功能
 */
@Mapper
public interface UnifiedEtUserExamHistoryDao {
    
    /**
     * 插入用户考试历史记录（只插入非空字段）
     * @param etUserExamHistory 用户考试历史实体对象
     * @return 影响的行数
     */
    int insertSelective(EtUserExamHistory etUserExamHistory);
    
    /**
     * 插入或更新用户考试历史记录（使用ON DUPLICATE KEY UPDATE）
     * @param etUserExamHistory 用户考试历史实体对象
     * @return 影响的行数
     */
    int insertOrUpdate(EtUserExamHistory etUserExamHistory);
    
    /**
     * 根据条件查询用户考试历史列表
     * @param query 查询条件对象
     * @return 用户考试历史列表
     */
    List<EtUserExamHistory> selectByCondition(EtUserExamHistoryQuery query);
    
    /**
     * 根据条件分页查询用户考试历史列表
     * @param query 查询条件对象（包含offset和pageSize）
     * @return 用户考试历史列表
     */
    List<EtUserExamHistory> selectByConditionWithPage(EtUserExamHistoryQuery query);
    
    /**
     * 根据条件统计用户考试历史数量
     * @param query 查询条件对象
     * @return 用户考试历史数量
     */
    int countByCondition(EtUserExamHistoryQuery query);
    
    /**
     * 批量插入用户考试历史记录
     * @param userExamHistories 用户考试历史列表
     * @return 影响的行数
     */
    int batchInsert(@Param("userExamHistories") List<EtUserExamHistory> userExamHistories);
    
    /**
     * 批量删除用户考试历史记录
     * @param histIds 历史记录ID列表
     * @return 影响的行数
     */
    int batchDelete(@Param("histIds") List<Long> histIds);
    
    /**
     * 根据条件更新用户考试历史记录（只更新非空字段）
     * @param query 查询条件对象（必须包含histId）
     * @return 影响的行数
     */
    int updateByCondition(EtUserExamHistoryQuery query);
    
    /**
     * 根据ID查询用户考试历史记录
     * @param histId 历史记录ID
     * @return 用户考试历史记录
     */
    EtUserExamHistory selectById(@Param("histId") Long histId);
    
    /**
     * 根据ID删除用户考试历史记录
     * @param histId 历史记录ID
     * @return 影响的行数
     */
    int deleteById(@Param("histId") Long histId);
    
    /**
     * 根据ID更新用户考试历史记录
     * @param etUserExamHistory 用户考试历史实体对象
     * @return 影响的行数
     */
    int updateById(EtUserExamHistory etUserExamHistory);
    
    /**
     * 查询所有用户考试历史记录
     * @return 用户考试历史列表
     */
    List<EtUserExamHistory> selectAll();
    
    /**
     * 根据用户ID查询考试历史记录
     * @param userId 用户ID
     * @return 用户考试历史列表
     */
    List<EtUserExamHistory> selectByUserId(@Param("userId") Long userId);
    
    /**
     * 根据试卷ID查询考试历史记录
     * @param examPaperId 试卷ID
     * @return 用户考试历史列表
     */
    List<EtUserExamHistory> selectByExamPaperId(@Param("examPaperId") Long examPaperId);
    
    /**
     * 根据用户ID和试卷ID查询考试历史记录
     * @param userId 用户ID
     * @param examPaperId 试卷ID
     * @return 用户考试历史列表
     */
    List<EtUserExamHistory> selectByUserIdAndExamPaperId(
        @Param("userId") Long userId,
        @Param("examPaperId") Long examPaperId
    );
    
    /**
     * 分页查询考试历史列表（包含用户和试卷信息）
     */
    List<ExamHistoryDetailDTO> selectExamHistoryList(
        @Param("examPaperName") String examPaperName,
        @Param("department") String department,
        @Param("groupname") String groupname,
        @Param("offset") Integer offset,
        @Param("pageSize") Integer pageSize
    );
    
    /**
     * 统计考试历史总数
     */
    Long countExamHistoryList(
        @Param("examPaperName") String examPaperName,
        @Param("department") String department,
        @Param("groupname") String groupname
    );
    
    /**
     * 根据用户ID查询有提交时间的考试历史记录
     * @param userId 用户ID
     * @return 用户考试历史列表
     */
    List<EtUserExamHistory> getUserExamHistoryWithSubmitTime(@Param("userId") Long userId);
    
    /**
     * 根据用户ID获取已完成的试卷ID列表
     * @param userId 用户ID
     * @return 已完成的试卷ID列表
     */
    List<Long> getCompletedExamPaperIds(@Param("userId") Long userId);
}