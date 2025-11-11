package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.mapper.EtCommentMapper;
import com.example.wmsiescore.model.EtComment;
import com.example.wmsiescore.service.EtCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtCommentServiceImpl implements EtCommentService {

    @Autowired
    private EtCommentMapper etCommentMapper;

    @Override
    public List<EtComment> findAll() {
        return etCommentMapper.findAll();
    }

    @Override
    public EtComment findById(Integer commentId) {
        return etCommentMapper.findById(commentId);
    }

    @Override
    public int save(EtComment etComment) {
        return etCommentMapper.insert(etComment);
    }

    @Override
    public int update(EtComment etComment) {
        return etCommentMapper.update(etComment);
    }

    @Override
    public int delete(Integer commentId) {
        return etCommentMapper.delete(commentId);
    }
}