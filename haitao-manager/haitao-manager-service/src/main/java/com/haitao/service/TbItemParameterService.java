package com.haitao.service;

import com.haitao.dto.TbListPage;
import com.haitao.entity.TbItemParameter;

import java.util.List;

/**
 * Created by ballontt on 2017/2/27.
 */
public interface TbItemParameterService {
    TbListPage<TbItemParameter> queryList(int pages,int rows);
    int deleteItemParameterByBatch(List<Long> id);
    int addItemParameter(Long cid,String parameterData);
}
