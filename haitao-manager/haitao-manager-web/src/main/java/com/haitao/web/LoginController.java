package com.haitao.web;

import com.haitao.dto.HaitaoResult;
import com.haitao.entity.UserAdmin;
import com.haitao.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ballontt on 2017/2/1.
 */
@Controller
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(value="/login",method= RequestMethod.POST,produces={"application/json;charset=utf-8"})
    @ResponseBody
    public HaitaoResult LoginController(@RequestBody UserAdmin userAdmin) {
        try{

            HaitaoResult haitaoResult = loginService.login(userAdmin.getUserName(),userAdmin.getPassword());
            return haitaoResult;
        }catch (Exception e) {
            e.printStackTrace();
            return new HaitaoResult(false,e.getMessage());
        }

    }
}
