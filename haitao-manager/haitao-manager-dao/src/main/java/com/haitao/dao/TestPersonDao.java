package com.haitao.dao;

import com.haitao.entity.TestPerson;

import java.util.List;

/**
 * Created by ballontt on 2016/12/29.
 */
public interface TestPersonDao {
    List<TestPerson> queryList();
}
