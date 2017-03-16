package com.haitao.service.imp;

import com.alibaba.druid.support.json.JSONUtils;
import com.haitao.dto.ItemCatResult;
import com.haitao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.haitao.dao.TbItemCatDao;
import com.haitao.service.TTbItemCatService;
import org.apache.thrift.TException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ballontt on 2017/3/8.
 */
public class TTbItemCatServiceImp implements TTbItemCatService.Iface {

    @Autowired
    private TbItemCatDao tbItemCatDao;


    @Override
    public String queryTreeItemCat() throws TException {
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
        String result = JsonUtils.objectToJson(itemCatResultTree);
        return result;
    }
}
