package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.dao.UnifiedEtExamAnalysisDao;
import com.example.wmsiescore.dao.UnifiedEtExamPaperDao;
import com.example.wmsiescore.dao.UnifiedEtUserExamHistoryDao;
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
import java.util.Comparator;
import java.util.List;

@Service
public class EtExamAnalysisServiceImpl implements EtExamAnalysisService {
    @Autowired
    private UnifiedEtExamAnalysisDao unifiedEtExamAnalysisDao;

    @Autowired
    private UnifiedEtExamPaperDao unifiedEtExamPaperDao;

    @Autowired
    private UnifiedEtUserExamHistoryDao unifiedEtUserExamHistoryDao;

    @Override
    public List<ExamRanking> getExamRankings(Long examPaperId) {
        List<EtUserExamHistory> histories = unifiedEtUserExamHistoryDao.selectByExamPaperId(examPaperId);
        if (histories == null || histories.isEmpty()) {
            return new ArrayList<>();
        }

        EtExamPaper examPaper = unifiedEtExamPaperDao.selectById(examPaperId);
        Integer totalScore = examPaper != null && examPaper.getTotalPoint() != null
                ? examPaper.getTotalPoint().intValue()
                : null;

        histories.sort(Comparator.comparing(EtUserExamHistory::getPointGet, Comparator.nullsLast(BigDecimal::compareTo))
                .reversed()
                .thenComparing(EtUserExamHistory::getSubmitTime, Comparator.nullsLast(Timestamp::compareTo)));

        List<ExamRanking> rankings = new ArrayList<>();
        int rank = 1;
        for (int i = 0; i < histories.size(); i++) {
            EtUserExamHistory history = histories.get(i);
            ExamRanking ranking = new ExamRanking();
            ranking.setUserId(history.getUserId());
            ranking.setScore(history.getPointGet() == null ? null : history.getPointGet().intValue());
            ranking.setTotalScore(totalScore);
            ranking.setActualDuration(history.getDuration());
            ranking.setSubmitTime(history.getSubmitTime());

            if (i > 0 && histories.get(i - 1).getPointGet() != null &&
                    histories.get(i - 1).getPointGet().compareTo(history.getPointGet()) == 0) {
                ranking.setRank(rankings.get(i - 1).getRank());
            } else {
                ranking.setRank(rank);
            }
            rank++;
            rankings.add(ranking);
        }
        return rankings;
    }

    @Override
    public ExamStatistics getExamStatistics(Long examPaperId) {
        ExamStatistics statistics = new ExamStatistics();
        statistics.setExamPaperId(examPaperId);

        // 获取试卷信息
        EtExamPaper examPaper = unifiedEtExamPaperDao.selectById(examPaperId);
        if (examPaper != null) {
            statistics.setExamTitle(examPaper.getName());
        }

        // 统计各项数据
        List<EtUserExamHistory> participantList = unifiedEtUserExamHistoryDao.selectByExamPaperId(examPaperId);
        int totalParticipants = participantList == null ? 0 : participantList.size();
        int actualParticipants = (int) (participantList == null ? 0 : participantList.stream()
                .filter(history -> history.getSubmitTime() != null)
                .count());

        statistics.setTotalParticipants(totalParticipants);
        statistics.setActualParticipants(actualParticipants);
        statistics.setAbsentCount(Math.max(totalParticipants - actualParticipants, 0));
        statistics.setAverageScore(getAverageScore(examPaperId));
        statistics.setMaxScore(getMaxScore(examPaperId));
        statistics.setMinScore(getMinScore(examPaperId));

        if (examPaper != null) {
            statistics.setPassRate(calculatePassRate(examPaperId, examPaper.getPassPoint() == null
                    ? null
                    : examPaper.getPassPoint().intValue()));
        }
        
        // 获取排名
        statistics.setRankings(getExamRankings(examPaperId));
        
        return statistics;
    }

