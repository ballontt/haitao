package com.haitao.service.imp;

import com.haitao.dao.TestPersonDao;
import com.haitao.entity.TestPerson;
import com.haitao.service.TestPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ballontt on 2016/12/29.
 */
@Service
public class TestPersonServiceImp implements TestPersonService{
    @Autowired
    private TestPersonDao testPersonDao;
    @Override
    public List<TestPerson> queryList() {
        return testPersonDao.queryList();
    }
}
