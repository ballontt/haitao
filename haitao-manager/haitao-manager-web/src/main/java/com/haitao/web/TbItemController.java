package com.haitao.web;

import com.haitao.dto.EditTbItemResult;
import com.haitao.dto.ItemListPage;
import com.haitao.entity.TbItem;
import com.haitao.exception.TbItemException;
import com.haitao.service.TbItemService;
import com.haitao.tbItemForm.TbItemIdListForm;
import com.haitao.tbItemForm.TbItemListForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ballontt on 2017/1/3.
 */
@Controller
@RequestMapping("/item")
public class TbItemController {
    @Autowired
    private TbItemService tbItemService;

    //分页查询
    @RequestMapping(value="/list",method= RequestMethod.GET,produces={"application/json;charset=utf-8"})
    @ResponseBody
    public ItemListPage queryList(@RequestParam(value = "page",defaultValue = "1") int page,
                                  @RequestParam(value = "rows",defaultValue = "30") int rows) {
        ItemListPage itemListPage = tbItemService.queryList(page,rows);
        return itemListPage;
    }

    //批量删除
    @RequestMapping(value="/deletions",method= RequestMethod.DELETE,produces={"application/json;charset=utf-8"})
    @ResponseBody
   public EditTbItemResult deleteItemByBatch(TbItemIdListForm tbItemIdListForm) {
        //接收前台传递来的list数据
        List<Long> tbItemList = tbItemIdListForm.getTbItemList();

        //返回结果是删除的行数
       int deleteResult;
       try{
           deleteResult = tbItemService.deleteItemByBatch(tbItemList);
       }catch(TbItemException e)
       {
           //如果异常则返回异常信息到前台
           return new EditTbItemResult(false,e.getMessage());
       }
       //返回成功执行的结果
       return new EditTbItemResult(true,deleteResult);
   }


   //增加商品
   @RequestMapping(value="/addition",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
   @ResponseBody
   public EditTbItemResult addItemByOne(TbItem tbItem) {
       Long addResult;
       try{
           addResult= tbItemService.addItemByOne(tbItem);

       }catch(TbItemException e) {
           return  new EditTbItemResult(false,e.getMessage());
       }
       return  new EditTbItemResult(true,addResult);
   }

   //批量更新商品
    @RequestMapping(value="/updation",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
    @ResponseBody
    public EditTbItemResult updateItemByBatch(TbItemListForm tbItemListForm) {
        List<TbItem> tbItemList = tbItemListForm.getTbItemList();
        int updateResult;
        try{
            updateResult = tbItemService.updateItemByBatch(tbItemList);

        }catch(TbItemException e) {
            return new EditTbItemResult(false,e.getMessage());
        }
        return new EditTbItemResult(true,updateResult);
    }
}
