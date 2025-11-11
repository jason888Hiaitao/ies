package com.example.wmsiescore.mapper;

import com.example.wmsiescore.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("INSERT INTO et_question (content, type, options, answer, category_id, create_time, create_by) " +
            "VALUES (#{content}, #{type}, #{options}, #{answer}, #{categoryId}, #{createTime}, #{createBy})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertQuestion(Question question);

    @Update("UPDATE et_question SET content = #{content}, type = #{type}, options = #{options}, " +
            "answer = #{answer}, category_id = #{categoryId}, update_time = #{updateTime}, update_by = #{updateBy} " +
            "WHERE id = #{id}")
    int updateQuestion(Question question);

    @Delete("DELETE FROM et_question WHERE id = #{questionId}")
    int deleteQuestion(@Param("questionId") Long questionId);

    @Select("SELECT * FROM et_question WHERE id = #{questionId}")
    Question getQuestionById(@Param("questionId") Long questionId);

    @Select("SELECT * FROM et_question WHERE category_id = #{categoryId}")
    List<Question> listQuestionsByCategory(@Param("categoryId") Long categoryId);
}