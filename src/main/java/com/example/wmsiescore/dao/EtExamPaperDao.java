package com.example.wmsiescore.dao;

import com.example.wmsiescore.model.EtExamPaper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试卷表DAO接口
 */
@Mapper
public interface EtExamPaperDao {
    
    /**
     * 插入试卷
     */
    int insert(EtExamPaper etExamPaper);
    
    /**
     * 根据ID删除试卷
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 根据ID更新试卷
     */
    int updateById(EtExamPaper etExamPaper);
    
    /**
     * 根据ID查询试卷
     */
    EtExamPaper selectById(@Param("id") Long id);
    
    /**
     * 分页查询试卷列表
     */
    List<EtExamPaper> selectExamPaperList(
        @Param("name") String name,
        @Param("status") String status,
        @Param("validdpt") String validdpt,
        @Param("validsource") String validsource,
        @Param("paperType") String paperType,
        @Param("offset") Integer offset,
        @Param("pageSize") Integer pageSize
    );
    
    /**
     * 统计试卷总数
     */
    Long countExamPaperList(
        @Param("name") String name,
        @Param("status") String status,
        @Param("validdpt") String validdpt,
        @Param("validsource") String validsource,
        @Param("paperType") String paperType
    );
    
    /**
     * 查询所有试卷
     */
    List<EtExamPaper> selectAll();
}