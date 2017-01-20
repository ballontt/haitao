package com.haitao.web;

import com.haitao.entity.TbItemCat;
import com.haitao.service.TbItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by ballontt on 2017/1/20.
 */
@Controller
@RequestMapping("/itemCat")
public class TbItemCatController {
    @Autowired
    private TbItemCatService tbItemCatService;

    @RequestMapping(value="/list",method= RequestMethod.GET,produces={"application/json;charset=utf-8"})
    @ResponseBody
    public List<TbItemCat> queryList() {

        return tbItemCatService.queryList();
    }
}
