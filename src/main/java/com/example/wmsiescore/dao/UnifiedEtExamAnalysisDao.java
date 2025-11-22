package com.example.wmsiescore.dao;

import com.example.wmsiescore.dto.query.EtExamAnalysisQuery;
import com.example.wmsiescore.model.EtExamAnalysis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 考试分析表统一DAO接口
 * 提供考试分析表的标准CRUD操作和高级查询功能
 * 对应数据库表：et_user_exam_history
 */
@Mapper
public interface UnifiedEtExamAnalysisDao {
    
    /**
     * 插入考试分析记录（只插入非空字段）
     * @param etExamAnalysis 考试分析实体对象
     * @return 影响的行数
     */
    int insertSelective(EtExamAnalysis etExamAnalysis);
    
    /**
     * 插入或更新考试分析记录（使用ON DUPLICATE KEY UPDATE）
     * @param etExamAnalysis 考试分析实体对象
     * @return 影响的行数
     */
    int insertOrUpdate(EtExamAnalysis etExamAnalysis);
    
    /**
     * 根据条件查询考试分析列表
     * @param query 查询条件对象
     * @return 考试分析列表
     */
    List<EtExamAnalysis> selectByCondition(EtExamAnalysisQuery query);
    
    /**
     * 根据条件分页查询考试分析列表
     * @param query 查询条件对象（包含offset和pageSize）
     * @return 考试分析列表
     */
    List<EtExamAnalysis> selectByConditionWithPage(EtExamAnalysisQuery query);
    
    /**
     * 根据条件统计考试分析数量
     * @param query 查询条件对象
     * @return 考试分析数量
     */
    int countByCondition(EtExamAnalysisQuery query);
    
    /**
     * 批量插入考试分析记录
     * @param examAnalyses 考试分析列表
     * @return 影响的行数
     */
    int batchInsert(@Param("examAnalyses") List<EtExamAnalysis> examAnalyses);
    
    /**
     * 批量删除考试分析记录
     * @param ids 考试分析ID列表
     * @return 影响的行数
     */
    int batchDelete(@Param("ids") List<Long> ids);
    
    /**
     * 根据条件更新考试分析记录（只更新非空字段）
     * @param query 查询条件对象（必须包含id）
     * @return 影响的行数
     */
    int updateByCondition(EtExamAnalysisQuery query);
    
    /**
     * 根据ID查询考试分析记录
     * @param histId 历史记录ID
     * @return 考试分析记录
     */
    EtExamAnalysis selectById(@Param("histId") Long histId);
    
    /**
     * 根据ID删除考试分析记录
     * @param histId 历史记录ID
     * @return 影响的行数
     */
    int deleteById(@Param("histId") Long histId);
    
    /**
     * 根据ID更新考试分析记录
     * @param etExamAnalysis 考试分析实体对象
     * @return 影响的行数
     */
    int updateById(EtExamAnalysis etExamAnalysis);
    
    /**
     * 查询所有考试分析记录
     * @return 考试分析列表
     */
    List<EtExamAnalysis> selectAll();
    
    /**
     * 根据试卷ID查询考试分析记录
     * @param examPaperId 试卷ID
     * @return 考试分析列表
     */
    List<EtExamAnalysis> selectByExamPaperId(@Param("examPaperId") Long examPaperId);
    
    /**
     * 根据用户ID查询考试分析记录
     * @param userId 用户ID
     * @return 考试分析列表
     */
    List<EtExamAnalysis> selectByUserId(@Param("userId") Long userId);
    
    /**
     * 根据试卷ID和用户ID查询考试分析记录
     * @param examPaperId 试卷ID
     * @param userId 用户ID
     * @return 考试分析记录
     */
    EtExamAnalysis selectByExamPaperIdAndUserId(
        @Param("examPaperId") Long examPaperId,
        @Param("userId") Long userId
    );
    
    /**
     * 根据及格状态查询考试分析记录
     * @param passStatus 及格状态
     * @return 考试分析列表
     */
    List<EtExamAnalysis> selectByPassStatus(@Param("passStatus") String passStatus);
    
    /**
     * 统计试卷的平均分
     * @param examPaperId 试卷ID
     * @return 平均分
     */
    Double avgScoreByExamPaperId(@Param("examPaperId") Long examPaperId);
    
    /**
     * 统计试卷的最高分
     * @param examPaperId 试卷ID
     * @return 最高分
     */
    Double maxScoreByExamPaperId(@Param("examPaperId") Long examPaperId);
    
    /**
     * 统计试卷的最低分
     * @param examPaperId 试卷ID
     * @return 最低分
     */
    Double minScoreByExamPaperId(@Param("examPaperId") Long examPaperId);
    
    /**
     * 统计试卷的及格率
     * @param examPaperId 试卷ID
     * @return 及格率
     */
    Double passRateByExamPaperId(@Param("examPaperId") Long examPaperId);
    
    /**
     * 统计试卷的考试人数
     * @param examPaperId 试卷ID
     * @return 考试人数
     */
    Integer countByExamPaperId(@Param("examPaperId") Long examPaperId);
    
    /**
     * 统计用户的考试次数
     * @param userId 用户ID
     * @return 考试次数
     */
    Integer countByUserId(@Param("userId") Long userId);
    
    /**
     * 统计用户的平均分
     * @param userId 用户ID
     * @return 平均分
     */
    Double avgScoreByUserId(@Param("userId") Long userId);
    
    /**
     * 统计用户的及格率
     * @param userId 用户ID
     * @return 及格率
     */
    Double passRateByUserId(@Param("userId") Long userId);
    
    /**
     * 按分数段统计试卷考试人数
     * @param examPaperId 试卷ID
     * @return 分数段统计结果
     */
    List<Map<String, Object>> scoreDistributionByExamPaperId(@Param("examPaperId") Long examPaperId);
    
    /**
     * 按日期统计试卷考试人数
     * @param query 查询条件对象（必须包含examPaperId、startDate和endDate）
     * @return 日期统计结果
     */
    List<Map<String, Object>> dailyCountByExamPaperId(EtExamAnalysisQuery query);
}