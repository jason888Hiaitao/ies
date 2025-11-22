package com.example.wmsiescore.dao;

import com.example.wmsiescore.dto.query.EtExamPaperQuestionQuery;
import com.example.wmsiescore.model.EtExamPaperQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试卷试题关联表统一DAO接口
 * 提供试卷试题关联表的标准CRUD操作和高级查询功能
 */
@Mapper
public interface UnifiedEtExamPaperQuestionDao {
    
    /**
     * 插入试卷试题关联记录（只插入非空字段）
     * @param etExamPaperQuestion 试卷试题关联实体对象
     * @return 影响的行数
     */
    int insertSelective(EtExamPaperQuestion etExamPaperQuestion);
    
    /**
     * 插入或更新试卷试题关联记录（使用ON DUPLICATE KEY UPDATE）
     * @param etExamPaperQuestion 试卷试题关联实体对象
     * @return 影响的行数
     */
    int insertOrUpdate(EtExamPaperQuestion etExamPaperQuestion);
    
    /**
     * 根据条件查询试卷试题关联列表
     * @param query 查询条件对象
     * @return 试卷试题关联列表
     */
    List<EtExamPaperQuestion> selectByCondition(EtExamPaperQuestionQuery query);
    
    /**
     * 根据条件分页查询试卷试题关联列表
     * @param query 查询条件对象（包含offset和pageSize）
     * @return 试卷试题关联列表
     */
    List<EtExamPaperQuestion> selectByConditionWithPage(EtExamPaperQuestionQuery query);
    
    /**
     * 根据条件统计试卷试题关联数量
     * @param query 查询条件对象
     * @return 试卷试题关联数量
     */
    int countByCondition(EtExamPaperQuestionQuery query);
    
    /**
     * 批量插入试卷试题关联记录
     * @param examPaperQuestions 试卷试题关联列表
     * @return 影响的行数
     */
    int batchInsert(@Param("examPaperQuestions") List<EtExamPaperQuestion> examPaperQuestions);
    
    /**
     * 批量删除试卷试题关联记录
     * @param ids 关联ID列表
     * @return 影响的行数
     */
    int batchDelete(@Param("ids") List<Long> ids);
    
    /**
     * 根据条件更新试卷试题关联记录（只更新非空字段）
     * @param query 查询条件对象（必须包含id）
     * @return 影响的行数
     */
    int updateByCondition(EtExamPaperQuestionQuery query);
    
    /**
     * 根据ID查询试卷试题关联记录
     * @param id 关联ID
     * @return 试卷试题关联记录
     */
    EtExamPaperQuestion selectById(@Param("id") Long id);
    
    /**
     * 根据ID删除试卷试题关联记录
     * @param id 关联ID
     * @return 影响的行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 根据ID更新试卷试题关联记录
     * @param etExamPaperQuestion 试卷试题关联实体对象
     * @return 影响的行数
     */
    int updateById(EtExamPaperQuestion etExamPaperQuestion);
    
    /**
     * 查询所有试卷试题关联记录
     * @return 试卷试题关联列表
     */
    List<EtExamPaperQuestion> selectAll();
    
    /**
     * 根据试卷ID查询试卷试题关联记录
     * @param examPaperId 试卷ID
     * @return 试卷试题关联列表
     */
    List<EtExamPaperQuestion> selectByExamPaperId(@Param("examPaperId") Long examPaperId);
    
    /**
     * 根据试题ID查询试卷试题关联记录
     * @param questionId 试题ID
     * @return 试卷试题关联列表
     */
    List<EtExamPaperQuestion> selectByQuestionId(@Param("questionId") Long questionId);
    
    /**
     * 根据试卷ID和试题ID查询试卷试题关联记录
     * @param examPaperId 试卷ID
     * @param questionId 试题ID
     * @return 试卷试题关联记录
     */
    EtExamPaperQuestion selectByExamPaperIdAndQuestionId(
        @Param("examPaperId") Long examPaperId,
        @Param("questionId") Long questionId
    );
    
    /**
     * 根据试卷ID查询试卷试题关联记录（按试题顺序排序）
     * @param examPaperId 试卷ID
     * @return 试卷试题关联列表
     */
    List<EtExamPaperQuestion> selectByExamPaperIdOrderByQuestionOrder(@Param("examPaperId") Long examPaperId);
    
    /**
     * 根据试卷ID删除试卷试题关联记录
     * @param examPaperId 试卷ID
     * @return 影响的行数
     */
    int deleteByExamPaperId(@Param("examPaperId") Long examPaperId);
    
    /**
     * 根据试题ID删除试卷试题关联记录
     * @param questionId 试题ID
     * @return 影响的行数
     */
    int deleteByQuestionId(@Param("questionId") Long questionId);
    
    /**
     * 根据试卷ID统计试题数量
     * @param examPaperId 试卷ID
     * @return 试题数量
     */
    int countQuestionsByExamPaperId(@Param("examPaperId") Long examPaperId);
    
    /**
     * 根据试卷ID计算总分
     * @param examPaperId 试卷ID
     * @return 总分
     */
    Double sumPointsByExamPaperId(@Param("examPaperId") Long examPaperId);
}