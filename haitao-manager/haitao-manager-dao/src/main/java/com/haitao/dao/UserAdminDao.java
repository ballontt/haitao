package com.haitao.dao;

import com.haitao.entity.UserAdmin;

/**
 * Created by ballontt on 2017/1/31.
 */
public interface UserAdminDao {
    UserAdmin queryUser(String userName);
}
