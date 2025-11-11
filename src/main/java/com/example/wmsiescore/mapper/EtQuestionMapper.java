package com.example.wmsiescore.mapper;

import com.example.wmsiescore.model.EtQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EtQuestionMapper {
    void insertQuestion(EtQuestion question);

    int updateQuestion(EtQuestion question);

    int deleteQuestion(@Param("id") Long id);

    @Select("SELECT * FROM et_question WHERE id = #{id}")
    EtQuestion getQuestionById(@Param("id") Long id);

    List<EtQuestion> listQuestionsByGroupId(@Param("groupId") Long groupId);

    List<EtQuestion> listQuestionsByType(@Param("questionTypeId") Long questionTypeId);

    List<EtQuestion> listQuestionsByCreator(@Param("creator") String creator);

    List<EtQuestion> listAllQuestions();

    List<EtQuestion> searchQuestions(@Param("keyword") String keyword);

    int updateQuestionVisibility(@Param("id") Long id, @Param("isVisible") Boolean isVisible);

    List<EtQuestion> listVisibleQuestions();

    List<EtQuestion> listQuestionsByDifficulty(@Param("difficulty") String difficulty);

    int updateQuestionStatistics(@Param("id") Long id, @Param("exposeTimes") Integer exposeTimes, 
                                @Param("rightTimes") Integer rightTimes, @Param("wrongTimes") Integer wrongTimes);

    @Select("SELECT q.* FROM et_question q " +
            "INNER JOIN et_exam_paper ep ON q.group_id = ep.group_id " +
            "WHERE ep.id = #{examPaperId} and ep.is_visible=1 " +
            "ORDER BY q.id")
    List<EtQuestion> getQuestionsByPaper(@Param("examPaperId") Long examPaperId);
}