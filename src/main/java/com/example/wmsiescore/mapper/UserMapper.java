package com.example.wmsiescore.mapper;

import com.example.wmsiescore.model.EtUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    int updateAdminStatus(@Param("userId") Long userId, @Param("isAdmin") Boolean isAdmin);
    int updateDisabledStatus(@Param("userId") Long userId, @Param("isDisabled") Boolean isDisabled);
    void insertUser(EtUser user);
    int deleteUser(@Param("userId") Long userId);
    int updateUser(EtUser user);
    EtUser getUser(@Param("userId") Long userId);
    List<EtUser> listUsers();
}