package com.haitao.dao;

import com.haitao.entity.TbItemParameter;

import java.util.List;

/**
 * Created by ballontt on 2017/2/27.
 */
public interface TbItemParameterDao {
    List<TbItemParameter> queryList();
    int addItemParameterByOne(TbItemParameter tbItemParameter);
    int deleteItemParameterByBatch(List<Long> id);
}

