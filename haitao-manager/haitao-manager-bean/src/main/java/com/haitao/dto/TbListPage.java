package com.haitao.dto;

import com.haitao.entity.TbItem;

import java.util.List;

/**
 * Created by ballontt on 2017/1/4.
 */
public class TbListPage<T> {
    private int totalPage;
    private int currentPage;
    private List<T> tbList;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getTbList() {
        return tbList;
    }

    public void setTbList(List<T> tbList) {
        this.tbList = tbList;
    }

    @Override
    public String toString() {
        return "TbListPage{" +
                "totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", tbList=" + tbList +
                '}';
    }
}
