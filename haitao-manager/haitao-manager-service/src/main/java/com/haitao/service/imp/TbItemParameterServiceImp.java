package com.haitao.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haitao.dao.TbItemParameterDao;
import com.haitao.dto.TbListPage;
import com.haitao.entity.TbItemParameter;
import com.haitao.exception.TbItemException;
import com.haitao.service.TbItemParameterService;
import com.haitao.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by ballontt on 2017/2/27.
 */
@Service
public class TbItemParameterServiceImp implements TbItemParameterService{

    @Autowired
    private TbItemParameterDao tbItemParameterDao;

    @Override
    public TbListPage<TbItemParameter> queryList(int pages, int rows) {

        PageHelper.startPage(pages,rows);
        List<TbItemParameter> tbItemParameterList= tbItemParameterDao.queryList();

        //对查询结果包装
        PageInfo pageInfo = new PageInfo(tbItemParameterList);

        //构造返回对象
        TbListPage<TbItemParameter> tbItemParameterListPage = new TbListPage<TbItemParameter>();
        //设置总页数
        tbItemParameterListPage.setTotalPage(pageInfo.getPages());
        //设置当前页码
        tbItemParameterListPage.setCurrentPage(pageInfo.getPageNum());

        tbItemParameterListPage.setTbList(tbItemParameterList);

        return tbItemParameterListPage;
    }

    @Override
    public int deleteItemParameterByBatch(List<Long> id){
        int deleteRows = tbItemParameterDao.deleteItemParameterByBatch(id);
        if(deleteRows != id.size()) {
            throw new TbItemException("删除异常!");
        } else{
            return deleteRows;
        }
    }

    @Override
    public int addItemParameter(Long cid,String parameterData) {

        //构建对象填充参数数据
        TbItemParameter tbItemParameter = new TbItemParameter();
        tbItemParameter.setParamData(parameterData);
        //填充cid
        tbItemParameter.setItemCatId(cid);
        //填充时间数据
        Date date = new Date();
        tbItemParameter.setCreated(date);
        tbItemParameter.setUpdated(date);
        //构建id并填充
        long id = IDUtils.getItemId();
        tbItemParameter.setId(id);


        int addResult = tbItemParameterDao.addItemParameterByOne(tbItemParameter);
        if(addResult != 1) {
            throw new TbItemException("插入规格参数异常");

        }
        return addResult;
    }
}
