package com.haitao.web;

import com.haitao.dto.HaitaoResult;
import com.haitao.dto.TbListPage;
import com.haitao.entity.TbItemParameter;
import com.haitao.exception.TbItemException;
import com.haitao.service.TbItemParameterService;
import com.haitao.tbItemForm.CatParamIdListForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ballontt on 2017/2/27.
 */
@Controller
@RequestMapping("/parameter")
public class TbItemParameterController {

    @Autowired
    private TbItemParameterService tbItemParameterService;

    @RequestMapping(value="/list",method= RequestMethod.GET,produces={"application/json;charset=utf-8"})
    @ResponseBody
    public HaitaoResult queryList(@RequestParam(value = "page",defaultValue = "1") int page,
                                  @RequestParam(value="rows",defaultValue = "20") int rows) {

        TbListPage<TbItemParameter> tbItemParameterListPage;
        try{
            tbItemParameterListPage = tbItemParameterService.queryList(page,rows);

        }catch (Exception e) {
            return new HaitaoResult(false,e.getMessage());
        }
        return new HaitaoResult<TbListPage<TbItemParameter>>(tbItemParameterListPage);
    }

    @RequestMapping(value="/deletions",method=RequestMethod.DELETE,produces={"application/json;charset=utf-8"})
    @ResponseBody
    public HaitaoResult deleteItemParamsByBatch(CatParamIdListForm catParamIdListForm) {

        List<Long> catParamList = catParamIdListForm.getCatParamList();
        int deleteRows;
        try{
            deleteRows = tbItemParameterService.deleteItemParameterByBatch(catParamList) ;
        } catch(TbItemException e) {
            return new HaitaoResult(false,e.getMessage());
        }
        return new HaitaoResult(deleteRows);
    }

    @RequestMapping(value="/addition",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
    @ResponseBody
    public HaitaoResult addItemParameter(long cid,String parameterData) {

        int addRows;
        try{
            addRows = tbItemParameterService.addItemParameter(cid,parameterData);
        }catch(TbItemException e) {
            return new HaitaoResult(false,e.getMessage());
        }
        return new HaitaoResult(addRows);
    }
}
