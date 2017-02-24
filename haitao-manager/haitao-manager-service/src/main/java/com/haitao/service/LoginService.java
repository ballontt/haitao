package com.haitao.service;

import com.haitao.dto.HaitaoResult;

/**
 * Created by ballontt on 2017/2/1.
 */
public interface LoginService {
   HaitaoResult login(String username,String password);
}
