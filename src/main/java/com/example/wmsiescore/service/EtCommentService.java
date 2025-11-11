package com.example.wmsiescore.service;

import com.example.wmsiescore.model.EtComment;

import java.util.List;

public interface EtCommentService {
    List<EtComment> findAll();
    EtComment findById(Integer commentId);
    int save(EtComment etComment);
    int update(EtComment etComment);
    int delete(Integer commentId);
}