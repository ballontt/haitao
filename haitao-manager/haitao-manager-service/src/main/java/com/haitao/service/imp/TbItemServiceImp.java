package com.haitao.service.imp;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haitao.dao.TbItemDao;
import com.haitao.dto.EditTbItemResult;
import com.haitao.dto.ItemListPage;
import com.haitao.entity.TbItem;
import com.haitao.exception.TbItemException;
import com.haitao.service.TbItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ballontt on 2017/1/3.
 */
@Service
public class TbItemServiceImp implements TbItemService{
    //private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbItemDao tbItemDao;

    @Override
    public ItemListPage queryList(int page,int rows) {
        PageHelper.startPage(page,rows);
        List<TbItem> tbItemList = tbItemDao.queryList();

        //用PageInfo对结果进行包装
        PageInfo pageInfo = new PageInfo(tbItemList);

        ItemListPage itemListPage = new ItemListPage();
        //每页中的内容
        itemListPage.setTbItemlist(tbItemList);
        //读取总页数
        itemListPage.setTotalPage(pageInfo.getPages());
        //读取当前页数
        itemListPage.setCurrentPage(pageInfo.getPageNum());
        return itemListPage;
    }
    @Override
    public int deleteItemByBatch(List<Long> tbItemList) {
        int deleteNum = tbItemDao.deleteItemByBatch(tbItemList);
        logger.debug("delete operation printed via slf4j");
        logger.error("delete operation printed via slf4j");
        if(deleteNum != tbItemList.size()) {
            throw new TbItemException("删除异常");
        }
        else {
            return deleteNum;
        }
    }

    @Override
    public Long addItemByOne(TbItem tbItem){

        Long addResult = tbItemDao.addItemByOne(tbItem);
        if(addResult != 1) {
            throw new TbItemException("增加异常!");
        }
        else{
            return addResult;
        }
    }

    @Override
    public int updateItemByBatch(List<TbItem> tbItemList){

        int updateResult = tbItemDao.updateItemByBatch(tbItemList);
        if(updateResult != tbItemList.size()) {
            throw new TbItemException("更新异常!");
        }
        else{
            return updateResult;
        }

    }
}
