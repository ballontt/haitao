package com.haitao.service;

import com.haitao.dto.TbListPage;
import com.haitao.entity.TbItem;
import java.util.List;

/**
 * Created by ballontt on 2017/1/3.
 */
public interface TbItemService {
    TbListPage<TbItem> queryList(int pages, int rows);
    int deleteItemByBatch(List<Long> tbItemList);
    Long addItemByOne(TbItem tbItem,String descText);
    int updateItemByBatch(List<TbItem> tbItemList);
}
