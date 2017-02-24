package com.haitao.service.imp;

import com.haitao.dao.UserAdminDao;
import com.haitao.dto.HaitaoResult;
import com.haitao.entity.UserAdmin;
import com.haitao.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by ballontt on 2017/2/1.
 */
@Service
public class LoginServiceImp implements LoginService {
    @Autowired
    private UserAdminDao userAdminDao;
    @Override
    public HaitaoResult login(String username, String password) {


        UserAdmin userAdmin = userAdminDao.queryUser(username);
        if(userAdmin == null){
            return new HaitaoResult(false,"用户名或密码错误！");
        }
        else if(!userAdmin.getPassword().equals(password)) {
            return new HaitaoResult(false,"用户名或密码错误!");
        }
        //生成token并返回
        String token = UUID.randomUUID().toString();
        return new HaitaoResult<String>(token);
    }
}
