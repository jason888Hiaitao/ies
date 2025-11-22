package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.dao.UnifiedEtUserTemporaryDao;
import com.example.wmsiescore.model.EtUserTemporary;
import com.example.wmsiescore.service.EtUserTemporaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtUserTemporaryServiceImpl implements EtUserTemporaryService {

    @Autowired
    private UnifiedEtUserTemporaryDao unifiedEtUserTemporaryDao;

    @Override
    public List<EtUserTemporary> findAll() {
        return unifiedEtUserTemporaryDao.selectAll();
    }

    @Override
    public EtUserTemporary findById(Integer id) {
        return unifiedEtUserTemporaryDao.selectById(id.longValue());
    }

    @Override
    public int save(EtUserTemporary etUserTemporary) {
        return unifiedEtUserTemporaryDao.insertSelective(etUserTemporary);
    }

    @Override
    public int update(EtUserTemporary etUserTemporary) {
        return unifiedEtUserTemporaryDao.updateById(etUserTemporary);
    }

    @Override
    public int delete(Integer id) {
        return unifiedEtUserTemporaryDao.deleteById(id.longValue());
    }
}