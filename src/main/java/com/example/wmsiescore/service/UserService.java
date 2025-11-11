package com.example.wmsiescore.service;

import com.example.wmsiescore.model.EtUser;

import java.util.List;

public interface UserService {
    boolean setAdmin(Long userId, Boolean isAdmin);
    boolean setDisabled(Long userId, Boolean isDisabled);
    Long addUser(EtUser user);
    boolean deleteUser(Long userId);
    boolean updateUser(EtUser user);
    EtUser getUser(Long userId);
    List<EtUser> listUsers();
}