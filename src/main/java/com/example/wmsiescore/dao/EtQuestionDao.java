package com.example.wmsiescore.dao;

import com.example.wmsiescore.dto.QuestionListDTO;
import com.example.wmsiescore.model.EtQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试题表DAO接口
 */
@Mapper
public interface EtQuestionDao {
    
    /**
     * 插入试题记录
     */
    int insert(EtQuestion etQuestion);
    
    /**
     * 根据ID删除试题记录
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 更新试题记录
     */
    int updateById(EtQuestion etQuestion);
    
    /**
     * 根据ID查询试题记录
     */
    EtQuestion selectById(@Param("id") Long id);
    
    /**
     * 查询所有试题记录
     */
    List<EtQuestion> selectAll();
    
    /**
     * 根据名称模糊查询试题记录
     */
    List<EtQuestion> selectByNameLike(@Param("name") String name);
    
    /**
     * 根据试题类型ID查询试题记录
     */
    List<EtQuestion> selectByTypeId(@Param("questionTypeId") Long questionTypeId);
    
    /**
     * 根据分组ID查询试题记录
     */
    List<EtQuestion> selectByGroupId(@Param("groupId") Long groupId);
    
    /**
     * 根据是否可见查询试题记录
     */
    List<EtQuestion> selectByIsVisible(@Param("isVisible") Boolean isVisible);
    
    /**
     * 根据创建人查询试题记录
     */
    List<EtQuestion> selectByCreator(@Param("creator") String creator);
    
    /**
     * 根据难度查询试题记录
     */
    List<EtQuestion> selectByDifficulty(@Param("difficulty") String difficulty);
    
    /**
     * 根据条件分页查询试题记录
     */
    List<EtQuestion> selectByCondition(@Param("name") String name, 
                                       @Param("questionTypeId") Long questionTypeId,
                                       @Param("groupId") Long groupId,
                                       @Param("isVisible") Boolean isVisible,
                                       @Param("creator") String creator,
                                       @Param("difficulty") String difficulty,
                                       @Param("offset") Integer offset, 
                                       @Param("limit") Integer limit);
    
    /**
     * 根据条件统计试题记录数
     */
    int countByCondition(@Param("name") String name, 
                        @Param("questionTypeId") Long questionTypeId,
                        @Param("groupId") Long groupId,
                        @Param("isVisible") Boolean isVisible,
                        @Param("creator") String creator,
                        @Param("difficulty") String difficulty);
    
    /**
     * 批量删除试题记录
     */
    int deleteByIds(@Param("ids") List<Long> ids);
    
    /**
     * 统计记录总数
     */
    int countAll();
    
    /**
     * 根据ID列表查询试题记录
     */
    List<EtQuestion> selectByIds(@Param("ids") List<Long> ids);
    
    /**
     * 多表关联查询试题列表（包含知识点和题库信息）
     */
    List<QuestionListDTO> selectQuestionListWithDetails(
            @Param("questionName") String questionName,
            @Param("questionTypeId") Integer questionTypeId,
            @Param("pointId") Integer pointId,
            @Param("fieldId") Integer fieldId,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit
    );
    
    /**
     * 多表关联统计试题数量
     */
    Integer countQuestionListWithDetails(
            @Param("questionName") String questionName,
            @Param("questionTypeId") Integer questionTypeId,
            @Param("pointId") Integer pointId,
            @Param("fieldId") Integer fieldId
    );
}