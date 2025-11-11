package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.mapper.EtUserTemporaryMapper;
import com.example.wmsiescore.model.EtUserTemporary;
import com.example.wmsiescore.service.EtUserTemporaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtUserTemporaryServiceImpl implements EtUserTemporaryService {

    @Autowired
    private EtUserTemporaryMapper etUserTemporaryMapper;

    @Override
    public List<EtUserTemporary> findAll() {
        return etUserTemporaryMapper.findAll();
    }

    @Override
    public EtUserTemporary findById(Integer id) {
        return etUserTemporaryMapper.findById(id);
    }

    @Override
    public int save(EtUserTemporary etUserTemporary) {
        return etUserTemporaryMapper.insert(etUserTemporary);
    }

    @Override
    public int update(EtUserTemporary etUserTemporary) {
        return etUserTemporaryMapper.update(etUserTemporary);
    }

    @Override
    public int delete(Integer id) {
        return etUserTemporaryMapper.delete(id);
    }
}