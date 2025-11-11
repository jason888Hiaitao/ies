package com.example.wmsiescore.mapper;

import com.example.wmsiescore.model.EtUserTemporary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EtUserTemporaryMapper {
    List<EtUserTemporary> findAll();
    EtUserTemporary findById(Integer id);
    int insert(EtUserTemporary etUserTemporary);
    int update(EtUserTemporary etUserTemporary);
    int delete(Integer id);
}