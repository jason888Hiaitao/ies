package com.example.wmsiescore.mapper;

import com.example.wmsiescore.model.ExamPaper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ExamPaperMapper {
    @Insert("INSERT INTO et_exam_paper (name, description, category_id, permission, create_time, create_by) " +
            "VALUES (#{name}, #{description}, #{categoryId}, #{permission}, #{createTime}, #{createBy})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertExamPaper(ExamPaper examPaper);

    @Update("UPDATE et_exam_paper SET name = #{name}, description = #{description}, category_id = #{categoryId}, " +
            "permission = #{permission}, update_time = #{updateTime}, update_by = #{updateBy} " +
            "WHERE id = #{id}")
    int updateExamPaper(ExamPaper examPaper);

    @Delete("DELETE FROM et_exam_paper WHERE id = #{paperId}")
    int deleteExamPaper(@Param("paperId") Long paperId);

    @Select("SELECT * FROM et_exam_paper WHERE id = #{paperId}")
    ExamPaper getExamPaperById(@Param("paperId") Long paperId);

    @Select("SELECT * FROM et_exam_paper WHERE category_id = #{categoryId}")
    List<ExamPaper> listExamPapersByCategory(@Param("categoryId") Long categoryId);
}