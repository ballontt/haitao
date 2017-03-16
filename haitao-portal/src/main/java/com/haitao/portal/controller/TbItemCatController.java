package com.haitao.portal.controller;

import com.haitao.dto.HaitaoResult;
import com.haitao.service.TTbItemCatService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ballontt on 2017/3/9.
 */

@Controller
@RequestMapping("/itemCat")
public class TbItemCatController {
    @Autowired
    @Qualifier(value="tbItemCatService")
    private TTbItemCatService.Iface tbItemCatService;

    @RequestMapping(value="/treelist",method=RequestMethod.GET,produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public HaitaoResult ItequeryList() {
        String result = null;
        try {
            result = tbItemCatService.queryTreeItemCat();
        } catch (TException e) {
            e.printStackTrace();
        }
        return new HaitaoResult(result);
    }
}