    @Override
    public Integer getTotalParticipants(Long examPaperId) {
        return unifiedEtExamAnalysisDao.countByExamPaperId(examPaperId);
    }

    @Override
    public Integer getActualParticipants(Long examPaperId) {
        List<EtUserExamHistory> participantList = unifiedEtUserExamHistoryDao.selectByExamPaperId(examPaperId);
        if (participantList == null) {
            return 0;
        }
        return (int) participantList.stream().filter(history -> history.getSubmitTime() != null).count();
    }

    private Integer getAbsentCount(Long examPaperId) {
        Integer total = getTotalParticipants(examPaperId);
        Integer actual = getActualParticipants(examPaperId);
        return Math.max((total == null ? 0 : total) - (actual == null ? 0 : actual), 0);
    }

    @Override
    public List<EtUserExamHistory> getParticipantList(Long examPaperId) {
        List<EtUserExamHistory> histories = unifiedEtUserExamHistoryDao.selectByExamPaperId(examPaperId);
        return histories == null ? new ArrayList<>() : histories;
    }

    @Override
    public List<EtUserExamHistory> getAbsentList(Long examPaperId) {
        List<EtUserExamHistory> histories = unifiedEtUserExamHistoryDao.selectByExamPaperId(examPaperId);
        if (histories == null) {
            return new ArrayList<>();
        }
        List<EtUserExamHistory> absentList = new ArrayList<>();
        for (EtUserExamHistory history : histories) {
            if (history.getSubmitTime() == null) {
                absentList.add(history);
            }
        }
        return absentList;
    }

    @Override
    public BigDecimal getAverageScore(Long examPaperId) {
        Double avgScore = unifiedEtExamAnalysisDao.avgScoreByExamPaperId(examPaperId);
        if (avgScore == null) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(avgScore).setScale(2, RoundingMode.HALF_UP);
    }

    private Integer getMaxScore(Long examPaperId) {
        Double maxScore = unifiedEtExamAnalysisDao.maxScoreByExamPaperId(examPaperId);
        return maxScore == null ? 0 : maxScore.intValue();
    }

    private Integer getMinScore(Long examPaperId) {
        Double minScore = unifiedEtExamAnalysisDao.minScoreByExamPaperId(examPaperId);
        return minScore == null ? 0 : minScore.intValue();
    }

    private BigDecimal calculatePassRate(Long examPaperId, Integer passScore) {
        Double passRate = unifiedEtExamAnalysisDao.passRateByExamPaperId(examPaperId);
        if (passRate == null) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(passRate).setScale(4, RoundingMode.HALF_UP);
    }

    @Override
    public List<EtUserExamHistory> getUserExamDurations(Long examPaperId) {
        List<EtUserExamHistory> histories = unifiedEtUserExamHistoryDao.selectByExamPaperId(examPaperId);
        return histories == null ? new ArrayList<>() : histories;
    }

    @Override
    public List<EtUserExamHistory> exportExamResults(Long examPaperId) {
        List<EtUserExamHistory> histories = unifiedEtUserExamHistoryDao.selectByExamPaperId(examPaperId);
        return histories == null ? new ArrayList<>() : histories;
    }

    @Override
    public Long recordUserExamHistory(EtUserExamHistory history) {
        history.setCreateTime(new Timestamp(System.currentTimeMillis()));
        unifiedEtUserExamHistoryDao.insertSelective(history);
        return history.getHistId();
    }

    @Override
    public Boolean updateUserExamHistory(EtUserExamHistory history) {
        return unifiedEtUserExamHistoryDao.updateById(history) > 0;
    }

    @Override
    public EtUserExamHistory getUserExamHistory(Long userId, Long examPaperId) {
        List<EtUserExamHistory> histories = unifiedEtUserExamHistoryDao.selectByUserIdAndExamPaperId(userId, examPaperId);
        if (histories == null || histories.isEmpty()) {
            return null;
        }
        return histories.get(0);
    }
}