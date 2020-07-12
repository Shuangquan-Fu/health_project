package com.quan.dao;

import com.quan.pojo.User;

public interface UserDao {
    public User findByUsername(String username);
}
