package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.mapper.UserMapper;
import com.example.wmsiescore.model.EtUser;
import com.example.wmsiescore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean setAdmin(Long userId, Boolean isAdmin) {
        return userMapper.updateAdminStatus(userId, isAdmin) > 0;
    }

    @Override
    public boolean setDisabled(Long userId, Boolean isDisabled) {
        return userMapper.updateDisabledStatus(userId, isDisabled) > 0;
    }

    @Override
    public Long addUser(EtUser user) {
        userMapper.insertUser(user);
        return user.getId();
    }

    @Override
    public boolean deleteUser(Long userId) {
        return userMapper.deleteUser(userId) > 0;
    }

    @Override
    public boolean updateUser(EtUser user) {
        return userMapper.updateUser(user) > 0;
    }

    @Override
    public EtUser getUser(Long userId) {
        return userMapper.getUser(userId);
    }

    @Override
    public List<EtUser> listUsers() {
        return userMapper.listUsers();
    }
}