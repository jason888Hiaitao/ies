package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.dao.UnifiedEtExamPaperDao;
import com.example.wmsiescore.model.EtExamPaper;
import com.example.wmsiescore.model.PendingExamDTO;
import com.example.wmsiescore.service.EtExamPaperService;
import com.example.wmsiescore.service.PendingExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class EtExamPaperServiceImpl implements EtExamPaperService {
    @Autowired
    private UnifiedEtExamPaperDao unifiedEtExamPaperDao;

    @Autowired
    private PendingExamService pendingExamService;

    @Override
    @Transactional
    public Long createExamPaper(EtExamPaper examPaper) {
        examPaper.setCreateTime(new Timestamp(System.currentTimeMillis()));
        examPaper.setStatus("draft");
        unifiedEtExamPaperDao.insertSelective(examPaper);
        return examPaper.getId();
    }

    @Override
    @Transactional
    public Boolean updateExamPaper(EtExamPaper examPaper) {
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return unifiedEtExamPaperDao.updateById(examPaper) > 0;
    }

    @Override
    @Transactional
    public Boolean deleteExamPaper(Long id) {
        return unifiedEtExamPaperDao.deleteById(id) > 0;
    }

    @Override
    public EtExamPaper getExamPaperById(Long id) {
        return unifiedEtExamPaperDao.selectById(id);
    }

    @Override
    @Transactional
    public Boolean pushExamToUser(Long examPaperId, String userId) {
        EtExamPaper examPaper = unifiedEtExamPaperDao.selectById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        examPaper.setCreator(userId);
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return unifiedEtExamPaperDao.updateById(examPaper) > 0;
    }

    @Override
    @Transactional
    public Boolean pushExamToGroup(Long examPaperId, String groupId) {
        EtExamPaper examPaper = unifiedEtExamPaperDao.selectById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        examPaper.setGroupId(Long.valueOf(groupId));
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return unifiedEtExamPaperDao.updateById(examPaper) > 0;
    }

    @Override
    @Transactional
    public Boolean setExamTimeRange(Long examPaperId, Timestamp startTime, Timestamp endTime) {
        EtExamPaper examPaper = unifiedEtExamPaperDao.selectById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return unifiedEtExamPaperDao.updateById(examPaper) > 0;
    }

    @Override
    @Transactional
    public Boolean setExamDuration(Long examPaperId, Integer duration) {
        EtExamPaper examPaper = unifiedEtExamPaperDao.selectById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        examPaper.setDuration(duration);
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return unifiedEtExamPaperDao.updateById(examPaper) > 0;
    }

    @Override
    @Transactional
    public Boolean setMaxAttempts(Long examPaperId, Integer maxAttempts) {
        EtExamPaper examPaper = unifiedEtExamPaperDao.selectById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        examPaper.setExamCount(maxAttempts);
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return unifiedEtExamPaperDao.updateById(examPaper) > 0;
    }

    @Override
    @Transactional
    public Boolean setTotalScore(Long examPaperId, Integer totalScore) {
        EtExamPaper examPaper = unifiedEtExamPaperDao.selectById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        examPaper.setTotalPoint(BigDecimal.valueOf(totalScore));
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return unifiedEtExamPaperDao.updateById(examPaper) > 0;
    }

    @Override
    @Transactional
    public Boolean setPassScore(Long examPaperId, Integer passScore) {
        EtExamPaper examPaper = unifiedEtExamPaperDao.selectById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        examPaper.setPassPoint(BigDecimal.valueOf(passScore));
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return unifiedEtExamPaperDao.updateById(examPaper) > 0;
    }

    @Override
    @Transactional
    public Boolean publishExam(Long examPaperId) {
        EtExamPaper examPaper = unifiedEtExamPaperDao.selectById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        examPaper.setStatus("published");
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return unifiedEtExamPaperDao.updateById(examPaper) > 0;
    }

    @Override
    public List<EtExamPaper> getExamPapersForUser(String userId) {
        try {
            Long parsedUserId = Long.parseLong(userId);
            List<EtExamPaper> papers = unifiedEtExamPaperDao.getVisibleExamPapersForUser(parsedUserId);
            return papers == null ? new ArrayList<>() : papers;
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<EtExamPaper> getExamPapersForGroup(String groupId) {
        try {
            Long fieldId = Long.parseLong(groupId);
            List<EtExamPaper> papers = unifiedEtExamPaperDao.getExamPapersByFieldIds(java.util.Collections.singletonList(fieldId));
            return papers == null ? new ArrayList<>() : papers;
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<EtExamPaper> listAllExamPapers() {
        return unifiedEtExamPaperDao.selectAll();
    }

    @Override
    @Transactional
    public Boolean publishExamPaper(Long examPaperId) {
        EtExamPaper examPaper = unifiedEtExamPaperDao.selectById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        examPaper.setStatus("published");
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return unifiedEtExamPaperDao.updateById(examPaper) > 0;
    }

    @Override
    @Transactional
    public Boolean unpublishExamPaper(Long examPaperId) {
        EtExamPaper examPaper = unifiedEtExamPaperDao.selectById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        examPaper.setStatus("draft");
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return unifiedEtExamPaperDao.updateById(examPaper) > 0;
    }

    @Override
    public List<Long> getCompletedExamPaperIds(Long userId) {
        List<Long> ids = unifiedEtExamPaperDao.getCompletedExamPaperIds(userId);
        return ids == null ? new ArrayList<>() : ids;
    }

    @Override
    public List<EtExamPaper> getVisibleExamPapersForUser(Long userId) {
        List<EtExamPaper> papers = unifiedEtExamPaperDao.getVisibleExamPapersForUser(userId);
        return papers == null ? new ArrayList<>() : papers;
    }

    @Override
    public List<PendingExamDTO> getPendingExamsForUser(Long userId) {
        return pendingExamService.getPendingExamsForUser(userId);
    }
}