package com.haitao.dto;

import com.haitao.entity.TbItem;

import java.util.List;

/**
 * Created by ballontt on 2017/1/4.
 */
public class ItemListPage {
    private int totalPage;
    private int currentPage;
    private List<TbItem> tbItemlist;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<TbItem> getTbItemlist() {
        return tbItemlist;
    }

    public void setTbItemlist(List<TbItem> tbItemlist) {
        this.tbItemlist = tbItemlist;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
