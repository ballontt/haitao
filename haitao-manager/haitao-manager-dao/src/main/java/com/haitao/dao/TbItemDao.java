package com.haitao.dao;

import com.haitao.entity.TbItem;

import java.util.List;

/**
 * Created by ballontt on 2017/1/3.
 */
public interface TbItemDao {
    List<TbItem> queryList();
    int deleteItemByBatch(List<Long> tbItemList);
    Long addItemByOne(TbItem tbItem);
    int updateItemByBatch(List<TbItem> tbItemList);
}

