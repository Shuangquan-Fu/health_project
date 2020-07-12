package com.quan.dao;

import com.quan.pojo.Permission;
import com.quan.pojo.Role;

import java.util.Set;

public interface PermissionDao {
    public Set<Permission> findByRoleId(int roleId);
}
