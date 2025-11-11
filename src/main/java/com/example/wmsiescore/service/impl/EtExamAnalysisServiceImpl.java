package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.mapper.EtExamAnalysisMapper;
import com.example.wmsiescore.mapper.EtExamPaperMapper;
import com.example.wmsiescore.model.EtExamPaper;
import com.example.wmsiescore.model.EtUserExamHistory;
import com.example.wmsiescore.model.ExamRanking;
import com.example.wmsiescore.model.ExamStatistics;
import com.example.wmsiescore.service.EtExamAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class EtExamAnalysisServiceImpl implements EtExamAnalysisService {
    @Autowired
    private EtExamAnalysisMapper etExamAnalysisMapper;
    
    @Autowired
    private EtExamPaperMapper etExamPaperMapper;

    @Override
    public List<ExamRanking> getExamRankings(Long examPaperId) {
       /* List<EtUserExamHistory> histories = etExamAnalysisMapper.getExamRankings(examPaperId);
        List<ExamRanking> rankings = new ArrayList<>();
        
        int rank = 1;
        for (int i = 0; i < histories.size(); i++) {
            EtUserExamHistory history = histories.get(i);
            ExamRanking ranking = new ExamRanking();
            ranking.setUserId(history.getUserId());
            ranking.setUserName(history.getUserName());
            ranking.setUserAccount(history.getUserAccount());
            ranking.setScore(history.getScore());
            ranking.setTotalScore(history.getTotalScore());
            ranking.setActualDuration(history.getActualDuration());
            ranking.setSubmitTime(history.getEndTime());
            
            // 处理并列排名
            if (i > 0 && histories.get(i - 1).getScore().equals(history.getScore())) {
                ranking.setRank(rankings.get(i - 1).getRank());
            } else {
                ranking.setRank(rank);
            }
            rank++;
            
            rankings.add(ranking);
        }*/
        
        return null;
    }

    @Override
    public ExamStatistics getExamStatistics(Long examPaperId) {
        ExamStatistics statistics = new ExamStatistics();
        statistics.setExamPaperId(examPaperId);
        
        // 获取试卷信息
        EtExamPaper examPaper = etExamPaperMapper.getExamPaperById(examPaperId);
        if (examPaper != null) {
//            statistics.setExamTitle(examPaper.getTitle());
        }
        
        // 统计各项数据
        statistics.setTotalParticipants(getTotalParticipants(examPaperId));
        statistics.setActualParticipants(getActualParticipants(examPaperId));
        statistics.setAbsentCount(getAbsentCount(examPaperId));
        statistics.setAverageScore(getAverageScore(examPaperId));
        statistics.setMaxScore(getMaxScore(examPaperId));
        statistics.setMinScore(getMinScore(examPaperId));
        
        if (examPaper != null) {
//            statistics.setPassRate(calculatePassRate(examPaperId, examPaper.getPassScore()));
        }
        
        // 获取排名
        statistics.setRankings(getExamRankings(examPaperId));
        
        return statistics;
    }

    @Override
    public Integer getTotalParticipants(Long examPaperId) {
        return etExamAnalysisMapper.countTotalParticipants(examPaperId);
    }

    @Override
    public Integer getActualParticipants(Long examPaperId) {
        return etExamAnalysisMapper.countActualParticipants(examPaperId);
    }

    private Integer getAbsentCount(Long examPaperId) {
        return etExamAnalysisMapper.countAbsentParticipants(examPaperId);
    }

    @Override
    public List<EtUserExamHistory> getParticipantList(Long examPaperId) {
        return etExamAnalysisMapper.listCompletedExams(examPaperId);
    }

    @Override
    public List<EtUserExamHistory> getAbsentList(Long examPaperId) {
        return etExamAnalysisMapper.listAbsentExams(examPaperId);
    }

    @Override
    public BigDecimal getAverageScore(Long examPaperId) {
        return etExamAnalysisMapper.calculateAverageScore(examPaperId);
    }

    private Integer getMaxScore(Long examPaperId) {
        return etExamAnalysisMapper.getMaxScore(examPaperId);
    }

    private Integer getMinScore(Long examPaperId) {
        return etExamAnalysisMapper.getMinScore(examPaperId);
    }

    private BigDecimal calculatePassRate(Long examPaperId, Integer passScore) {
        if (passScore == null) {
            return BigDecimal.ZERO;
        }
        return etExamAnalysisMapper.calculatePassRate(examPaperId, passScore);
    }

    @Override
    public List<EtUserExamHistory> getUserExamDurations(Long examPaperId) {
        return etExamAnalysisMapper.getUserExamDurations(examPaperId);
    }

    @Override
    public List<EtUserExamHistory> exportExamResults(Long examPaperId) {
        return etExamAnalysisMapper.getExamResultsForExport(examPaperId);
    }

    @Override
    public Long recordUserExamHistory(EtUserExamHistory history) {
        history.setCreateTime(new Timestamp(System.currentTimeMillis()));
        etExamAnalysisMapper.insertUserExamHistory(history);
//        return history.getId();
        return null;

    }

    @Override
    public Boolean updateUserExamHistory(EtUserExamHistory history) {
//        history.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return etExamAnalysisMapper.updateUserExamHistory(history) > 0;
    }

    @Override
    public EtUserExamHistory getUserExamHistory(Long userId, Long examPaperId) {
        return etExamAnalysisMapper.getUserExamHistory(userId, examPaperId);
    }
}