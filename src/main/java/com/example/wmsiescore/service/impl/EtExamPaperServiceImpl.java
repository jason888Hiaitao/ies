package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.mapper.EtExamPaperMapper;
import com.example.wmsiescore.model.EtExamPaper;
import com.example.wmsiescore.model.PendingExamDTO;
import com.example.wmsiescore.service.EtExamPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
public class EtExamPaperServiceImpl implements EtExamPaperService {
    @Autowired
    private EtExamPaperMapper etExamPaperMapper;

    @Override
    @Transactional
    public Long createExamPaper(EtExamPaper examPaper) {
        examPaper.setCreateTime(new Timestamp(System.currentTimeMillis()));
        examPaper.setStatus("draft");
        etExamPaperMapper.insertExamPaper(examPaper);
        return examPaper.getId();
    }

    @Override
    @Transactional
    public Boolean updateExamPaper(EtExamPaper examPaper) {
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return etExamPaperMapper.updateExamPaper(examPaper) > 0;
    }

    @Override
    @Transactional
    public Boolean deleteExamPaper(Long id) {
        return etExamPaperMapper.deleteExamPaper(id) > 0;
    }

    @Override
    public EtExamPaper getExamPaperById(Long id) {
        return etExamPaperMapper.getExamPaperById(id);
    }

    @Override
    @Transactional
    public Boolean pushExamToUser(Long examPaperId, String userId) {
        EtExamPaper examPaper = etExamPaperMapper.getExamPaperById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        examPaper.setCreator(userId);
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return etExamPaperMapper.updateExamPaper(examPaper) > 0;
    }

    @Override
    @Transactional
    public Boolean pushExamToGroup(Long examPaperId, String groupId) {
        EtExamPaper examPaper = etExamPaperMapper.getExamPaperById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        examPaper.setGroupId(Long.valueOf(groupId));
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return etExamPaperMapper.updateExamPaper(examPaper) > 0;
    }

    @Override
    @Transactional
    public Boolean setExamTimeRange(Long examPaperId, Timestamp startTime, Timestamp endTime) {
        EtExamPaper examPaper = etExamPaperMapper.getExamPaperById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return etExamPaperMapper.updateExamPaper(examPaper) > 0;
    }

    @Override
    @Transactional
    public Boolean setExamDuration(Long examPaperId, Integer duration) {
        EtExamPaper examPaper = etExamPaperMapper.getExamPaperById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        examPaper.setDuration(duration);
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return etExamPaperMapper.updateExamPaper(examPaper) > 0;
    }

    @Override
    @Transactional
    public Boolean setMaxAttempts(Long examPaperId, Integer maxAttempts) {
        EtExamPaper examPaper = etExamPaperMapper.getExamPaperById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        examPaper.setExamCount(maxAttempts);
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return etExamPaperMapper.updateExamPaper(examPaper) > 0;
    }

    @Override
    @Transactional
    public Boolean setTotalScore(Long examPaperId, Integer totalScore) {
        EtExamPaper examPaper = etExamPaperMapper.getExamPaperById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        examPaper.setTotalPoint(BigDecimal.valueOf(totalScore));
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return etExamPaperMapper.updateExamPaper(examPaper) > 0;
    }

    @Override
    @Transactional
    public Boolean setPassScore(Long examPaperId, Integer passScore) {
        EtExamPaper examPaper = etExamPaperMapper.getExamPaperById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        examPaper.setPassPoint(BigDecimal.valueOf(passScore));
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return etExamPaperMapper.updateExamPaper(examPaper) > 0;
    }

    @Override
    @Transactional
    public Boolean publishExam(Long examPaperId) {
        EtExamPaper examPaper = etExamPaperMapper.getExamPaperById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        examPaper.setStatus("published");
        examPaper.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return etExamPaperMapper.updateExamPaper(examPaper) > 0;
    }

    @Override
    public List<EtExamPaper> getExamPapersForUser(String userId) {
        return etExamPaperMapper.listExamPapersForUser(userId);
    }

    @Override
    public List<EtExamPaper> getExamPapersForGroup(String groupId) {
        return etExamPaperMapper.listExamPapersForGroup(groupId);
    }

    @Override
    public List<EtExamPaper> listAllExamPapers() {
        return etExamPaperMapper.listAllExamPapers();
    }

    @Override
    @Transactional
    public Boolean publishExamPaper(Long examPaperId) {
        EtExamPaper examPaper = etExamPaperMapper.getExamPaperById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        return etExamPaperMapper.updateExamPaperStatus(examPaperId, "published") > 0;
    }

    @Override
    @Transactional
    public Boolean unpublishExamPaper(Long examPaperId) {
        EtExamPaper examPaper = etExamPaperMapper.getExamPaperById(examPaperId);
        if (examPaper == null) {
            return false;
        }
        return etExamPaperMapper.updateExamPaperStatus(examPaperId, "draft") > 0;
    }

    @Override
    public List<Long> getCompletedExamPaperIds(Long userId) {
        return etExamPaperMapper.getCompletedExamPaperIds(userId);
    }

    @Override
    public List<EtExamPaper> getVisibleExamPapersForUser(Long userId) {
        return etExamPaperMapper.getVisibleExamPapersForUser(userId);
    }

    @Override
    public List<PendingExamDTO> getPendingExamsForUser(Long userId) {
        return etExamPaperMapper.getPendingExamsForUser(userId);
    }
}