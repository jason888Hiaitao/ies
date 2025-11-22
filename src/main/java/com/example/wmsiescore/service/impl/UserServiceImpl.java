package com.example.wmsiescore.service.impl;

import com.example.wmsiescore.dao.UnifiedUserDao;
import com.example.wmsiescore.model.EtUser;
import com.example.wmsiescore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UnifiedUserDao unifiedUserDao;

    @Override
    public boolean setAdmin(Long userId, Boolean isAdmin) {
        return unifiedUserDao.updateAdminStatus(userId, isAdmin) > 0;
    }

    @Override
    public boolean setDisabled(Long userId, Boolean isDisabled) {
        return unifiedUserDao.updateDisabledStatus(userId, isDisabled) > 0;
    }

    @Override
    public Long addUser(EtUser user) {
        unifiedUserDao.insertUser(user);
        return user.getId();
    }

    @Override
    public boolean deleteUser(Long userId) {
        return unifiedUserDao.deleteUser(userId) > 0;
    }

    @Override
    public boolean updateUser(EtUser user) {
        return unifiedUserDao.updateUser(user) > 0;
    }

    @Override
    public EtUser getUser(Long userId) {
        return unifiedUserDao.getUser(userId);
    }

    @Override
    public List<EtUser> listUsers() {
        return unifiedUserDao.listUsers();
    }
}