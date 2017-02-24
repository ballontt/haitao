package com.haitao.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haitao.dao.TbItemDao;
import com.haitao.dao.TbItemDescDao;
import com.haitao.dto.ItemListPage;
import com.haitao.entity.TbItem;
import com.haitao.entity.TbItemDesc;
import com.haitao.exception.TbItemException;
import com.haitao.service.TbItemService;
import com.haitao.utils.IDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by ballontt on 2017/1/3.
 */
@Service
public class TbItemServiceImp implements TbItemService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbItemDao tbItemDao;
    @Autowired
    private TbItemDescDao tbItemDescDao;

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
        if(deleteNum != tbItemList.size()) {
            throw new TbItemException("删除异常");
        }
        else {
            return deleteNum;
        }
    }

    @Override
    public Long addItemByOne(TbItem tbItem,String descText){
        //生成TbItemDesc对象并填充
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemDesc(descText);
        //生成item ID
        long id = IDUtils.getItemId();
        tbItem.setId(id);
        tbItemDesc.setItemId(id);
        //设置时间
        Date date = new Date();
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        tbItemDesc.setCreated(date);
        tbItemDesc.setUpdated(date);

        //设置状态
        tbItem.setStatus(1);
        //添加到数据库
        Long addItemResult = tbItemDao.addItemByOne(tbItem);
        long addDescResult = tbItemDescDao.addItemDesc(tbItemDesc);
        if(addItemResult != 1 || addDescResult != 1) {
            throw new TbItemException("增加异常!");
        }
        else{
            return addItemResult;
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
