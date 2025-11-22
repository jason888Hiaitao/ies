package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.dao.UnifiedEtCommentDao;
import com.example.wmsiescore.model.EtComment;
import com.example.wmsiescore.service.EtCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtCommentServiceImpl implements EtCommentService {

    @Autowired
    private UnifiedEtCommentDao unifiedEtCommentDao;

    @Override
    public List<EtComment> findAll() {
        return unifiedEtCommentDao.selectAll();
    }

    @Override
    public EtComment findById(Integer commentId) {
        return unifiedEtCommentDao.selectById(commentId.longValue());
    }

    @Override
    public int save(EtComment etComment) {
        return unifiedEtCommentDao.insertSelective(etComment);
    }

    @Override
    public int update(EtComment etComment) {
        return unifiedEtCommentDao.updateById(etComment);
    }

    @Override
    public int delete(Integer commentId) {
        return unifiedEtCommentDao.deleteById(commentId.longValue());
    }
}