package com.haitao.service;

import com.haitao.dto.EditTbItemResult;
import com.haitao.dto.ItemListPage;
import com.haitao.entity.TbItem;

import java.util.List;

/**
 * Created by ballontt on 2017/1/3.
 */
public interface TbItemService {
    ItemListPage queryList(int pages,int rows);
    int deleteItemByBatch(List<Long> tbItemList);
    Long addItemByOne(TbItem tbItem);
    int updateItemByBatch(List<TbItem> tbItemList);
}
