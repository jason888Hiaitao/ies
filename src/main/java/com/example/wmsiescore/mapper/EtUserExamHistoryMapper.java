package com.example.wmsiescore.mapper;

import com.example.wmsiescore.model.EtUserExamHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EtUserExamHistoryMapper {
    List<EtUserExamHistory> findAll();
    EtUserExamHistory findById(Long histId);
    int insert(EtUserExamHistory etUserExamHistory);
    int update(EtUserExamHistory etUserExamHistory);
    int delete(Long histId);

    // 获取用户考试历史记录
    List<EtUserExamHistory> getUserExamHistory(@Param("userId") Long userId);

    // 获取用户对指定试卷的考试次数
    int countUserAttempts(@Param("userId") Long userId, @Param("examPaperId") Long examPaperId);

    // 获取用户已完成的试卷ID列表
    List<Long> getCompletedExamPaperIds(@Param("userId") Long userId);

    // 根据用户ID查询用户考试记录，且提交时间不为空
    List<EtUserExamHistory> getUserExamHistoryWithSubmitTime(@Param("userId") Long userId);
}