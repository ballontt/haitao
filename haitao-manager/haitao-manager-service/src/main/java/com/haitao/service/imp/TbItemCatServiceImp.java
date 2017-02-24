package com.haitao.service.imp;

import com.haitao.dao.TbItemCatDao;
import com.haitao.dto.ItemCatResult;
import com.haitao.service.TbItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ballontt on 2017/1/18.
 */
@Service
public class TbItemCatServiceImp implements TbItemCatService {
    @Autowired
    private TbItemCatDao tbItemCatDao;

    @Override
    public List<ItemCatResult> queryList() {
        List<ItemCatResult> itemCatResultList = tbItemCatDao.queryList();
        List<ItemCatResult> itemCatResultTree = new ArrayList<ItemCatResult>();
        //将返回列表处理为树形结构
        for(ItemCatResult result1 : itemCatResultList) {
           boolean mark = false;
            for(ItemCatResult result2 : itemCatResultList) {
                if(result1.getData().getParentId() == result2.getLabel()) {
                    mark = true;
                    if(result2.getChildren() == null) {
                        result2.setChildren(new ArrayList<ItemCatResult>());
                    }
                    result2.getChildren().add(result1);
                    break;
                }
            }
            if(!mark) {
                itemCatResultTree.add(result1);
            }
        }
        //返回树形结构的结果
        return itemCatResultTree;
    }
}
