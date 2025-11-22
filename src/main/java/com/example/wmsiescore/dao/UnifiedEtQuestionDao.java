package com.example.wmsiescore.dao;

import com.example.wmsiescore.dto.query.EtQuestionQuery;
import com.example.wmsiescore.model.EtQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试题表统一DAO接口
 * 提供试题表的标准CRUD操作和高级查询功能
 */
@Mapper
public interface UnifiedEtQuestionDao {
    
    /**
     * 插入试题记录（只插入非空字段）
     * @param etQuestion 试题实体对象
     * @return 影响的行数
     */
    int insertSelective(EtQuestion etQuestion);
    
    /**
     * 插入或更新试题记录（使用ON DUPLICATE KEY UPDATE）
     * @param etQuestion 试题实体对象
     * @return 影响的行数
     */
    int insertOrUpdate(EtQuestion etQuestion);
    
    /**
     * 根据条件查询试题列表
     * @param query 查询条件对象
     * @return 试题列表
     */
    List<EtQuestion> selectByCondition(EtQuestionQuery query);
    
    /**
     * 根据条件分页查询试题列表
     * @param query 查询条件对象（包含offset和pageSize）
     * @return 试题列表
     */
    List<EtQuestion> selectByConditionWithPage(EtQuestionQuery query);
    
    /**
     * 根据条件统计试题数量
     * @param query 查询条件对象
     * @return 试题数量
     */
    int countByCondition(EtQuestionQuery query);
    
    /**
     * 批量插入试题记录
     * @param questions 试题列表
     * @return 影响的行数
     */
    int batchInsert(@Param("questions") List<EtQuestion> questions);
    
    /**
     * 批量删除试题记录
     * @param ids 试题ID列表
     * @return 影响的行数
     */
    int batchDelete(@Param("ids") List<Long> ids);
    
    /**
     * 根据条件更新试题记录（只更新非空字段）
     * @param query 查询条件对象（必须包含id）
     * @return 影响的行数
     */
    int updateByCondition(EtQuestionQuery query);
    
    /**
     * 根据ID查询试题记录
     * @param id 试题ID
     * @return 试题记录
     */
    EtQuestion selectById(@Param("id") Long id);
    
    /**
     * 根据ID删除试题记录
     * @param id 试题ID
     * @return 影响的行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 根据ID更新试题记录
     * @param etQuestion 试题实体对象
     * @return 影响的行数
     */
    int updateById(EtQuestion etQuestion);
    
    /**
     * 查询所有试题记录
     * @return 试题列表
     */
    List<EtQuestion> selectAll();
    
    /**
     * 根据题目类型ID查询试题记录
     * @param questionTypeId 题目类型ID
     * @return 试题列表
     */
    List<EtQuestion> selectByQuestionTypeId(@Param("questionTypeId") Long questionTypeId);
    
    /**
     * 根据分组ID查询试题记录
     * @param groupId 分组ID
     * @return 试题列表
     */
    List<EtQuestion> selectByGroupId(@Param("groupId") Long groupId);
    
    /**
     * 根据创建人查询试题记录
     * @param creator 创建人
     * @return 试题列表
     */
    List<EtQuestion> selectByCreator(@Param("creator") String creator);
    
    /**
     * 根据难度查询试题记录
     * @param difficulty 难度
     * @return 试题列表
     */
    List<EtQuestion> selectByDifficulty(@Param("difficulty") String difficulty);
    
    /**
     * 根据是否可见查询试题记录
     * @param isVisible 是否可见
     * @return 试题列表
     */
    List<EtQuestion> selectByIsVisible(@Param("isVisible") Boolean isVisible);
    
    /**
     * 根据名称模糊查询试题记录
     * @param name 试题名称
     * @return 试题列表
     */
    List<EtQuestion> selectByNameLike(@Param("name") String name);
    
    /**
     * 根据试卷ID查询试题记录
     * @param examPaperId 试卷ID
     * @return 试题列表
     */
    List<EtQuestion> selectByExamPaperId(@Param("examPaperId") Long examPaperId);
}