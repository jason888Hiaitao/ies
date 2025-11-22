package com.example.wmsiescore.dao;

import com.example.wmsiescore.dto.query.EtExamPaperQuery;
import com.example.wmsiescore.model.EtExamPaper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试卷表统一DAO接口
 * 提供试卷表的标准CRUD操作和高级查询功能
 */
@Mapper
public interface UnifiedEtExamPaperDao {
    
    /**
     * 插入试卷记录（只插入非空字段）
     * @param etExamPaper 试卷实体对象
     * @return 影响的行数
     */
    int insertSelective(EtExamPaper etExamPaper);
    
    /**
     * 插入或更新试卷记录（使用ON DUPLICATE KEY UPDATE）
     * @param etExamPaper 试卷实体对象
     * @return 影响的行数
     */
    int insertOrUpdate(EtExamPaper etExamPaper);
    
    /**
     * 根据条件查询试卷列表
     * @param query 查询条件对象
     * @return 试卷列表
     */
    List<EtExamPaper> selectByCondition(EtExamPaperQuery query);
    
    /**
     * 根据条件分页查询试卷列表
     * @param query 查询条件对象（包含offset和pageSize）
     * @return 试卷列表
     */
    List<EtExamPaper> selectByConditionWithPage(EtExamPaperQuery query);
    
    /**
     * 根据条件统计试卷数量
     * @param query 查询条件对象
     * @return 试卷数量
     */
    int countByCondition(EtExamPaperQuery query);
    
    /**
     * 批量插入试卷记录
     * @param examPapers 试卷列表
     * @return 影响的行数
     */
    int batchInsert(@Param("examPapers") List<EtExamPaper> examPapers);
    
    /**
     * 批量删除试卷记录
     * @param ids 试卷ID列表
     * @return 影响的行数
     */
    int batchDelete(@Param("ids") List<Long> ids);
    
    /**
     * 根据条件更新试卷记录（只更新非空字段）
     * @param query 查询条件对象（必须包含id）
     * @return 影响的行数
     */
    int updateByCondition(EtExamPaperQuery query);
    
    /**
     * 根据ID查询试卷记录
     * @param id 试卷ID
     * @return 试卷记录
     */
    EtExamPaper selectById(@Param("id") Long id);
    
    /**
     * 根据ID删除试卷记录
     * @param id 试卷ID
     * @return 影响的行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 根据ID更新试卷记录
     * @param etExamPaper 试卷实体对象
     * @return 影响的行数
     */
    int updateById(EtExamPaper etExamPaper);
    
    /**
     * 查询所有试卷记录
     * @return 试卷列表
     */
    List<EtExamPaper> selectAll();
    
    /**
     * 根据用户ID获取已完成的试卷ID列表
     * @param userId 用户ID
     * @return 已完成的试卷ID列表
     */
    List<Long> getCompletedExamPaperIds(@Param("userId") Long userId);
    
    /**
     * 获取用户可见的试卷列表
     * @param userId 用户ID
     * @return 可见试卷列表
     */
    List<EtExamPaper> getVisibleExamPapersForUser(@Param("userId") Long userId);
    
    /**
     * 根据部门获取用户可见的试卷列表
     * @param department 部门名称
     * @return 可见试卷列表
     */
    List<EtExamPaper> getExamPapersByUserAndDepartment(@Param("department") String department);
    
    /**
     * 根据领域ID列表获取试卷列表
     * @param fieldIds 领域ID列表
     * @return 试卷列表
     */
    List<EtExamPaper> getExamPapersByFieldIds(@Param("fieldIds") List<Long> fieldIds);
}