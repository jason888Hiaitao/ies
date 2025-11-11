package com.example.wmsiescore.service;

import com.example.wmsiescore.model.EtUserTemporary;

import java.util.List;

public interface EtUserTemporaryService {
    List<EtUserTemporary> findAll();
    EtUserTemporary findById(Integer id);
    int save(EtUserTemporary etUserTemporary);
    int update(EtUserTemporary etUserTemporary);
    int delete(Integer id);
}