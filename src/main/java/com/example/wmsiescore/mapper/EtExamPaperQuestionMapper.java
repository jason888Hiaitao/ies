package com.example.wmsiescore.mapper;

import com.example.wmsiescore.model.EtExamPaperQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EtExamPaperQuestionMapper {
    void insertExamPaperQuestion(EtExamPaperQuestion examPaperQuestion);

    int updateExamPaperQuestion(EtExamPaperQuestion examPaperQuestion);

    int deleteExamPaperQuestion(@Param("id") Long id);

    int deleteExamPaperQuestionByPaperAndQuestion(@Param("examPaperId") Long examPaperId, @Param("questionId") Long questionId);

    int deleteQuestionsByExamPaper(@Param("examPaperId") Long examPaperId);

    EtExamPaperQuestion getExamPaperQuestion(@Param("examPaperId") Long examPaperId, @Param("questionId") Long questionId);

    List<EtExamPaperQuestion> listQuestionsByExamPaper(@Param("examPaperId") Long examPaperId);

    List<EtExamPaperQuestion> listExamPapersByQuestion(@Param("questionId") Long questionId);

    int updateQuestionSortOrder(@Param("id") Long id, @Param("sortOrder") Integer sortOrder);

    int updateQuestionScore(@Param("id") Long id, @Param("score") Integer score);

    int updateExamPaperQuestionStatus(@Param("id") Long id, @Param("status") String status);

    int getMaxSortOrder(@Param("examPaperId") Long examPaperId);
}