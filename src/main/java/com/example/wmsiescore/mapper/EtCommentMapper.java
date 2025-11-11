package com.example.wmsiescore.mapper;

import com.example.wmsiescore.model.EtComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EtCommentMapper {
    List<EtComment> findAll();
    EtComment findById(Integer commentId);
    int insert(EtComment etComment);
    int update(EtComment etComment);
    int delete(Integer commentId);
}