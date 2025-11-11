package com.example.wmsiescore.mapper;

import com.example.wmsiescore.model.EtUserExamHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface EtExamAnalysisMapper {
    // 考试历史记录相关
    void insertUserExamHistory(EtUserExamHistory history);

    int updateUserExamHistory(EtUserExamHistory history);

    EtUserExamHistory getUserExamHistory(@Param("userId") Long userId, @Param("examPaperId") Long examPaperId);

    List<EtUserExamHistory> listExamHistoriesByExam(@Param("examPaperId") Long examPaperId);

    List<EtUserExamHistory> listExamHistoriesByUser(@Param("userId") Long userId);

    List<EtUserExamHistory> listCompletedExams(@Param("examPaperId") Long examPaperId);

    List<EtUserExamHistory> listAbsentExams(@Param("examPaperId") Long examPaperId);

    // 统计相关
    Integer countTotalParticipants(@Param("examPaperId") Long examPaperId);

    Integer countActualParticipants(@Param("examPaperId") Long examPaperId);

    Integer countAbsentParticipants(@Param("examPaperId") Long examPaperId);

    BigDecimal calculateAverageScore(@Param("examPaperId") Long examPaperId);

    Integer getMaxScore(@Param("examPaperId") Long examPaperId);

    Integer getMinScore(@Param("examPaperId") Long examPaperId);

    BigDecimal calculatePassRate(@Param("examPaperId") Long examPaperId, @Param("passScore") Integer passScore);

    // 排名相关
    List<EtUserExamHistory> getExamRankings(@Param("examPaperId") Long examPaperId);

    List<EtUserExamHistory> getUserExamDurations(@Param("examPaperId") Long examPaperId);

    // 导出相关
    List<EtUserExamHistory> getExamResultsForExport(@Param("examPaperId") Long examPaperId);
}