package com.haitao.service.imp;

import com.haitao.dao.TbItemCatDao;
import com.haitao.entity.TbItemCat;
import com.haitao.service.TbItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ballontt on 2017/1/18.
 */
@Service
public class TbItemCatServiceImp implements TbItemCatService {
    @Autowired
    private TbItemCatDao tbItemCatDao;

    @Override
    public List<TbItemCat> queryList() {
        List<TbItemCat> tbItemCatList = tbItemCatDao.queryList();
        return tbItemCatList;
    }
}
