package com.haitao.dao;

import com.haitao.dto.ItemCatResult;

import java.util.List;

/**
 * Created by ballontt on 2017/1/18.
 */
public interface TbItemCatDao {
    List<ItemCatResult> queryList();
}
