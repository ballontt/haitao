package com.haitao.web;

import com.haitao.entity.TestPerson;
import com.haitao.service.TestPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by ballontt on 2016/12/29.
 */
@Controller
@RequestMapping("/test")
public class TestPersonController {
    @Autowired
    private TestPersonService testPersonService;

    @RequestMapping(value="/list",method= RequestMethod.GET,produces={"application/json;charset=utf-8"})
    @ResponseBody
    public List<TestPerson> queryList() {

       List<TestPerson> testPersonList = testPersonService.queryList();
       return testPersonList;

    }
}
