package com.haitao.service;

import com.haitao.dto.ItemCatResult;
import com.haitao.entity.TbItemCat;

import java.util.List;

/**
 * Created by ballontt on 2017/1/18.
 */
public interface TbItemCatService {
    List<ItemCatResult> queryList();
}
